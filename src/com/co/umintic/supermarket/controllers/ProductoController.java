/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.co.umintic.supermarket.controllers;

import com.co.umintic.supermarket.model.Categoria;
import com.co.umintic.supermarket.model.Producto;
import com.co.umintic.supermarket.model.Proveedor;
import com.co.umintic.supermarket.model.Venta;
import com.co.umintic.supermarket.repository.MySQLManager;
import com.co.umintic.supermarket.repository.SqlType;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author crist
 */
public class ProductoController extends BaseController {
    private HashMap<String, SqlType> columnas;
    private final String sqlCreate = """
                                     INSERT INTO producto (id_proveedor, id_categoria, nombre, precio_actual, stock)
                                     VALUES (?, ?, ?, ?, ?)
                                     """;
    private final String sqlRead = "SELECT * FROM producto";
    private final String sqlReadPorVenta = """
                                                SELECT
                                                   p.*
                                                FROM
                                                    producto as p
                                                INNER JOIN
                                                    venta_producto as vp
                                                        ON vp.id_producto = p.id
                                               """;
    private final String sqlUpdateStock = """
                                          UPDATE producto
                                          SET stock = stock - ?
                                          WHERE id = ?;
                                          """;

    public ProductoController() {
        llenarColumnas();
    }

    private void llenarColumnas() {
        columnas = new HashMap<>();
        columnas.put("id", SqlType.INT);
        columnas.put("id_proveedor", SqlType.INT);
        columnas.put("id_categoria", SqlType.INT);
        columnas.put("nombre", SqlType.STRING);
        columnas.put("precio_actual", SqlType.INT);
        columnas.put("stock", SqlType.INT);
    }
    
    public ArrayList<Producto> obtenerProductos() throws ClassNotFoundException, SQLException {
        return obtenerProductosPorSql(sqlRead, null, null);
    }
    
    public ArrayList<Producto> obtenerProductosPorProveedor(Proveedor proveedor, Venta venta) throws ClassNotFoundException, SQLException {
        return obtenerProductosPorSql(agregarFiltroPorIdProveedor(proveedor.getIdPersona()), proveedor, venta);
    }
    
    public ArrayList<Producto> obtenerProductosPorVenta(Venta venta) throws ClassNotFoundException, SQLException {
        return obtenerProductosPorSql(agregarFiltroPorIdVenta(venta.getId()), null, venta);
    }

    public Producto crearProducto(Producto producto) throws ClassNotFoundException, SQLException {
        MySQLManager manager = new MySQLManager();
        int idProducto = manager.EjecutarCreacion(sqlCreate, extraerDatos(producto));
        
        producto.setId(idProducto);
        
        return producto;
    }
    
    private ArrayList<Producto> obtenerProductosPorSql(String sql, Proveedor proveedor, Venta venta) throws ClassNotFoundException, SQLException {
        MySQLManager manager = new MySQLManager();
        ArrayList<HashMap<String,Object>> productos = manager.EjecutarConsulta(sql, columnas);
        
        ArrayList<Producto> resultado = new ArrayList<>();
        
        for (HashMap<String,Object> producto : productos) {
            Producto prod= encapsularProducto(producto, proveedor, venta);
            
            resultado.add(prod);
        }
        
        return resultado;
    }

    private String agregarFiltroPorIdProveedor(int idProveedor) {
        return sqlRead + " WHERE id_proveedor = " + idProveedor;
    }
    
    private String agregarFiltroPorIdVenta(int idVenta) {
        return sqlReadPorVenta +" WHERE vp.id_venta = " + idVenta;
    }

    private Producto encapsularProducto(HashMap<String, Object> producto, Proveedor proveedor, Venta venta) throws ClassNotFoundException, SQLException {
        Producto prod = new Producto();
        prod.setId((int)producto.get("id"));
        prod.setIdProveedor((int)producto.get("id_proveedor"));
        prod.setIdCategoria((int)producto.get("id_categoria"));
        prod.setNombre(producto.get("nombre").toString());
        prod.setPrecioActual((int)producto.get("precio_actual"));
        prod.setStock((int)producto.get("stock"));
        
        Categoria categoria = new CategoriaController().obtenerCategoriaPorId(prod.getIdCategoria());
        prod.setCategoria(categoria);
        
        if (proveedor == null) {
            proveedor = new ProveedorController().obtenerProveedorPorId(prod.getIdProveedor(), venta);
        }
        
        prod.setProveedor(proveedor);
        
        if (venta == null) {
            venta = new VentaController().obtenerVentaPorProducto(prod);
        }
        
        if (venta != null) {
            prod.getVentas().add(venta);
        }
        return prod;
    }

    private ArrayList<HashMap<Object, SqlType>> extraerDatos(Producto producto) {
        ArrayList<HashMap<Object, SqlType>> resultado = new ArrayList<>();
        
        resultado.add(crearHashMapDatos(producto.getIdProveedor(), SqlType.INT));
        resultado.add(crearHashMapDatos(producto.getIdCategoria(), SqlType.INT));
        resultado.add(crearHashMapDatos(producto.getNombre(), SqlType.STRING));
        resultado.add(crearHashMapDatos(producto.getPrecioActual(), SqlType.INT));
        resultado.add(crearHashMapDatos(producto.getStock(), SqlType.INT));
        
        return resultado;
    }

    protected void actualizarStockProductos(HashMap<Integer, Integer> productosVendidos) throws SQLException, ClassNotFoundException {
        MySQLManager manager;
        
        for (Map.Entry<Integer, Integer> entry : productosVendidos.entrySet()) {
            int idProducto = entry.getKey();
            int cantidad = entry.getValue();
            
            manager = new MySQLManager();
            manager.EjecutarActualizacion(sqlUpdateStock, extraerDatosUpdateStock(idProducto, cantidad));
        }
    }

    private ArrayList<HashMap<Object, SqlType>> extraerDatosUpdateStock(int idProducto, int cantidad) {
        ArrayList<HashMap<Object, SqlType>> resultado = new ArrayList<>();
        
        resultado.add(crearHashMapDatos(cantidad, SqlType.INT));
        resultado.add(crearHashMapDatos(idProducto, SqlType.INT));
        
        return resultado;
    }
}
