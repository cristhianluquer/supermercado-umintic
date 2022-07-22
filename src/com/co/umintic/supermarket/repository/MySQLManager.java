/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.co.umintic.supermarket.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.sql.PreparedStatement;
import java.sql.Date;

/**
 *
 * @author crist
 */
public class MySQLManager {
    private Connection connection;
    
    public MySQLManager() throws ClassNotFoundException, SQLException {
        connection = new Conexion().getConection();
    }
    
    public ArrayList<HashMap<String,Object>> EjecutarConsulta(String sql, HashMap<String, SqlType> columnas) throws SQLException {
        Statement statement = connection.createStatement();
        
        ResultSet rs = statement.executeQuery(sql);
        
        ArrayList<HashMap<String,Object>> resultado= new ArrayList<>();
        
        while (rs.next()) {
            HashMap<String, Object> dato = extraerColumnas(columnas, rs);
            
            resultado.add(dato);
        }
        
        connection.close();
        
        return resultado;
    }
    
    public int EjecutarCreacion(String sql, ArrayList<HashMap<Object, SqlType>> datos) throws SQLException {
        return EjecutarCreacion(sql, datos, true);
    }
    
    public int EjecutarCreacion(String sql, ArrayList<HashMap<Object, SqlType>> datos, boolean obtenerId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        
        agregarValoresAlInsert(datos, statement);
        
        int cantidadDeRegistros = statement.executeUpdate();
        
        if (cantidadDeRegistros == 0) {
            throw new SQLException("No se guardó en DB");
        }
        
        ResultSet rs = statement.getGeneratedKeys();
        
        if (rs.next()) {
            int id = rs.getInt(1);
            connection.close();
            return id;
        }
        
        if (obtenerId) {
            throw new SQLException("No se obtuvo un ID");
        }
        return 0;
    }
    
    public void EjecutarActualizacion(String sql, ArrayList<HashMap<Object, SqlType>> datos) throws SQLException {
        EjecutarCreacion(sql, datos, false);
    }

    private void agregarValoresAlInsert(ArrayList<HashMap<Object, SqlType>> datos, PreparedStatement statement) throws SQLException {
        int index = 1;
        for (HashMap<Object, SqlType> dato : datos) {
            for (Map.Entry<Object, SqlType> entry : dato.entrySet()) {
                Object valor = entry.getKey();
                SqlType tipoDato = entry.getValue();
                
                switch (tipoDato){
                    case BIG_INT:
                        statement.setLong(index, (long)valor);
                        break;
                    case DATE:
                        statement.setDate(index, (Date)valor);
                        break;
                    case INT:
                        statement.setInt(index, (int)valor);
                        break;
                    case SMALL_INT:
                        statement.setShort(index, (short)valor);
                        break;
                    case STRING:
                        statement.setString(index, valor.toString());
                        break;
                }
            }
            index++;
        }
    }

    private HashMap<String, Object> extraerColumnas(HashMap<String, SqlType> columnas, ResultSet rs) throws SQLException {
        HashMap<String, Object> dato = new HashMap<>();
        for (Map.Entry<String,SqlType> entry : columnas.entrySet()) {
            String nombreColumna = entry.getKey();
            SqlType tipoDatoColumna= entry.getValue();
            
            Object valorEnDB = obtenerValorColumna(tipoDatoColumna, rs, nombreColumna);
            
            dato.put(nombreColumna, valorEnDB);
        }
        return dato;
    }

    private Object obtenerValorColumna(SqlType tipoDatoColumna, ResultSet rs, String nombreColumna) throws SQLException {
        Object valorEnDB = null;
        switch(tipoDatoColumna) {
            case INT:
                valorEnDB = rs.getInt(nombreColumna);
                break;
            case DATE:
                valorEnDB = rs.getDate(nombreColumna);
                break;
            case STRING:
                valorEnDB = rs.getString(nombreColumna);
                break;
            case BIG_INT:
                valorEnDB = rs.getLong(nombreColumna);
                break;
            case SMALL_INT:
                valorEnDB = rs.getShort(nombreColumna);
                break;
        }
        return valorEnDB;
    }
}
