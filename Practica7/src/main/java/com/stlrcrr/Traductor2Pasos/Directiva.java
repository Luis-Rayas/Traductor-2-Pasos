/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.stlrcrr.Traductor2Pasos;

/**
 *
 * @author Luis
 */
public class Directiva {
    private String nombre;
    private String operador;
    private Long ubicacionMemoria;

    public Directiva() {
    }

    public Directiva(String nombre) {
        this.nombre = nombre;
    }
    
    public Directiva(String nombre, String operador, Long ubicacionMemoria) {
        this.nombre = nombre;
        this.operador = operador;
        this.ubicacionMemoria = ubicacionMemoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getOperador() {
        return operador;
    }

    public void setOperador(String operador) {
        this.operador = operador;
    }

    public Long getUbicacionMemoria() {
        return ubicacionMemoria;
    }

    public void setUbicacionMemoria(Long ubicacionMemoria) {
        this.ubicacionMemoria = ubicacionMemoria;
    }

    @Override
    public String toString() {
        return "Directiva{" + "nombre=" + nombre + ", operador=" + operador + ", ubicacionMemoria=" + ubicacionMemoria + '}';
    }
    
    
}
