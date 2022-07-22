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
public class Cliente extends Persona {
    private int id;
    private String nombre;
    private String apellidos;
    private ArrayList<Venta> compras;

    public ArrayList<Venta> getCompras() {
        return compras;
    }

    public void setCompras(ArrayList<Venta> compras) {
        this.compras = compras;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
}
