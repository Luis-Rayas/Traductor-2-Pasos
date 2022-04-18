/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.stlrcrr.Traductor2Pasos;

/**
 *
 * @author Luis
 */
public class Etiqueta {
    private String nombre;
    private Long valor;

    public Etiqueta() {
    }

    public Etiqueta(String nombre, Long valor) {
        this.nombre = nombre;
        this.valor = valor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getValor() {
        return valor;
    }

    public void setValor(Long valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Etiqueta{" + "nombre=" + nombre + ", valor=" + valor + '}' + '\n';
    }
    
    
}
