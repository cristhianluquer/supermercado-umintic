/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.co.umintic.supermarket.controllers;

import com.co.umintic.supermarket.model.Categoria;
import com.co.umintic.supermarket.model.Persona;
import com.co.umintic.supermarket.model.Telefono;
import com.co.umintic.supermarket.repository.MySQLManager;
import com.co.umintic.supermarket.repository.SqlType;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author crist
 */
public class TelefonoController {
    private HashMap<String, SqlType> columnas;
    private final String sqlConsulta = "SELECT * FROM telefono";

    public TelefonoController() {
        llenarColumnas();
    }

    private void llenarColumnas() {
        columnas = new HashMap<>();
        columnas.put("id_persona", SqlType.INT);
        columnas.put("numero", SqlType.BIG_INT);
    }
    
    public ArrayList<Telefono> obtenerTelefonosPorPersona(Persona persona) throws ClassNotFoundException, SQLException {
        MySQLManager manager = new MySQLManager();
        ArrayList<HashMap<String,Object>> telefonos = manager.EjecutarConsulta(agregarFiltroPorIdPersona(persona.getIdPersona()), columnas);
        
        ArrayList<Telefono> resultado = new ArrayList<>();
        
        for (HashMap<String,Object> telefono : telefonos) {
            Telefono tel=  encapsularTelefono(telefono, persona);
            
            resultado.add(tel);
        }
        
        return resultado;    
    }

    private Telefono encapsularTelefono(HashMap<String, Object> telefono, Persona persona) throws ClassNotFoundException, SQLException {
        Telefono tel = new Telefono();
        tel.setIdPersona((int)telefono.get("id_persona"));
        tel.setNumero((long)telefono.get("numero"));
        tel.setPersona(persona);
        
        return tel;
    }

    private String agregarFiltroPorIdPersona(int id) {
        return sqlConsulta + " WHERE id_persona = " + id;
    }
}
