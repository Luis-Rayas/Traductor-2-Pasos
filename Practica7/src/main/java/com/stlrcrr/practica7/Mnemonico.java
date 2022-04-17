package com.stlrcrr.practica7;

/**
 *
 * @author Luis
 */
public class Mnemonico {
    private String name;
    private AddressingMode modoDireccionamiento;
    private Integer longInstruccion;
    private String codOp;

    //Operador es el numero ya convertido a hexadecimal

    public Mnemonico(String name) {
        this.name = name;
        
    }    
    
    public Mnemonico(String sourceForm, String addresMode,int longOpc, String machineCod) { 
        this.name = sourceForm;
        this.modoDireccionamiento = new AddressingMode(addresMode, longOpc);
        this.codOp = machineCod;
        this.longInstruccion = longOpc;
    }
    
    public String getNombre() {
        return name;
    }

    public void setNombre(String nombre) {
        this.name = nombre;
    }

    public AddressingMode getModoDireccionamiento() {
        return modoDireccionamiento;
    }

    public void setModoDireccionamiento(String addrMode, int lonOpc) {
        this.modoDireccionamiento = new AddressingMode(addrMode, lonOpc);
    }    

    public Integer getLongInstruccion() {
        return longInstruccion;
    }

    public void setLongInstruccion(Integer longInstruccion) {
        this.longInstruccion = longInstruccion;
    }

    public String getCodOp() {
        return codOp;
    }

    public void setCodOp(String codOp) {
        this.codOp = codOp;
    }

    @Override
    public String toString() {
        return "Mnemonico{" + "name=" + name + ", modoDireccionamiento=" + modoDireccionamiento + ", longInstruccion=" + longInstruccion + ", codOp=" + codOp + '}';
    }
}
