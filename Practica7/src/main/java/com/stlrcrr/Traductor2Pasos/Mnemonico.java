package com.stlrcrr.Traductor2Pasos;

import java.util.regex.Pattern;

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

    public Mnemonico(String name, String codOp) {
        this.name = name;
        this.codOp = codOp;
    }
    
    public Mnemonico(String sourceForm, String addresMode, int longOpc, String machineCod) {
        this.name = sourceForm;
        this.modoDireccionamiento = new AddressingMode(addresMode, longOpc);
        this.codOp = machineCod;
        this.longInstruccion = longOpc;
    }

    public Long getRelativeValue(Long operador, Long siguienteDir) {
        //Long operadorDecimal = Long.parseLong(operadorHex, 16);
        //Long siguienteDireccion = Long.parseLong(siguienteDirHex, 16);

        Long resultado = operador - siguienteDir;
        return resultado;
    }

    public Boolean relativeValueInRange(Long operador, Long siguienteDir) {
        Long value = operador - siguienteDir;
        if (value < 0) { // negativo            
            if (value >= -128 && value <= -1) {
                return true;
            } else {
                return false;
            }
        } else { //es positivo
            if (value <= 127 && value >= 0) {
                return true;
            } else {
                return false;
            }
        }
    }

    public void setModoIndexadoIDX(String operador) throws Exception {
        String operadores[] = operador.split(Pattern.quote(","));
        String n, r, xb;
        Long operadorDecimal = null; //operador de N
        Integer noFormula = null;
        n = operadores[0].trim();
        r = operadores[1].toUpperCase().trim();
        if (n.isEmpty()) {
            n = "0";
        }

        //Identificar la formula a aplicar
        if (n.contains("[") && r.contains("]")) { //formula 3 o 6
            n = n.replace("[", "");
            r = r.replace("]", "");
            if (n.equals("D")) { //formula 6
                noFormula = 6;
            } else { //formula 3
                noFormula = 3;
            }
        }
        if (r.equals("X")) { //identificar r
            r = "00";
        } else if (r.equals("Y")) {
            r = "01";
        } else if (r.equals("SP")) {
            r = "10";
        } else if (r.equals("PC")) {
            r = "11";
        } else {
            throw new Exception("El operador r no esta identificado o no contiene un valor valido");
        }

        //Obtener n para formulas 1 - 3 o 5
        if (n.equals("A") || n.equals("B") || n.equals("D")) {
            //formula 5
            
            if(noFormula == null){
                noFormula = 5;
            }
            if (n.equals("A") && noFormula != 3) {
                operadorDecimal = 0L; //A = 00
            } else if (n.equals("B") && noFormula != 3) {
                operadorDecimal = 1L; //B = 01
            } else if(n.equals("D") && noFormula != 3){
                operadorDecimal = 2L; //D = 10
            }
        } else if (n.contains("$") && operadorDecimal == null) { //sistema de numeracion hexadecimal
            operador = n.substring(n.indexOf("$") + 1, n.length());
            operadorDecimal = Long.parseLong(operador, 16);
        } else if (n.contains("@") && operadorDecimal == null) { //sistema de numeracion octal
            operador = n.substring(n.indexOf("@") + 1, n.length());
            operadorDecimal = Long.parseLong(operador, 8);
        } else if (n.contains("%") && operadorDecimal == null) {//sistema de numeracion binario
            operador = n.substring(n.indexOf("%") + 1, n.length());
            operadorDecimal = Long.parseLong(operador, 2);
        } else if (operadorDecimal == null) {//sistema de numeracion decimal
            try {
                operadorDecimal = Long.parseLong(n);
            } catch (NumberFormatException e) {
                throw new Exception("El operador n no esta identificado o no contiene un valor valido");
            }
        }

        if (noFormula == null) {
            if (operadorDecimal <= 15 && operadorDecimal >= -16) { //formula 1
                noFormula = 1;
            } else {
                noFormula = 2; //formula 2
            }
        }
        Integer xbInt;
        switch (noFormula) {
            case 1:
                this.modoDireccionamiento = new AddressingMode("IDX", 2);
                this.longInstruccion = 2;
                xb = "rr0xnnnn";
                xb = xb.replace("rr", r);
                if(operadorDecimal < 0){
                    xb = xb.replace("x", "1");
                } else {
                    xb = xb.replace("x", "0");
                }
                xb = xb.replace("nnnn", NumeralSistemConverter.decimalToBinario(operadorDecimal.toString(), 4));
                xbInt = Integer.parseInt(xb,2);
                this.codOp += NumeralSistemConverter.decimalToHexadecimal(xbInt.toString(),2);
                break;
            case 2:
                xb = "111rr0zs";
                xb = xb.replace("rr", r);
                if(operadorDecimal < 0){
                    xb = xb.replace("s", "1");
                } else {
                    xb = xb.replace("s", "0");
                }
                if(operadorDecimal > 255) { //el operador ocupa 2 bytes
                    xb = xb.replace("z", "1");
                    this.modoDireccionamiento = new AddressingMode("IDX", 4);
                    this.longInstruccion = 4;                    
                    xbInt = Integer.parseInt(xb,2);
                    this.codOp += NumeralSistemConverter.decimalToHexadecimal(xbInt.toString(),2);
                    this.codOp += NumeralSistemConverter.decimalToHexadecimal(operadorDecimal.toString(), 4);
                } else { // el operador ocupa 1 byte
                    xb = xb.replace("z", "0");
                    this.modoDireccionamiento = new AddressingMode("IDX", 3);
                    this.longInstruccion = 3;
                    xbInt = Integer.parseInt(xb,2);
                    this.codOp += NumeralSistemConverter.decimalToHexadecimal(xbInt.toString(),2);
                    this.codOp += NumeralSistemConverter.decimalToHexadecimal(operadorDecimal.toString(), 2);
                }
                break;
            case 3:                
                if(operadorDecimal >= 0){                    
                   this.modoDireccionamiento = new AddressingMode("IDX", 4);
                   this.longInstruccion = 4;
                   xb = "111rr011"; 
                   xb = xb.replace("rr", r);
                } else {
                    throw new Exception("N debe ser positivo");
                }                
                this.codOp += NumeralSistemConverter.binaryToHexadecimal(xb);                
                this.codOp += NumeralSistemConverter.decimalToHexadecimal(operadorDecimal.toString(), 4);
                break;
            case 5:
                this.modoDireccionamiento = new AddressingMode("IDX", 2);
                this.longInstruccion = 2;
                xb = "111rr1aa"; 
                xb = xb.replace("rr", r);
                xb = xb.replace("aa", NumeralSistemConverter.decimalToBinario(operadorDecimal.toString(), 2));
                this.codOp += NumeralSistemConverter.binaryToHexadecimal(xb);
                break;
            case 6:
                this.modoDireccionamiento = new AddressingMode("IDX", 2);
                this.longInstruccion = 2;
                xb = "111rr111"; 
                xb = xb.replace("rr", r);                
                this.codOp += NumeralSistemConverter.binaryToHexadecimal(xb);
                break;
            default:
                throw new Exception("No se logro identificar el numero de formula para aplicar");
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
