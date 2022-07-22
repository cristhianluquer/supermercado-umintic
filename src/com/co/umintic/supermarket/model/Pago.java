/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.co.umintic.supermarket.model;

import java.util.ArrayList;

/**
 *
 * @author crist
 */
public class Pago {
    private int id;
    private int idVenta;
    private short tipoPago;
    private int interes;
    private short cuotas;
    private Venta venta;
    private ArrayList<PagoDetalle> pagoDetalle;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public short getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(short tipoPago) {
        this.tipoPago = tipoPago;
    }

    public int getInteres() {
        return interes;
    }

    public void setInteres(int interes) {
        this.interes = interes;
    }

    public short getCuotas() {
        return cuotas;
    }

    public void setCuotas(short cuotas) {
        this.cuotas = cuotas;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public ArrayList<PagoDetalle> getPagoDetalle() {
        return pagoDetalle;
    }

    public void setPagoDetalle(ArrayList<PagoDetalle> pagoDetalle) {
        this.pagoDetalle = pagoDetalle;
    }
}
