/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.co.umintic.supermarket.controllers;

import com.co.umintic.supermarket.model.Categoria;
import com.co.umintic.supermarket.model.Pago;
import com.co.umintic.supermarket.model.PagoDetalle;
import com.co.umintic.supermarket.model.Persona;
import com.co.umintic.supermarket.model.Telefono;
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
public class PagoDetalleController {
    private HashMap<String, SqlType> columnas;
    private final String sqlConsulta = "SELECT * FROM pago_detalle";

    public PagoDetalleController() {
        llenarColumnas();
    }

    private void llenarColumnas() {
        columnas = new HashMap<>();
        columnas.put("id_pago", SqlType.INT);
        columnas.put("fecha", SqlType.DATE);
        columnas.put("valor_pagado", SqlType.INT);
        columnas.put("interes", SqlType.INT);
        columnas.put("capital", SqlType.INT);
    }
    
    public ArrayList<PagoDetalle> obtenerPagoDetallePorPago(Pago pago) throws ClassNotFoundException, SQLException {
        MySQLManager manager = new MySQLManager();
        ArrayList<HashMap<String,Object>> detalles = manager.EjecutarConsulta(agregarFiltroPorIdPago(pago.getId()), columnas);
        
        ArrayList<PagoDetalle> resultado = new ArrayList<>();
        
        for (HashMap<String,Object> detalle : detalles) {
            PagoDetalle pagoDet =  encapsularPagoDetalle(detalle, pago);
            
            resultado.add(pagoDet);
        }
        
        return resultado;    
    }

    private PagoDetalle encapsularPagoDetalle(HashMap<String, Object> pagoDetalle, Pago pago) throws ClassNotFoundException, SQLException {
        PagoDetalle pagoDet = new PagoDetalle();
        pagoDet.setIdPago((int)pagoDetalle.get("id_pago"));
        pagoDet.setFecha((Date)pagoDetalle.get("fecha"));
        pagoDet.setValorPagado((int)pagoDetalle.get("valor_pagado"));
        pagoDet.setInteres((int)pagoDetalle.get("interes"));
        pagoDet.setCapital((int)pagoDetalle.get("capital"));
        pagoDet.setPago(pago);
        
        return pagoDet;
    }

    private String agregarFiltroPorIdPago(int idPago) {
        return sqlConsulta + " WHERE id_pago = " + idPago;
    }
}
