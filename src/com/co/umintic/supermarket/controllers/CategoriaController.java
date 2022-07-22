/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.co.umintic.supermarket.controllers;

import com.co.umintic.supermarket.model.Categoria;
import com.co.umintic.supermarket.repository.MySQLManager;
import com.co.umintic.supermarket.repository.SqlType;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author crist
 */
public class CategoriaController {
    private HashMap<String, SqlType> columnas;
    private final String sqlRead = "SELECT * FROM categoria";

    public CategoriaController() {
        llenarColumnas();
    }

    private void llenarColumnas() {
        columnas = new HashMap<>();
        columnas.put("id", SqlType.INT);
        columnas.put("nombre", SqlType.STRING);
        columnas.put("descripcion", SqlType.STRING);
    }
    
    public ArrayList<Categoria> obtenerCategorias() throws ClassNotFoundException, SQLException {
        MySQLManager manager = new MySQLManager();
        ArrayList<HashMap<String,Object>> categorias = manager.EjecutarConsulta(sqlRead, columnas);
        
        ArrayList<Categoria> resultado = new ArrayList<>();
        
        for (HashMap<String,Object> categoria : categorias) {
            Categoria cat = encapsularCategoria(categoria);
            
            resultado.add(cat);
        }
        
        return resultado;    
    }
    
    public Categoria obtenerCategoriaPorId(int id) throws ClassNotFoundException, SQLException {
        MySQLManager manager = new MySQLManager();
        HashMap<String,Object> categoria = manager.EjecutarConsulta(agregarFiltroPorId(id), columnas).get(0);
        
        return encapsularCategoria(categoria);
    }

    private Categoria encapsularCategoria(HashMap<String, Object> categoria) throws ClassNotFoundException, SQLException {
        Categoria cat = new Categoria();
        cat.setId((int)categoria.get("id"));
        cat.setNombre(categoria.get("nombre").toString());
        
        Object descripcion = categoria.get("descripcion");
        if (descripcion != null) {
            cat.setDescripcion(descripcion.toString());
        }
        
        return cat;
    }

    private String agregarFiltroPorId(int id) {
        return sqlRead + " WHERE id = " + id;
    }
}
