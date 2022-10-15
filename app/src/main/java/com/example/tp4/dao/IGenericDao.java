package com.example.tp4.dao;

import com.example.tp4.exception.NotFoundAnnotationException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IGenericDao<T> {

    public void save(T t) throws Exception, NotFoundAnnotationException;

    public void delete(Object id,Class<T> clazz) throws Exception, NotFoundAnnotationException;

    public void update(T t) throws Exception, NotFoundAnnotationException;

    public T get(Object id,Class<T> clazz) throws Exception, NotFoundAnnotationException;
    /**
     * Consultar según condiciones
     * @param select: los valores que quieres traer en la consulta, ejemplo: id,nombre ó *
     * @param table: el nombre de la tabla, también se puede hacer un join acá
     * @param where: acá van las sentencias del where
     * @return
     * @throws Exception
     */
    public ResultSet selectByQuery(String select, String table,String where) throws SQLException;

    /**
     * Consultar según condiciones
     * @param sqlWhereMap: valor del nombre del campo de condición: valor del campo de condición
     * @param clazz
     * @return
     * @throws Exception
     */
    public List<T> findAllByConditions(Map<String,Object> sqlWhereMap,Class<T> clazz) throws Exception, NotFoundAnnotationException;

}
