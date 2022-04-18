package com.stlrcrr.Traductor2Pasos;

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
    
    public Long getRelativeValue(Long operador, Long siguienteDir){
        //Long operadorDecimal = Long.parseLong(operadorHex, 16);
        //Long siguienteDireccion = Long.parseLong(siguienteDirHex, 16);
        
        Long resultado = operador - siguienteDir;        
        return resultado;
    }
    
    public Boolean relativeValueInRange(Long operador, Long siguienteDir){
        Long value = operador - siguienteDir;
        if(value < 0){ // negativo            
            if(value >= -128 && value <= -1){
                return true;
            } else {
                return false;
            }
        } else { //es positivo
            if(value <= 127 && value >= 0){
                return true;
            } else {
                return false;
            }
        }
    }
    
    public void setModoIndexadoIDX(String operador) throws Exception{
        if(operador.contains(",")){
            
        } else {
            //el metodo no es indexado o esta incorrecta la estructura
            throw new Exception("El metodo no es indexado o esta incorrecta la estructura");
        }
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
