/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.co.umintic.supermarket.controllers;

import com.co.umintic.supermarket.repository.SqlType;
import java.util.HashMap;

/**
 *
 * @author crist
 */
public class BaseController {
    protected HashMap<Object, SqlType> crearHashMapDatos(Object dato, SqlType sqlType) {
        HashMap<Object, SqlType> retorno= new HashMap<>();
        retorno.put(dato, sqlType);
        return retorno;
    }
}
