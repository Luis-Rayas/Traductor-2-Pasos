/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.stlrcrr.practica6;

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
            File fileIn = new File("..\\P6.asm");
            File fileOut = new File("..\\" + fileIn.getName().replace(".asm", "")+ ".lst");
            BufferedReader obj = null;
            obj = new BufferedReader(new FileReader(fileIn));
            String line;
            Mnemonico value;
            String[] palabra;
            StringBuilder finalContent = new StringBuilder();

            String comentario = "";

            String operador;
            Long operadorDecimal = 0l;

            Long ubicacionMemoria;
            TabCop tab = new TabCop();

            line = obj.readLine();
            line = line.replace("\t", "").trim();
            palabra = line.split(" ");
            ubicacionMemoria = Long.parseLong(palabra[1].substring(palabra[1].indexOf("$") + 1, palabra[1].length()), 16);
            
            boolean error = false;
            finalContent.append(line + "\n");
            while ((line = obj.readLine()) != null) {//lectura de linea por linea del archivo 
                line = line.replace("\t", "").trim();
                line = line.replace(";", " ;");
                line = line.replace(":", ": ");
                palabra = line.split(" ");
                value = null;
                error = false;

                finalContent.append(NumeralSistemConverter.decimalToHexadecimal(ubicacionMemoria.toString()) + " ");
                if (palabra[0].contains(":")) { //es una etiqueta
                    finalContent.append(palabra[0] + " ");
                    line = line.replace(palabra[0], "").trim();
                    palabra = line.split(" ");
                } else if (line.contains(";")) {//comentario
                    
                }
                if (palabra[0].equals("END")) {
                    finalContent.append(palabra[0]);
                    break;
                }
                if (tab.getMnemonicos().contains(palabra[0])) { // es un mnemonico
                    if (tab.getMnemonicosINH().containsKey(palabra[0])) { // es un inherente
                        for (Map.Entry entry : tab.getMnemonicosINH().entrySet()) {
                            if (palabra[0].equals(entry.getKey())) {
                                value = (Mnemonico) entry.getValue();
                                break;
                            }
                        }
                        if(palabra.length > 1 && value != null && !value.getModoDireccionamiento().getAbbreviation().equals("REL")){
                            error = true;
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
                    } else { // es extendido o es Directo
                        if (palabra[1].contains("$")) { //sistema de numeracion hexadecimal
                            operador = palabra[1].substring(palabra[1].indexOf("$") + 1, palabra[1].length());
                            operadorDecimal = Long.parseLong(operador, 16);
                        } else if (palabra[1].contains("@")) { //sistema de numeracion octal
                            operador = palabra[1].substring(palabra[1].indexOf("@") + 1, palabra[1].length());
                            operadorDecimal = Long.parseLong(operador, 8);
                        } else if (palabra[1].contains("%")) {//sistema de numeracion binario
                            operador = palabra[1].substring(palabra[1].indexOf("%") + 1, palabra[1].length());
                            operadorDecimal = Long.parseLong(operador, 2);
                        } else {//sistema de numeracion decimal
                            operadorDecimal = Long.parseLong(palabra[1]);
                        }
                    }
                    if (operadorDecimal <= 255) {
                        if(tab.getMnemonicosDIR().containsKey(palabra[0]) && value == null){
                            for (Map.Entry entry : tab.getMnemonicosDIR().entrySet()) {
                                if (palabra[0].equals(entry.getKey())) {
                                    value = (Mnemonico) entry.getValue();
                                    break;
                                }
                            }
                        } else if (tab.getMnemonicosEXT().containsKey(palabra[0])  && value == null) {
                            for (Map.Entry entry : tab.getMnemonicosEXT().entrySet()) {
                                if (palabra[0].equals(entry.getKey())) {
                                    value = (Mnemonico) entry.getValue();
                                    break;
                                }
                            }
                        }
                    } else {
                        for (Map.Entry entry : tab.getMnemonicosEXT().entrySet()) {
                            if (palabra[0].equals(entry.getKey())) {
                                value = (Mnemonico) entry.getValue();
                                break;
                            }
                        }
                    }
                }
                if (value != null) {
                    if(!error){
                        finalContent.append(value.getNombre() + " ");
                        finalContent.append(palabra.length > 1 ? palabra[1] + " " : "");
                        finalContent.append(value.getModoDireccionamiento().getAbbreviation() + " ");
                        finalContent.append("(LI = " + value.getLongInstruccion() + ")");
                        ubicacionMemoria += value.getLongInstruccion();
                    } else {
                        finalContent.append("FDR");
                    }
                } else {
                    finalContent.append("??? ");
                }
                finalContent.append("\n");
            }
            System.out.println(finalContent.toString());
            if (!finalContent.toString().isEmpty()) {
                if(!fileOut.exists()){
                    fileOut.createNewFile();
                }
                FileWriter fw = new FileWriter(fileOut);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(finalContent.toString());
                bw.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
