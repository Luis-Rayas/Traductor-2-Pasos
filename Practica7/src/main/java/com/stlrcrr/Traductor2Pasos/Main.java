/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.stlrcrr.Traductor2Pasos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Luis
 */
public class Main {

    public static void main(String[] args) {
        try {
            File fileIn = new File("..\\P11.asm");
            File fileOut = new File("..\\" + fileIn.getName().replace(".asm", "") + ".lst");
            File fileTabSim = new File("..\\" + fileIn.getName().replace(".asm", "") + ".tabsim");
            BufferedReader obj = null;

            String line = "";
            Mnemonico value;
            Directiva directiva;
            String[] palabra;
            StringBuilder finalContent = null;

            String comentario = "";
            String etiqueta = "";

            String operador;
            Long operadorDecimal = 0L;

            Long ubicacionMemoria = 0L;
            TabCop tab = new TabCop();

            boolean error;
            boolean operadorPosEtiqueta;
            boolean tieneEtiqueta;
            Integer vuelta = 1;
            try {
                do {
                    operadorPosEtiqueta = false;
                    obj = new BufferedReader(new FileReader(fileIn));
                    finalContent = new StringBuilder();
                    while ((line = obj.readLine()) != null) {//lectura de linea por linea del archivo 
                        line = line.replace("\t", " ").trim();
                        line = line.replace(";", " ;");
                        line = line.replace(":", ": ");
                        palabra = line.split(" ");
                        value = null;
                        directiva = null;
                        error = false;
                        tieneEtiqueta = false;
                        operadorDecimal = null;
                        tab.resetTablesMnemonicosIDX();
                        if (vuelta == 2) {
                            System.out.println("holi");
                        }

                        finalContent.append(String.format("%04X", ubicacionMemoria & 0xFFFFF) + " ");
                        if (tab.getDirectivas().containsKey(palabra[0])) { //es una directiva o pseudoinstruccion
                            for (Map.Entry entry : tab.getDirectivas().entrySet()) {
                                if (palabra[0].equals(entry.getKey())) {
                                    directiva = (Directiva) entry.getValue();
                                    if (palabra.length > 1) {
                                        directiva.setOperador(palabra[1]);
                                    }
                                    directiva.setUbicacionMemoria(ubicacionMemoria);
                                    break;
                                }
                            }
                        } else if (tab.getMnemonicos().contains(palabra[0])) { // es un mnemonico
                            if (palabra.length > 1 && palabra[1].contains(",") && tab.getMnemonicosIDX().containsKey(palabra[0])) { //el operador tiene coma y es indexado
                                if (tab.getMnemonicosIDX().containsKey(palabra[0]) && value == null) {
                                    for (Map.Entry entry : tab.getMnemonicosIDX().entrySet()) {
                                        if (palabra[0].equals(entry.getKey())) {
                                            value = (Mnemonico) entry.getValue();
                                            try {
                                                value.setModoIndexadoIDX(palabra[1]);
                                            } catch (Exception e) {
                                                System.out.println("Error en INDEXADO: " + e.getMessage() + "\t" + line);
                                                error = true;
                                            }
                                            break;
                                        }
                                    }
                                }
                            } else if (tab.getMnemonicosINH().containsKey(palabra[0]) && value == null) { // es un inherente
                                for (Map.Entry entry : tab.getMnemonicosINH().entrySet()) {
                                    if (palabra[0].equals(entry.getKey())) {
                                        value = (Mnemonico) entry.getValue();
                                        operadorDecimal = 0L;
                                        break;
                                    }
                                }
                                if (palabra.length > 1) {
                                    error = true;
                                    operadorDecimal = 0L;
                                }
                            } else if (tab.getMnemonicosREL().containsKey(palabra[0]) && value == null) { // es relativo
                                for (Map.Entry entry : tab.getMnemonicosREL().entrySet()) {
                                    if (palabra[0].equals(entry.getKey())) { //buscamos el mnemonico en los relativos
                                        value = (Mnemonico) entry.getValue();
                                        if (palabra.length > 1) {
                                            if (tab.getEtiquetas().containsKey(palabra[1])) { //buscamos la etiqueta en la lista de etiquetas
                                                for (Map.Entry entryEtq : tab.getEtiquetas().entrySet()) {
                                                    if (palabra[1].equals(entryEtq.getKey())) {
                                                        Etiqueta eti = (Etiqueta) entryEtq.getValue();
                                                        operadorDecimal = eti.getValor();
                                                        break;
                                                    }
                                                }
                                            }
                                        } else {
                                            operadorDecimal = 0L;
                                        }
                                        break;
                                    }
                                }
                            } else if (palabra[1].contains("#")) { //es inmediato
                                if (tab.getMnemonicosIMM().containsKey(palabra[0]) && value == null) {
                                    for (Map.Entry entry : tab.getMnemonicosIMM().entrySet()) {
                                        if (palabra[0].equals(entry.getKey())) {
                                            value = (Mnemonico) entry.getValue();
                                            break;
                                        }
                                    }
                                } else {
                                    error = true;
                                }
                            }// es extendido o es Directo
                            if (operadorDecimal == null) { //obtener el codigo de operacion
                                if (tab.getEtiquetas().containsKey(palabra[1])) {
                                    for (Map.Entry entryEtq : tab.getEtiquetas().entrySet()) {
                                        if (palabra[1].equals(entryEtq.getKey())) {
                                            Etiqueta eti = (Etiqueta) entryEtq.getValue();
                                            operadorDecimal = eti.getValor();
                                            break;
                                        }
                                    }
                                } else {
                                    if (palabra[1].contains("#")) {
                                        palabra[1] = palabra[1].replace("#", "");
                                    }
                                    if (palabra[1].contains("$") && operadorDecimal == null) { //sistema de numeracion hexadecimal
                                        operador = palabra[1].substring(palabra[1].indexOf("$") + 1, palabra[1].length());
                                        operadorDecimal = Long.parseLong(operador, 16);
                                    } else if (palabra[1].contains("@") && operadorDecimal == null) { //sistema de numeracion octal
                                        operador = palabra[1].substring(palabra[1].indexOf("@") + 1, palabra[1].length());
                                        operadorDecimal = Long.parseLong(operador, 8);
                                    } else if (palabra[1].contains("%") && operadorDecimal == null) {//sistema de numeracion binario
                                        operador = palabra[1].substring(palabra[1].indexOf("%") + 1, palabra[1].length());
                                        operadorDecimal = Long.parseLong(operador, 2);
                                    } else if (operadorDecimal == null) {//sistema de numeracion decimal
                                        try {
                                            operadorDecimal = Long.parseLong(palabra[1]);
                                        } catch (NumberFormatException e) {
                                            //es probablemente una etiqueta
                                            operadorPosEtiqueta = true;
                                        }
                                    }
                                }
                            }
                            if (operadorDecimal != null) { //existe un operador
                                if (operadorDecimal <= 255) {
                                    if (tab.getMnemonicosDIR().containsKey(palabra[0]) && value == null) {
                                        for (Map.Entry entry : tab.getMnemonicosDIR().entrySet()) {
                                            if (palabra[0].equals(entry.getKey())) {
                                                value = (Mnemonico) entry.getValue();
                                                break;
                                            }
                                        }
                                    } else if (tab.getMnemonicosEXT().containsKey(palabra[0]) && value == null) {
                                        for (Map.Entry entry : tab.getMnemonicosEXT().entrySet()) {
                                            if (palabra[0].equals(entry.getKey())) {
                                                value = (Mnemonico) entry.getValue();
                                                break;
                                            }
                                        }
                                    }
                                } else {
                                    if (value == null) {
                                        for (Map.Entry entry : tab.getMnemonicosEXT().entrySet()) {
                                            if (palabra[0].equals(entry.getKey())) {
                                                value = (Mnemonico) entry.getValue();
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            //es probablemente una etiqueta
                            if (palabra[0].contains(":") || palabra.length >= 2) { //es una etiqueta
                                //finalContent.append(palabra[0] + " ");
                                tieneEtiqueta = true;
                                tab.getEtiquetas().put(palabra[0].replace(":", "").trim(),
                                        new Etiqueta(palabra[0].replace(":", "").trim(), ubicacionMemoria));
                                etiqueta = palabra[0].replace(":", "").trim();
                                line = line.replace(palabra[0], "").trim(); //reseteamos la linea sin la etiqueta
                                palabra = line.split(" ");
                            } else if (line.contains(";")) {//comentario
                                comentario = palabra[palabra.length - 1];
                                line = line.replace(comentario, "").trim(); //reseteamos la linea sin la etiqueta
                                palabra = line.split(" ");
                            }
                        }
                        if (directiva != null) {
                            String codigoOperacion = "";
                            if (directiva.getOperador() != null) {
                                try {
                                    if (directiva.getOperador().contains("$")) { //sistema de numeracion hexadecimal
                                        operador = palabra[1].substring(directiva.getOperador().indexOf("$") + 1, directiva.getOperador().length());
                                        operadorDecimal = Long.parseLong(operador, 16);
                                    } else if (directiva.getOperador().contains("@")) { //sistema de numeracion octal
                                        operador = palabra[1].substring(directiva.getOperador().indexOf("@") + 1, directiva.getOperador().length());
                                        operadorDecimal = Long.parseLong(operador, 8);
                                    } else if (directiva.getOperador().contains("%")) {//sistema de numeracion binario
                                        operador = palabra[1].substring(directiva.getOperador().indexOf("%") + 1, directiva.getOperador().length());
                                        operadorDecimal = Long.parseLong(operador, 2);
                                    } else {//sistema de numeracion decimal
                                        operadorDecimal = Long.parseLong(directiva.getOperador());
                                    }
                                } catch (NumberFormatException e) {

                                }
                            }
                            switch (directiva.getNombre()) {
                                case "ORG":
                                    ubicacionMemoria = Long.parseLong(operadorDecimal.toString(), 10);
                                    finalContent.append("\t\t\t" + directiva.getNombre() + " $" + NumeralSistemConverter.decimalToHexadecimal(ubicacionMemoria.toString(), 4));
                                    break;
                                case "END":
                                    finalContent.append("\t\t\t" + directiva.getNombre());
                                    if (tieneEtiqueta) {
                                        finalContent.append("\t\t");
                                        finalContent.append(etiqueta + ":");
                                        finalContent.append("\t");
                                    }
                                    break;
                                case "START":
                                    ubicacionMemoria = 0L;
                                    finalContent.append("\t\t\t" + directiva.getNombre());
                                    break;
                                case "EQU":
                                    if (tab.getEtiquetas().containsKey(etiqueta.replace(":", ""))) { //buscamos la etiqueta en la lista de etiquetas
                                        for (Map.Entry entryEtq : tab.getEtiquetas().entrySet()) {
                                            if (etiqueta.equals(entryEtq.getKey())) {
                                                Etiqueta eti = (Etiqueta) entryEtq.getValue();
                                                eti.setValor(operadorDecimal);
                                                entryEtq.setValue(eti);
                                                break;
                                            }
                                        }
                                        finalContent.append("\t\t\t" + directiva.getNombre() + " " + directiva.getOperador() + "\t\t");
                                        finalContent.append(etiqueta + ":");
                                        finalContent.append("\t");
                                    }
                                    break;
                                case "DC.B":
                                    String[] subStringDCB;
                                    if (directiva.getOperador() != null) {
                                        directiva.setOperador(directiva.getOperador().replace("\"", ""));
                                        directiva.setOperador(directiva.getOperador().replace("\'", ""));
                                        subStringDCB = directiva.getOperador().split(",");
                                    } else {
                                        directiva.setOperador("");
                                        subStringDCB = new String[]{"0"};
                                    }
                                    codigoOperacion = "";
                                    for (int i = 0; i < subStringDCB.length; i++) {
                                        try {
                                            operadorDecimal = Long.parseLong(subStringDCB[i]);
                                        } catch (NumberFormatException e) {
                                            operadorDecimal = (long) subStringDCB[i].charAt(0);
                                        }
                                        if (operadorDecimal <= 255) {
                                            codigoOperacion += String.format("%02X", operadorDecimal & 0xFFFFF);
                                            ubicacionMemoria += Long.parseLong("1", 16);

                                        } else {
                                            finalContent.append("FDR");
                                        }
                                    }
                                    finalContent.append(codigoOperacion.replaceAll("(?s).{2}(?!$)", "$0 "));
                                    ubicacionMemoria += operadorDecimal;
                                    finalContent.append("\t\t" + directiva.getNombre() + " " + directiva.getOperador() + "\t\t");
                                    break;
                                case "DC.W":
                                    String[] subStringDCW;
                                    if (directiva.getOperador() != null) {
                                        directiva.setOperador(directiva.getOperador().replace("\"", ""));
                                        directiva.setOperador(directiva.getOperador().replace("\'", ""));
                                        subStringDCW = directiva.getOperador().split(",");
                                    } else {
                                        directiva.setOperador("");
                                        subStringDCW = new String[]{"0"};
                                    }
                                    codigoOperacion = "";
                                    for (int i = 0; i < subStringDCW.length; i++) {
                                        try {
                                            operadorDecimal = Long.parseLong(subStringDCW[i]);
                                        } catch (NumberFormatException e) {
                                            operadorDecimal = (long) subStringDCW[i].charAt(0);
                                        }
                                        if (operadorDecimal <= 65535) {
                                            codigoOperacion += String.format("%04X", operadorDecimal & 0xFFFFF);
                                            ubicacionMemoria += Long.parseLong("2", 16);

                                        } else {
                                            finalContent.append("FDR");
                                        }
                                    }
                                    finalContent.append(codigoOperacion.replaceAll("(?s).{2}(?!$)", "$0 "));
                                    ubicacionMemoria += operadorDecimal;
                                    finalContent.append("\t\t" + directiva.getNombre() + " " + directiva.getOperador() + "\t\t");

                                    break;
                                case "BSZ":
                                case "ZMB":
                                    codigoOperacion = "";
                                    for (int i = 0; i < operadorDecimal; i++) {
                                        codigoOperacion += "00";
                                    }
                                    finalContent.append(codigoOperacion.replaceAll("(?s).{2}(?!$)", "$0 "));
                                    ubicacionMemoria += operadorDecimal;
                                    finalContent.append("\t" + directiva.getNombre() + " " + directiva.getOperador() + "\t\t");
                                    break;
                                case "FCB":
                                    String[] subOperadorFCB;
                                    subOperadorFCB = directiva.getOperador().split(",");
                                    for (int i = 0; i < subOperadorFCB.length; i++) {
                                        operadorDecimal = Long.parseLong(subOperadorFCB[i]);
                                        if (operadorDecimal <= 255) {
                                            codigoOperacion += String.format("%02X", operadorDecimal & 0xFFFFF);
                                            ubicacionMemoria += Long.parseLong("1", 16);

                                        } else {
                                            finalContent.append("FDR");
                                        }
                                    }
                                    finalContent.append(codigoOperacion.replaceAll("(?s).{2}(?!$)", "$0 "));
                                    finalContent.append("\t" + directiva.getNombre() + " " + directiva.getOperador() + "\t\t");
                                    break;
                                case "FCC":
                                    codigoOperacion = "";
                                    String subPalabra = directiva.getOperador().substring(1, directiva.getOperador().length() - 1);
                                    for (int i = 0; i < subPalabra.length(); i++) {
                                        codigoOperacion += Integer.toHexString((int) subPalabra.charAt(i)).toUpperCase();
                                    }
                                    finalContent.append(codigoOperacion.replaceAll("(?s).{2}(?!$)", "$0 "));
                                    ubicacionMemoria += subPalabra.length();
                                    finalContent.append("\t" + directiva.getNombre() + " " + directiva.getOperador() + "\t\t");
                                    break;
                                case "FILL":
                                    String[] subOperadorFill;
                                    subOperadorFill = directiva.getOperador().split(",");
                                    switch (subOperadorFill.length) {
                                        case 1:
                                            operadorDecimal = 0L;
                                            for (int i = 0; i < Integer.parseInt(subOperadorFill[0]); i++) {
                                                if (operadorDecimal <= 255) {
                                                    codigoOperacion += String.format("%02X", operadorDecimal & 0xFFFFF);
                                                    ubicacionMemoria += Long.parseLong("1", 16);
                                                } else {
                                                    finalContent.append("FDR");
                                                }
                                            }
                                            finalContent.append(codigoOperacion.replaceAll("(?s).{2}(?!$)", "$0 "));
                                            finalContent.append("\t" + directiva.getNombre() + " " + directiva.getOperador() + "\t\t");
                                            break;
                                        case 2:
                                            operadorDecimal = Long.parseLong(subOperadorFill[0], 16);
                                            for (int i = 0; i < Integer.parseInt(subOperadorFill[1]); i++) {
                                                if (operadorDecimal <= 255) {
                                                    codigoOperacion += String.format("%02X", operadorDecimal & 0xFFFFF);
                                                    ubicacionMemoria += Long.parseLong("1", 16);
                                                } else {
                                                    finalContent.append("FDR");
                                                }
                                            }
                                            finalContent.append(codigoOperacion.replaceAll("(?s).{2}(?!$)", "$0 "));
                                            finalContent.append("\t" + directiva.getNombre() + " " + directiva.getOperador() + "\t\t");
                                            break;
                                        default:
                                            finalContent.append("FDR");
                                            finalContent.append("\t" + directiva.getNombre() + " " + directiva.getOperador() + "\t\t");
                                            break;
                                    }
                            }
                        } else if (value != null) {
                            String codigoOperacion = "";
                            if (!error && vuelta != 1) {
                                if (operadorDecimal != null && operadorDecimal != 0) {
                                    if (operadorDecimal >= 0) {
                                        codigoOperacion
                                                = value.getCodOp().replaceAll("\\?+$",
                                                        String.format("%0" + contarCaracteres(value.getCodOp(), '?') + "X", operadorDecimal & 0xFFFFFFFF));
                                        //String.format("%0" + contarCaracteres(value.getCodOp(), '?') + "X", NumeralSistemConverter.decimalToHexadecimal(operadorDecimal.toString())));
                                    } else {
                                        codigoOperacion
                                                = value.getCodOp().replaceAll("\\?+$",
                                                        String.format("%0" + contarCaracteres(value.getCodOp(), '?') + "X", operadorDecimal & 0xFF));

                                    }
                                } else if (value.getModoDireccionamiento().getAbbreviation().equals("INH") || value.getModoDireccionamiento().getAbbreviation().equals("IDX")) {
                                    codigoOperacion = "";
                                    codigoOperacion = value.getCodOp();
                                }
                                finalContent.append(codigoOperacion.replaceAll("(?s).{2}(?!$)", "$0 "));
                                finalContent.append("\t\t");
                                finalContent.append(value.getNombre() + " ");
                                finalContent.append(palabra.length > 1 ? palabra[1] + " " : "    ");
                                /*finalContent.append('\t');
                                finalContent.append(value.getCodOp());*/
                                finalContent.append("\t");
                                if (tieneEtiqueta) {
                                    finalContent.append("\t\t");
                                    finalContent.append(etiqueta);
                                    finalContent.append("\t");
                                } else {
                                    finalContent.append("\t");
                                }
                                finalContent.append(value.getModoDireccionamiento().getAbbreviation() + " ");
                                finalContent.append('\t');
                                finalContent.append("(LI = " + value.getLongInstruccion() + ")");
                                finalContent.append('\t');
                                ubicacionMemoria += value.getLongInstruccion();
                            } else {
                                finalContent.append("FDR");
                                finalContent.append("\t\t");
                                finalContent.append(value.getNombre() + " ");
                                finalContent.append(palabra.length > 1 ? palabra[1] + " " : "    ");
                                finalContent.append("\t\t");
                            }
                        } else {
                            finalContent.append("\t\t\t");
                            finalContent.append(palabra[0] + " ");
                            finalContent.append(palabra.length > 1 ? palabra[1] + " " : "    ");
                            if (tieneEtiqueta) {
                                finalContent.append("\t\t");
                                finalContent.append(etiqueta);
                                finalContent.append("\t");
                            } else {
                                finalContent.append("\t");
                            }
                        }
                        finalContent.append("\n");
                    }
                    vuelta++;
                } while (vuelta <= 2);
            } catch (Exception e) {
                System.out.println(line);
                e.printStackTrace();
            }
            System.out.println(finalContent.toString());
            if (!finalContent.toString().isEmpty()) {
                if (!fileOut.exists()) {
                    fileOut.createNewFile();
                }
                FileWriter fw = new FileWriter(fileOut);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(finalContent.toString());
                bw.flush();
            }
            if (!tab.getEtiquetas().isEmpty()) {
                if (!fileTabSim.exists()) {
                    fileTabSim.createNewFile();
                }
                FileWriter fw = new FileWriter(fileTabSim);
                BufferedWriter bw = new BufferedWriter(fw);
                for (Map.Entry entry : tab.getEtiquetas().entrySet()) {
                    Etiqueta e = (Etiqueta) entry.getValue();
                    bw.write(e.getNombre() + " $" + NumeralSistemConverter.decimalToHexadecimal(e.getValor().toString(), 4) + "\n");
                }
                bw.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static int contarCaracteres(String codOp, char caracter) {
        int totalCharacters = 0;
        for (int i = 0; i < codOp.length(); i++) {
            if (codOp.charAt(i) == caracter) {
                totalCharacters++;
            }
        }
        return totalCharacters;
    }

}
