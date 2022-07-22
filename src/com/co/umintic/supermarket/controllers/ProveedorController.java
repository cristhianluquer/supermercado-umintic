/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.co.umintic.supermarket.controllers;

import com.co.umintic.supermarket.model.Producto;
import com.co.umintic.supermarket.model.Proveedor;
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
public class ProveedorController extends BaseController {
    private HashMap<String, SqlType> columnas;
    private final String sqlCreate = """
                                     INSERT INTO proveedor(id_persona, razon_social, representante_legal, sitio_web)
                                     VALUES (?,?,?,?);
                                     """;
    private final String sqlRead = """
                                        SELECT
                                            p.*,
                                            pe.identificacion,
                                            pe.tipo_identificacion,
                                            pe.correo_electronico,
                                            pe.direccion
                                        FROM proveedor as p
                                            inner join persona as pe
                                                on p.id_persona = pe.id
                                       """;

    public ProveedorController() {
        llenarColumnas();
    }

    private void llenarColumnas() {
        columnas = new HashMap<>();
        columnas.put("id", SqlType.INT);
        columnas.put("id_persona", SqlType.INT);
        columnas.put("razon_social", SqlType.STRING);
        columnas.put("representante_legal", SqlType.STRING);
        columnas.put("sitio_web", SqlType.STRING);
        columnas.put("identificacion", SqlType.BIG_INT);
        columnas.put("tipo_identificacion", SqlType.STRING);
        columnas.put("correo_electronico", SqlType.STRING);
        columnas.put("direccion", SqlType.STRING);
    }
    
    public ArrayList<Proveedor> obtenerProveedores() throws ClassNotFoundException, SQLException {
        MySQLManager manager = new MySQLManager();
        ArrayList<HashMap<String,Object>> proveedores = manager.EjecutarConsulta(sqlRead, columnas);
        
        ArrayList<Proveedor> resultado = new ArrayList<>();
        
        for (HashMap<String,Object> proveedor : proveedores) {
            Proveedor prov = encapsularProveedor(proveedor, null);
            
            resultado.add(prov);
        }
        
        return resultado;    
    }
    
    public Proveedor obtenerProveedorPorId(int id, Venta venta) throws ClassNotFoundException, SQLException {
        MySQLManager manager = new MySQLManager();
        HashMap<String,Object> proveedor = manager.EjecutarConsulta(agregarFiltroPorId(id), columnas).get(0);
        
        return encapsularProveedor(proveedor, venta);
    }
    
    public Proveedor crearProveedor(Proveedor proveedor) throws ClassNotFoundException, SQLException {
        crearPersona(proveedor);
        
        MySQLManager manager = new MySQLManager();
        int idProveedor = manager.EjecutarCreacion(sqlCreate, extraerDatos(proveedor));
        
        proveedor.setId(idProveedor);
        
        return proveedor;
    }
    
    private ArrayList<HashMap<Object, SqlType>> extraerDatos(Proveedor proveedor) {
        ArrayList<HashMap<Object, SqlType>> resultado = new ArrayList<>();
        
        resultado.add(crearHashMapDatos(proveedor.getIdPersona(), SqlType.INT));
        resultado.add(crearHashMapDatos(proveedor.getRazonSocial(), SqlType.STRING));
        resultado.add(crearHashMapDatos(proveedor.getRepresentanteLegal(), SqlType.STRING));
        resultado.add(crearHashMapDatos(proveedor.getSitioWeb(), SqlType.STRING));
        
        return resultado;
    }

    private Proveedor encapsularProveedor(HashMap<String, Object> proveedor, Venta venta) throws ClassNotFoundException, SQLException {
        Proveedor prov = new Proveedor();
        prov.setId((int)proveedor.get("id"));
        prov.setIdPersona((int)proveedor.get("id_persona"));
        prov.setCorreoElectronico(proveedor.get("correo_electronico").toString());
        prov.setDireccion(proveedor.get("direccion").toString());
        prov.setIdentificacion((long)proveedor.get("identificacion"));
        prov.setRazonSocial(proveedor.get("razon_social").toString());
        prov.setRepresentanteLegal(proveedor.get("representante_legal").toString());
        prov.setTipoIdentificacion(proveedor.get("tipo_identificacion").toString());
        
        Object sitioWeb = proveedor.get("sitio_web");
        if (sitioWeb != null) {
            prov.setSitioWeb(proveedor.get("sitio_web").toString());
        }
        
        ArrayList<Producto> productos = new ProductoController().obtenerProductosPorProveedor(prov, venta);
        prov.setProductos(productos);
        
        ArrayList<Telefono> telefonos = new TelefonoController().obtenerTelefonosPorPersona(prov);
        prov.setTelefonos(telefonos);
        
        return prov;
    }

    private String agregarFiltroPorId(int id) {
        return sqlRead + " WHERE p.id = " + id;
    }

    private void crearPersona(Proveedor proveedor) throws SQLException, ClassNotFoundException {
        PersonaController controller = new PersonaController();
        controller.crearPersona(proveedor);
    }
}
