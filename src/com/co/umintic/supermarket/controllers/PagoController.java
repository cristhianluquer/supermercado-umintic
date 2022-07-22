/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.co.umintic.supermarket.controllers;

import com.co.umintic.supermarket.model.Categoria;
import com.co.umintic.supermarket.model.Pago;
import com.co.umintic.supermarket.model.PagoDetalle;
import com.co.umintic.supermarket.model.Producto;
import com.co.umintic.supermarket.model.Proveedor;
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
public class PagoController extends BaseController {
    private HashMap<String, SqlType> columnas;
    private final String sqlCreate = """
                                     INSERT INTO pago (id_venta, tipo_pago, interes, cuotas)
                                     VALUES (?,?,?,?);
                                     """;
    private final String sqlRead = "SELECT * FROM pago";

    public PagoController() {
        llenarColumnas();
    }

    private void llenarColumnas() {
        columnas = new HashMap<>();
        columnas.put("id", SqlType.INT);
        columnas.put("id_venta", SqlType.INT);
        columnas.put("tipo_pago", SqlType.SMALL_INT);
        columnas.put("interes", SqlType.INT);
        columnas.put("cuotas", SqlType.SMALL_INT);
    }
    
    public Pago obtenerPagoPorVenta(Venta venta) throws ClassNotFoundException, SQLException {
        MySQLManager manager = new MySQLManager();
        HashMap<String,Object> pago = manager.EjecutarConsulta(agregarFiltroPorIdVenta(venta.getId()), columnas).get(0);
        
        return encapsularPago(pago, venta);
    }

    private String agregarFiltroPorIdVenta(int idVenta) {
        return sqlRead + " WHERE id_venta= " + idVenta;
    }

    private Pago encapsularPago(HashMap<String, Object> pago, Venta venta) throws ClassNotFoundException, SQLException {
        Pago pag = new Pago();
        pag.setId((int)pago.get("id"));
        pag.setIdVenta((int)pago.get("id_venta"));
        pag.setTipoPago((short)pago.get("tipo_pago"));
        pag.setInteres((int)pago.get("interes"));
        pag.setCuotas((Short)pago.get("cuotas"));
        
        if (venta == null) {
            venta = new VentaController().obtenerVentaPorPago(pag);
        }
        
        pag.setVenta(venta);
        
        ArrayList<PagoDetalle> pagoDetalle = new PagoDetalleController().obtenerPagoDetallePorPago(pag);
        pag.setPagoDetalle(pagoDetalle);
        
        return pag;
    }

    protected void registrarPago(Pago pago) throws ClassNotFoundException, SQLException {
        MySQLManager manager = new MySQLManager();
        int idPago = manager.EjecutarCreacion(sqlCreate, extraerDatos(pago));
        
        pago.setId(idPago);
    }
    
    private ArrayList<HashMap<Object, SqlType>> extraerDatos(Pago pago) {
        ArrayList<HashMap<Object, SqlType>> resultado = new ArrayList<>();
        
        resultado.add(crearHashMapDatos(pago.getVenta().getId(), SqlType.INT));
        resultado.add(crearHashMapDatos(pago.getTipoPago(), SqlType.SMALL_INT));
        resultado.add(crearHashMapDatos(pago.getInteres(), SqlType.INT));
        resultado.add(crearHashMapDatos(pago.getCuotas(), SqlType.SMALL_INT));
        
        return resultado;
    }
}
