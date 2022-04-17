/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.stlrcrr.practica7;

/**
 *
 * @author Luis
 */
public class AddressingMode {
    private String name;
    private String abbreviation;
    private Integer bitsSize;

    public AddressingMode(String abbreviation, int bitsize) {
        switch(abbreviation){
            case "INH":
                buildModeAdrresing("Inherent",bitsize, "INH");
                break;
            case "IMM":
                buildModeAdrresing("Immediate",bitsize, "IMM");
                break;
            case "DIR":
                buildModeAdrresing("Direct",bitsize, "DIR");
                break;
            case "EXT":
                buildModeAdrresing("Extended",bitsize, "EXT");
                break;
            case "REL":
                buildModeAdrresing("Relative",bitsize, "REL");
                break;
            case "IDX":
                buildModeAdrresing("Indexed",bitsize, "IDX");
                break;
            case "IDX1":
                buildModeAdrresing("Indexed",bitsize, "IDX1");
                break;
            case "IDX2":
                buildModeAdrresing("Indexed-Indirect",bitsize, "IDX2");
                break;
            case "[IDX2]":
                buildModeAdrresing("Indexed-Indirect",bitsize, "[IDX2]");
                break;
            case "[D,IDX]":
                buildModeAdrresing("Indexed-Indirect",bitsize, "[D,IDX]");
                break;
            default:
                buildModeAdrresing("NA", 0,"NA");
                break;
        }
    }
    
    private void buildModeAdrresing(String name, int oprXi, String abbreviation){
        this.name = name;        
        this.abbreviation = abbreviation;
        this.bitsSize = oprXi;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public Integer getBitsSize() {
        return bitsSize;
    }

    public void setBitsSize(Integer bitsSize) {
        this.bitsSize = bitsSize;
    }

    @Override
    public String toString() {
        return "AddressingMode{" + "name=" + name + ", abbreviation=" + abbreviation + ", bitsSize=" + bitsSize + '}';
    }
    
    
    
    
}
