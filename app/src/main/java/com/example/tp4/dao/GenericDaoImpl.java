package com.example.tp4.dao;

import com.example.tp4.annotation.Column;
import com.example.tp4.annotation.Entity;
import com.example.tp4.annotation.Id;
import com.example.tp4.connection.DaoHelper;
import com.example.tp4.exception.NotFoundAnnotationException;
import com.googlecode.openbeans.IntrospectionException;
import com.googlecode.openbeans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class GenericDaoImpl<T> implements IGenericDao<T> {

    // El alias de la tabla
    private static final String TABLE_ALIAS = "t";

    @Override
    public void save(T t) throws Exception, NotFoundAnnotationException {
        Class<?> clazz = t.getClass();
        // Obtener el nombre de la tabla
        String tableName = getTableName(clazz);
        // Obtener el campo
        StringBuilder fieldNames = new StringBuilder();		//Nombre del campo
        List<Object> fieldValues = new ArrayList<Object>();	// Valor del campo
        StringBuilder placeholders = new StringBuilder();	// Marcador de posición
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            PropertyDescriptor pd = new PropertyDescriptor(field.getName(),t.getClass());
            if (field.isAnnotationPresent(Id.class)) {
                fieldNames.append(field.getAnnotation(Id.class).value()).append(",");
                fieldValues.add(pd.getReadMethod().invoke(t));
            } else if(field.isAnnotationPresent(Column.class)) {
                fieldNames.append(field.getAnnotation(Column.class).value()).append(",");
                fieldValues.add(pd.getReadMethod().invoke(t));
            }
            placeholders.append("?").append(",");
        }
        // Elimina la última coma
        fieldNames.deleteCharAt(fieldNames.length()-1);
        placeholders.deleteCharAt(placeholders.length()-1);

        // Empalme sql
        StringBuilder sql = new StringBuilder("");
        sql.append("insert into ").append(tableName)
                .append(" (").append(fieldNames.toString())
                .append(") values (").append(placeholders).append(")") ;
        PreparedStatement ps = DaoHelper.getConnection().prepareStatement(sql.toString());
        // Establecer el valor del marcador de posición del parámetro SQL
        setParameter(fieldValues, ps, false);
        // Ejecutar SQL
        ps.execute();
        DaoHelper.release(ps, null);
    }


    @Override
    public void delete(Object id,Class<T> clazz) throws Exception, NotFoundAnnotationException {
        // Obtener el nombre de la tabla
        String tableName = getTableName(clazz);
        // Obtener el nombre y el valor del campo de ID
        String idFieldName = "";
        boolean flag = false;
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if(field.isAnnotationPresent(Id.class)) {
                idFieldName = field.getAnnotation(Id.class).value();
                flag = true;
                break;
            }
        }
        if (!flag) {
            throw new NotFoundAnnotationException(clazz.getName() + " object not found id property.");
        }

        // Ensamblar sql
        String sql = "delete from " + tableName + " where " + idFieldName + "=?";
        PreparedStatement ps = DaoHelper.getConnection().prepareStatement(sql);
        ps.setObject(1, id);
        // Ejecutar SQL
        ps.execute();
        DaoHelper.release(ps,null);
    }

    @Override
    public void update(T t) throws Exception, NotFoundAnnotationException {
        Class<?> clazz = t.getClass();
        // Obtener el nombre de la tabla
        String tableName = getTableName(clazz);
        // Obtener el campo
        List<Object> fieldNames = new ArrayList<Object>();	//Nombre del campo
        List<Object> fieldValues = new ArrayList<Object>();	// Valor del campo
        List<String> placeholders = new ArrayList<String>();// Marcador de posición
        String idFieldName = "";
        Object idFieldValue = "";
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            PropertyDescriptor pd = new PropertyDescriptor(field.getName(),t.getClass());
            if (field.isAnnotationPresent(Id.class)) {
                idFieldName = field.getAnnotation(Id.class).value();
                idFieldValue = pd.getReadMethod().invoke(t);
            } else if(field.isAnnotationPresent(Column.class)) {
                fieldNames.add(field.getAnnotation(Column.class).value());
                fieldValues.add(pd.getReadMethod().invoke(t));
                placeholders.add("?");
            }
        }
        // ID como condición de actualización, colocado en el último elemento de la colección
        fieldNames.add(idFieldName);
        fieldValues.add(idFieldValue);
        placeholders.add("?");

        // Empalme sql
        StringBuilder sql = new StringBuilder("");
        sql.append("update ").append(tableName).append(" set ");
        int index = fieldNames.size() - 1;
        for (int i = 0; i < index; i++) {
            sql.append(fieldNames.get(i)).append("=").append(placeholders.get(i)).append(",");
        }
        sql.deleteCharAt(sql.length()-1).append(" where ").append(fieldNames.get(index)).append("=").append("?");

        // Establecer el valor del marcador de posición del parámetro SQL
        PreparedStatement ps = DaoHelper.getConnection().prepareStatement(sql.toString());
        setParameter(fieldValues, ps, false);

        // Ejecutar SQL
        ps.execute();
        DaoHelper.release(ps, null);
    }

    @Override
    public T get(Object id,Class<T> clazz) throws Exception, NotFoundAnnotationException {
        String idFieldName = "";
        Field[] fields = clazz.getDeclaredFields();
        boolean flag = false;
        for (Field field : fields) {
            if (field.isAnnotationPresent(Id.class)) {
                idFieldName = field.getAnnotation(Id.class).value();
                flag = true;
                break;
            }
        }

        if (!flag) {
            throw new NotFoundAnnotationException(clazz.getName() + " object not found id property.");
        }

        // Ensamblar SQL
        Map<String,Object> sqlWhereMap = new HashMap<String, Object>();
        sqlWhereMap.put(TABLE_ALIAS + "." + idFieldName, id);

        List<T> list = findAllByConditions(sqlWhereMap, clazz);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public ResultSet selectByQuery(String select, String table, String where) throws SQLException {
        Statement st = DaoHelper.getConnection().createStatement();
        return st.executeQuery("select "+select+" from "+table+" where "+where);
    }

    @Override
    public List<T> findAllByConditions(Map<String,Object> sqlWhereMap,Class<T> clazz) throws Exception, NotFoundAnnotationException {
        List<T> list = new ArrayList<T>();
        String tableName = getTableName(clazz);
        String idFieldName = "";
        // Almacenar información de todos los campos
        // Obtener el campo a consultar mediante la reflexión
        StringBuffer fieldNames = new StringBuffer();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            String propertyName = field.getName();
            if (field.isAnnotationPresent(Id.class)) {
                idFieldName = field.getAnnotation(Id.class).value();
                fieldNames.append(TABLE_ALIAS + "." + idFieldName)
                        .append(" as ").append(propertyName).append(",");
            } else if (field.isAnnotationPresent(Column.class)) {
                fieldNames.append(TABLE_ALIAS + "." + field.getAnnotation(Column.class).value())
                        .append(" as ").append(propertyName).append(",");
            }
        }
        fieldNames.deleteCharAt(fieldNames.length()-1);

        // Ensamblar SQL
        String sql = "select " + fieldNames + " from " + tableName + " " + TABLE_ALIAS;
        PreparedStatement ps = null;
        List<Object> values = null;
        if (sqlWhereMap != null) {
            List<Object> sqlWhereWithValues = getSqlWhereWithValues(sqlWhereMap);
            if (sqlWhereWithValues != null) {
                // empalmar condiciones SQL
                String sqlWhere = (String)sqlWhereWithValues.get(0);
                sql += sqlWhere;
                // Obtiene el valor del marcador de posición en la condición SQL
                values = (List<Object>) sqlWhereWithValues.get(1);
            }
        }

        // Establecer el valor del marcador de posición del parámetro
        if (values != null) {
            ps = DaoHelper.getConnection().prepareStatement(sql);
            setParameter(values, ps, true);
        } else {
            ps = DaoHelper.getConnection().prepareStatement(sql);
        }


        // Ejecutar SQL
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            T t = clazz.newInstance();
            initObject(t, fields, rs);
            list.add(t);
        }

        // Liberar recursos
        DaoHelper.release(ps, rs);

        System.out.println(sql);
        return list;
    }

    /**
     * Inicializar el objeto según el conjunto de resultados
     */
    private void initObject(T t, Field[] fields, ResultSet rs)
            throws SQLException, IntrospectionException,
            IllegalAccessException, InvocationTargetException {
        for (Field field : fields) {
            String propertyName = field.getName();
            Object paramVal = null;
            Class<?> clazzField = field.getType();
            if (clazzField == String.class) {
                paramVal = rs.getString(propertyName);
            } else if (clazzField == short.class || clazzField == Short.class) {
                paramVal = rs.getShort(propertyName);
            } else if (clazzField == int.class || clazzField == Integer.class) {
                paramVal = rs.getInt(propertyName);
            } else if (clazzField == long.class || clazzField == Long.class) {
                paramVal = rs.getLong(propertyName);
            } else if (clazzField == float.class || clazzField == Float.class) {
                paramVal = rs.getFloat(propertyName);
            } else if (clazzField == double.class || clazzField == Double.class) {
                paramVal = rs.getDouble(propertyName);
            } else if (clazzField == boolean.class || clazzField == Boolean.class) {
                paramVal = rs.getBoolean(propertyName);
            } else if (clazzField == byte.class || clazzField == Byte.class) {
                paramVal = rs.getByte(propertyName);
            } else if (clazzField == char.class || clazzField == Character.class) {
                paramVal = rs.getCharacterStream(propertyName);
            } else if (clazzField == Date.class) {
                paramVal = rs.getTimestamp(propertyName);
            } else if (clazzField.isArray()) {
                paramVal = rs.getString(propertyName).split(",");	// Cadena separada por comas
            }
            PropertyDescriptor pd = new PropertyDescriptor(propertyName,t.getClass());
            pd.getWriteMethod().invoke(t, paramVal);
        }
    }

    /**
     * Según las condiciones, devuelva la condición sql y el valor del marcador de posición en la condición
     * @param  sqlWhereMap: valor del nombre del campo: valor del campo
     * @return  El primer elemento es la condición de SQL y el segundo elemento es el valor del marcador de posición en la condición de SQL
     */
    private List<Object> getSqlWhereWithValues(Map<String,Object> sqlWhereMap) {
        if (sqlWhereMap.size() <1 ) return null;
        List<Object> list = new ArrayList<Object>();
        List<Object> fieldValues = new ArrayList<Object>();
        StringBuffer sqlWhere = new StringBuffer(" where ");
        Set<Entry<String, Object>> entrySets = sqlWhereMap.entrySet();
        for (Iterator<Entry<String, Object>> iteraotr = entrySets.iterator();iteraotr.hasNext();) {
            Entry<String, Object> entrySet = iteraotr.next();
            fieldValues.add(entrySet.getValue());
            Object value = entrySet.getValue();
            if (value.getClass() == String.class) {
                sqlWhere.append(entrySet.getKey()).append(" like ").append("?").append(" and ");
            } else {
                sqlWhere.append(entrySet.getKey()).append("=").append("?").append(" and ");
            }
        }
        sqlWhere.delete(sqlWhere.lastIndexOf("and"), sqlWhere.length());
        list.add(sqlWhere.toString());
        list.add(fieldValues);
        return list;
    }


    /**
     * Obtener el nombre de la tabla
     */
    private String getTableName(Class<?> clazz) throws NotFoundAnnotationException {
        if (clazz.isAnnotationPresent(Entity.class)) {
            Entity entity = clazz.getAnnotation(Entity.class);
            return entity.value();
        } else {
            throw new NotFoundAnnotationException(clazz.getName() + " is not Entity Annotation.");
        }
    }

    /**
     * Establecer el valor del marcador de posición del parámetro SQL
     */
    private void setParameter(List<Object> values, PreparedStatement ps, boolean isSearch)
            throws SQLException {
        for (int i = 1; i <= values.size(); i++) {
            Object fieldValue = values.get(i-1);
            Class<?> clazzValue = fieldValue.getClass();
            if (clazzValue == String.class) {
                if (isSearch)
                    ps.setString(i, "%" + (String)fieldValue + "%");
                else
                    ps.setString(i,(String)fieldValue);

            } else if (clazzValue == boolean.class || clazzValue == Boolean.class) {
                ps.setBoolean(i, (Boolean)fieldValue);
            } else if (clazzValue == byte.class || clazzValue == Byte.class) {
                ps.setByte(i, (Byte)fieldValue);
            } else if (clazzValue == char.class || clazzValue == Character.class) {
                ps.setObject(i, fieldValue,Types.CHAR);
            } else if (clazzValue == Date.class) {
                ps.setTimestamp(i, new Timestamp(((Date) fieldValue).getTime()));
            } else if (clazzValue.isArray()) {
                Object[] arrayValue = (Object[]) fieldValue;
                StringBuffer sb = new StringBuffer();
                for (int j = 0; j < arrayValue.length; j++) {
                    sb.append(arrayValue[j]).append("、");
                }
                ps.setString(i, sb.deleteCharAt(sb.length()-1).toString());
            } else {
                ps.setObject(i, fieldValue, Types.NUMERIC);
            }
        }
    }
}
