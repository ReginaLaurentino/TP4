package com.example.tp4.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Proporcionar una interfaz para obtener conexiones de bases de datos y liberar recursos.
 */
public class DaoHelper {

    //Información de la BD
    public static final String HOST="sql10.freesqldatabase.com";
    public static final String PORT="3306";
    public static final String NAMEBD="sql10526126";
    private static final String USER = "sql10526126";
    private static final String PASSWORD = "vnmXjBgH5s";

    //Información para la conexion
    private static final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/"+NAMEBD;
    public static final String DRIVER = "com.mysql.jdbc.Driver";

    private static Connection conn;

    /**
     * Obtener un objeto de conexión a la base de datos
     * @return  instancia java.sql.Connection
     */
    public static Connection getConnection() {
        try {
            if (conn == null) {
                Class.forName(DRIVER);
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
            } else {
                return conn;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * Liberar recursos de la base de datos
     */
    public static void release(PreparedStatement ps,ResultSet rs) {
        try {
            if (conn != null) {
                conn.close();
                conn = null;
            }
            if (ps != null) {
                ps.close();
                ps = null;
            }
            if (rs != null) {
                rs.close();
                rs = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
