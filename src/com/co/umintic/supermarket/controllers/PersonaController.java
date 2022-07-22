/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.co.umintic.supermarket.controllers;

import com.co.umintic.supermarket.model.Categoria;
import com.co.umintic.supermarket.model.Cliente;
import com.co.umintic.supermarket.model.Persona;
import com.co.umintic.supermarket.model.Telefono;
import com.co.umintic.supermarket.model.Venta;
import com.co.umintic.supermarket.repository.MySQLManager;
import com.co.umintic.supermarket.repository.SqlType;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author crist
 */
public class PersonaController extends BaseController {
    private final String sqlCreate = """
                                     INSERT INTO persona (identificacion, tipo_identificacion, correo_electronico, direccion)
                                     VALUES (?,?,?,?);
                                     """;
    private final String sqlCreateTelefono = """
                                     INSERT INTO telefono (id_persona, numero)
                                     VALUES (?,?);
                                     """;
    
    
    public Persona crearPersona(Persona persona) throws ClassNotFoundException, SQLException {
        MySQLManager manager = new MySQLManager();
        int idPersona = manager.EjecutarCreacion(sqlCreate, extraerDatos(persona));
        
        persona.setIdPersona(idPersona);
        
        crearTelefonos(persona);
        
        return persona;
    }
    
    private ArrayList<HashMap<Object, SqlType>> extraerDatos(Persona persona) {
        ArrayList<HashMap<Object, SqlType>> resultado = new ArrayList<>();
        
        resultado.add(crearHashMapDatos(persona.getIdentificacion(), SqlType.BIG_INT));
        resultado.add(crearHashMapDatos(persona.getTipoIdentificacion(), SqlType.STRING));
        resultado.add(crearHashMapDatos(persona.getCorreoElectronico(), SqlType.STRING));
        resultado.add(crearHashMapDatos(persona.getDireccion(), SqlType.STRING));
        
        return resultado;
    }

    private void crearTelefonos(Persona persona) throws ClassNotFoundException, SQLException {
        ArrayList<Telefono> telefonos = persona.getTelefonos();
        if (telefonos == null || telefonos.isEmpty()) {
            return;
        }
        
        MySQLManager manager;
        
        for (Telefono telefono : telefonos) {
            manager = new MySQLManager();
            telefono.setIdPersona(persona.getIdPersona());
            manager.EjecutarCreacion(sqlCreateTelefono, extraerDatosTelefono(telefono), false);
        }
    }

    private ArrayList<HashMap<Object, SqlType>> extraerDatosTelefono(Telefono telefono) {
        ArrayList<HashMap<Object, SqlType>> resultado = new ArrayList<>();
        
        resultado.add(crearHashMapDatos(telefono.getIdPersona(), SqlType.INT));
        resultado.add(crearHashMapDatos(telefono.getNumero(), SqlType.BIG_INT));
        
        return resultado;
    }
}
