/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.co.umintic.supermarket.controllers;

import com.co.umintic.supermarket.model.Categoria;
import com.co.umintic.supermarket.model.Cliente;
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
public class ClienteController {
    private HashMap<String, SqlType> columnas;
    private final String sqlConsulta = """
                                        SELECT
                                            c.*,
                                            pe.identificacion,
                                            pe.tipo_identificacion,
                                            pe.correo_electronico,
                                            pe.direccion
                                        FROM cliente as c
                                            inner join persona as pe
                                                on c.id_persona = pe.id
                                       """;

    public ClienteController() {
        llenarColumnas();
    }

    private void llenarColumnas() {
        columnas = new HashMap<>();
        columnas.put("id", SqlType.INT);
        columnas.put("nombre", SqlType.STRING);
        columnas.put("apellidos", SqlType.STRING);
        columnas.put("id_persona", SqlType.INT);
        columnas.put("identificacion", SqlType.BIG_INT);
        columnas.put("tipo_identificacion", SqlType.STRING);
        columnas.put("correo_electronico", SqlType.STRING);
        columnas.put("direccion", SqlType.STRING);
    }
    
    public ArrayList<Cliente> obtenerClientes() throws ClassNotFoundException, SQLException {
        MySQLManager manager = new MySQLManager();
        ArrayList<HashMap<String,Object>> clientes = manager.EjecutarConsulta(sqlConsulta, columnas);
        
        ArrayList<Cliente> resultado = new ArrayList<>();
        
        for (HashMap<String,Object> cliente : clientes) {
            Cliente cli = encapsularCliente(cliente);
            
            resultado.add(cli);
        }
        
        return resultado;    
    }
    
    public Cliente obtenerClientePorId(int id) throws ClassNotFoundException, SQLException {
        MySQLManager manager = new MySQLManager();
        HashMap<String,Object> cliente = manager.EjecutarConsulta(agregarFiltroPorId(id), columnas).get(0);
        
        return encapsularCliente(cliente);
    }

    private Cliente encapsularCliente(HashMap<String, Object> cliente) throws ClassNotFoundException, SQLException {
        Cliente cli = new Cliente();
        cli.setId((int)cliente.get("id"));
        cli.setNombre(cliente.get("nombre").toString());
        cli.setApellidos(cliente.get("apellidos").toString());
        cli.setIdPersona((int)cliente.get("id_persona"));
        cli.setCorreoElectronico(cliente.get("correo_electronico").toString());
        cli.setDireccion(cliente.get("direccion").toString());
        cli.setIdentificacion((long)cliente.get("identificacion"));
        cli.setTipoIdentificacion(cliente.get("tipo_identificacion").toString());
        
        ArrayList<Telefono> telefonos = new TelefonoController().obtenerTelefonosPorPersona(cli);
        cli.setTelefonos(telefonos);
        
        ArrayList<Venta> compras = new VentaController().obtenerVentasPorCliente(cli);
        cli.setCompras(compras);
        
        return cli;
    }

    private String agregarFiltroPorId(int id) {
        return sqlConsulta + " WHERE c.id = " + id;
    }
}
