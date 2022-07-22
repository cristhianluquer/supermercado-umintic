
package com.co.umintic.supermarket.repository;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private final String USER = "root";
    private final String PASSWORD = "";
    private final String HOST = "jdbc:mysql://localhost:3306/";
    private final String DB_NAME = "supermercado";
    
    private Connection conexion_bd;
    
    public Connection getConection() throws ClassNotFoundException, SQLException {
        if (this.conexion_bd == null) {
            this.establecerConexion();
        }
        
        return this.conexion_bd;
    }
    
    private void establecerConexion() throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER);
        this.conexion_bd = DriverManager.getConnection(HOST + DB_NAME,USER,PASSWORD);
    }
}
