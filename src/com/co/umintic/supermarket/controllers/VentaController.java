/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.co.umintic.supermarket.controllers;

import com.co.umintic.supermarket.model.Categoria;
import com.co.umintic.supermarket.model.Cliente;
import com.co.umintic.supermarket.model.Pago;
import com.co.umintic.supermarket.model.Producto;
import com.co.umintic.supermarket.model.Venta;
import com.co.umintic.supermarket.model.Proveedor;
import com.co.umintic.supermarket.repository.MySQLManager;
import com.co.umintic.supermarket.repository.SqlType;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author crist
 */
public class VentaController extends BaseController {
    private HashMap<String, SqlType> columnas;
    private final String sqlCreate = """
                                     INSERT INTO venta(id_cliente, descuento, total)
                                     VALUES (?,?,?);
                                     """;
    private final String sqlCreateVentaProducto = """
                                                  INSERT INTO venta_producto(id_venta, id_producto)
                                                  VALUES (?,?);
                                                  """;
    private final String sqlRead = "SELECT * FROM venta";
    private final String sqlReadPorProducto = """
                                                SELECT
                                                   v.*
                                                FROM
                                                    venta as v
                                                INNER JOIN
                                                    venta_producto as vp
                                                        ON vp.id_venta = v.id
                                               """;

    public VentaController() {
        llenarColumnas();
    }

    private void llenarColumnas() {
        columnas = new HashMap<>();
        columnas.put("id", SqlType.INT);
        columnas.put("id_cliente", SqlType.INT);
        columnas.put("fecha", SqlType.DATE);
        columnas.put("descuento", SqlType.INT);
        columnas.put("total", SqlType.INT);
    }
    
    public ArrayList<Venta> obtenerVentas() throws ClassNotFoundException, SQLException {
        return obtenerVentasPorSql(sqlRead, null, null, null);
    }
    
    public ArrayList<Venta> obtenerVentasPorCliente(Cliente cliente) throws ClassNotFoundException, SQLException {
        return obtenerVentasPorSql(agregarFiltroPorIdCliente(cliente.getId()), cliente, null, null);
    }
    
    public Venta obtenerVentaPorProducto(Producto producto) throws ClassNotFoundException, SQLException {
        ArrayList<Venta> ventas = obtenerVentasPorSql(agregarFiltroPorIdProducto(producto.getId()), null, null, producto);
        if (!ventas.isEmpty()) {
            return ventas.get(0);
        }
        return null;
    }
    
    public Venta obtenerVentaPorPago(Pago pago) throws ClassNotFoundException, SQLException {
        return obtenerVentasPorSql(agregarFiltroPorId(pago.getIdVenta()), null, pago, null).get(0);
    }
    
    public Venta crearVenta(Venta venta) throws ClassNotFoundException, SQLException {
        if (venta.getTotal() == 0) {
            throw new IllegalArgumentException("La venta no puede realizarse por $0");
        }
        
        MySQLManager manager = new MySQLManager();
        int idVenta = manager.EjecutarCreacion(sqlCreate, extraerDatos(venta));
        
        venta.setId(idVenta);
        
        guardarPago(venta);
        guardarProductos(venta);
        
        return venta;
    }
    
    private ArrayList<HashMap<Object, SqlType>> extraerDatos(Venta venta) {
        ArrayList<HashMap<Object, SqlType>> resultado = new ArrayList<>();
        
        resultado.add(crearHashMapDatos(venta.getCliente().getId(), SqlType.INT));
        resultado.add(crearHashMapDatos(venta.getDescuento(), SqlType.INT));
        resultado.add(crearHashMapDatos(venta.getTotal(), SqlType.INT));
        
        return resultado;
    }

    private ArrayList<Venta> obtenerVentasPorSql(String sql, Cliente cliente, Pago pago, Producto producto) throws ClassNotFoundException, SQLException {
        MySQLManager manager = new MySQLManager();
        ArrayList<HashMap<String,Object>> ventas = manager.EjecutarConsulta(sql, columnas);
        
        ArrayList<Venta> resultado = new ArrayList<>();
        
        for (HashMap<String,Object> venta : ventas) {
            Venta ven= encapsularVenta(venta, cliente, pago, producto);
            
            resultado.add(ven);
        }
        
        return resultado;
    }

    private Venta encapsularVenta(HashMap<String, Object> venta, Cliente cliente, Pago pago, Producto producto) throws ClassNotFoundException, SQLException {
        Venta ven = new Venta();
        ven.setId((int)venta.get("id"));
        ven.setIdCliente((int)venta.get("id_cliente"));
        ven.setDescuento((int)venta.get("descuento"));
        ven.setTotal((int)venta.get("total"));
        ven.setFecha((Date)venta.get("fecha"));
        
        if (pago == null) {
            pago = new PagoController().obtenerPagoPorVenta(ven);
        }
        
        ven.setPago(pago);
        
        if (cliente == null) {
            cliente = new ClienteController().obtenerClientePorId(ven.getIdCliente());
        }
        
        ven.setCliente(cliente);
        
        if (producto == null) {
            ArrayList<Producto> productos = new ProductoController().obtenerProductosPorVenta(ven);
            ven.setProductos(productos);
            return ven;
        }
        
        ven.getProductos().add(producto);
        
        return ven;
    }

    private String agregarFiltroPorIdCliente(int idCliente) {
        return sqlRead + " WHERE id_cliente = " + idCliente;
    }

    private String agregarFiltroPorId(int id) {
        return sqlRead + " WHERE id = " + id;
    }
    
    private String agregarFiltroPorIdProducto(int idProducto) {
        return sqlReadPorProducto+ " WHERE vp.id_producto = " + idProducto;
    }

    private void guardarPago(Venta venta) throws ClassNotFoundException, SQLException {
        Pago pago = venta.getPago();
        if (pago == null || pago.getTipoPago() == 0) {
            throw new IllegalArgumentException("La venta no tiene pago asociado!");
        }
        
        PagoController controller = new PagoController();
        pago.setVenta(venta);
        pago.setIdVenta(venta.getId());
        controller.registrarPago(pago);
    }

    private void guardarProductos(Venta venta) throws ClassNotFoundException, SQLException {
        ArrayList<Producto> productos = venta.getProductos();
        if (productos == null || productos.isEmpty()) {
            throw new IllegalArgumentException("La venta debe tener al menos 1 producto!");
        }
        
        int idVenta = venta.getId();
        
        // Este objeto es un acumulador de productos. 1 producto = 3 vendidos
        // lo cual puedo usar para restar directamente al stock -3, no de a 1
        HashMap<Integer, Integer> productosVendidos = new HashMap<>();
        
        for (Producto producto : productos) {
            int idProducto = producto.getId();
            
            productosVendidos.put(idProducto, productosVendidos.getOrDefault(idProducto, 0) + 1);
            
            registrarProductoVendido(idVenta, idProducto);
        }
        
        ProductoController controller = new ProductoController();
        controller.actualizarStockProductos(productosVendidos);
    }

    private void registrarProductoVendido(int idVenta, int idProducto) throws ClassNotFoundException, SQLException {
        MySQLManager manager = new MySQLManager();
        manager.EjecutarCreacion(sqlCreateVentaProducto, extraerDatosVentaProducto(idVenta, idProducto), false);
    }
    
    private ArrayList<HashMap<Object, SqlType>> extraerDatosVentaProducto(int idVenta, int idProducto) {
        ArrayList<HashMap<Object, SqlType>> resultado = new ArrayList<>();
        
        resultado.add(crearHashMapDatos(idVenta, SqlType.INT));
        resultado.add(crearHashMapDatos(idProducto, SqlType.INT));
        
        return resultado;
    }
}
