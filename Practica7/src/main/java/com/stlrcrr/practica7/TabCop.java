/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.stlrcrr.practica7;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Luis
 */
public class TabCop {
    private List<String> mnemonicos;
    private HashMap<String, Etiqueta> etiquetas;
    private HashMap<String, Directiva> directivas;
    private HashMap<String,Mnemonico> mnemonicosINH;
    private HashMap<String,Mnemonico> mnemonicosIMM;
    private HashMap<String,Mnemonico> mnemonicosDIR;
    private HashMap<String,Mnemonico> mnemonicosEXT;
    private HashMap<String,Mnemonico> mnemonicosREL;

    public TabCop() {
        mnemonicos = new ArrayList<>();
        buildList();
        
        etiquetas = new HashMap<>();
        
        directivas = new HashMap<>();
        buildTableDirectivas();
        
        mnemonicosINH = new HashMap<>();
        buildTableINH();
        
        mnemonicosIMM = new HashMap<>();
        buildTableIMM();
        
        mnemonicosDIR = new HashMap<>();
        buildTableDIR();
        
        mnemonicosEXT = new HashMap<>();
        buildTableEXT();        
        
        mnemonicosREL = new HashMap<>();
        buildTableREL();
    }   
    
    private void buildTableINH(){
        mnemonicosINH.put("ABA", new Mnemonico("ABA", "INH", 2, "1606"));
        mnemonicosINH.put("ABX", new Mnemonico("ABX", "IDX", 2, "1AE5"));
        mnemonicosINH.put("ABY", new Mnemonico("ABY", "IDX", 2, "19ED"));
        mnemonicosINH.put("ASLA", new Mnemonico("ASLA", "INH", 1, "48"));
        mnemonicosINH.put("ASLB", new Mnemonico("ASLB", "INH", 1, "58"));
        
        mnemonicosINH.put("ASLD", new Mnemonico("ASLD", "INH", 1, "59"));        
        mnemonicosINH.put("ASRA", new Mnemonico("ASRA", "INH", 1, "47"));
        mnemonicosINH.put("ASRB", new Mnemonico("ASRB", "INH", 1, "57"));  
    }
    
    private void buildTableREL(){
        mnemonicosREL.put("BCC", new Mnemonico("BCC", "REL", 2, "24??"));  //RELATIVO
        mnemonicosREL.put("BCS", new Mnemonico("BCS", "REL", 2, "25??"));  //RELATIVO
        mnemonicosREL.put("BGND", new Mnemonico("BGND", "REL", 2, "2C??"));  //RELATIVO
    }
    
    private void buildTableIMM(){
        mnemonicosIMM.put("ADCA", new Mnemonico("ADCA", "IMM", 2, "89??"));        
        mnemonicosIMM.put("ADCB", new Mnemonico("ADCB", "IMM", 2, "C9??"));        
        mnemonicosIMM.put("ADDA", new Mnemonico("ADDA", "IMM", 2, "8B??"));        
        mnemonicosIMM.put("ADDB", new Mnemonico("ADDB", "IMM", 2, "CB??"));        
        mnemonicosIMM.put("ADDD", new Mnemonico("ADDD", "IMM", 3, "C3????"));
        
        mnemonicosIMM.put("ANDA", new Mnemonico("ANDA", "IMM", 2, "84??"));
        mnemonicosIMM.put("ANDB", new Mnemonico("ANDB", "IMM", 2, "C4??"));
    }
    
    private void buildTableDIR(){
        mnemonicosDIR.put("ADCA", new Mnemonico("ADCA", "DIR", 2, "99??"));
        mnemonicosDIR.put("ADCB", new Mnemonico("ADCB", "DIR", 2, "D9??"));
        mnemonicosDIR.put("ADDA", new Mnemonico("ADDA", "DIR", 2, "9B??"));
        mnemonicosDIR.put("ADDB", new Mnemonico("ADDB", "DIR", 2, "DB??"));
        mnemonicosDIR.put("ADDD", new Mnemonico("ADDD", "DIR", 2, "D3??"));
        
        mnemonicosDIR.put("ANDA", new Mnemonico("ANDA", "DIR", 2, "94??"));
        mnemonicosDIR.put("ANDB", new Mnemonico("ANDB", "DIR", 2, "D4??"));
        mnemonicosDIR.put("BCLR", new Mnemonico("BCLR", "DIR", 3, "4D????"));
    }
    
    private void buildTableEXT(){
        mnemonicosEXT.put("ADCA", new Mnemonico("ADCA", "EXT", 3, "B9????"));
        mnemonicosEXT.put("ADCB", new Mnemonico("ADCB", "EXT", 3, "F9????"));
        mnemonicosEXT.put("ADDA", new Mnemonico("ADDA", "EXT", 3, "BB????"));
        mnemonicosEXT.put("ADDB", new Mnemonico("ADDB", "EXT", 3, "FB????"));
        mnemonicosEXT.put("ADDD", new Mnemonico("ADDD", "EXT", 3, "F3????"));
        
        mnemonicosEXT.put("ANDA", new Mnemonico("ANDA", "EXT", 3, "B4????"));
        mnemonicosEXT.put("ANDB", new Mnemonico("ANDB", "EXT", 3, "F4????"));
        mnemonicosEXT.put("ASL", new Mnemonico("ASL", "EXT", 3, "78????"));
        mnemonicosEXT.put("ASR", new Mnemonico("ASR", "EXT", 3, "77????"));
        mnemonicosEXT.put("BCLR", new Mnemonico("BCLR", "EXT", 3, "1D????"));
    }
    
    private void buildList(){
        this.mnemonicos.add("ABA");
        this.mnemonicos.add("ABA");
        this.mnemonicos.add("ABY");
        this.mnemonicos.add("ADCA");
        this.mnemonicos.add("ADCB");
        
        this.mnemonicos.add("ADDA");
        this.mnemonicos.add("ADDB");
        this.mnemonicos.add("ADDD");
        this.mnemonicos.add("ANDA");
        this.mnemonicos.add("ANDB");
        
        this.mnemonicos.add("ASL");
        this.mnemonicos.add("ASLA");
        this.mnemonicos.add("ASLB");
        this.mnemonicos.add("ASLD");
        this.mnemonicos.add("ASR");
        
        this.mnemonicos.add("ASRA");
        this.mnemonicos.add("ASRB");
        this.mnemonicos.add("BCC");
        this.mnemonicos.add("BCLR");
        this.mnemonicos.add("BCS");
        
        this.mnemonicos.add("BGND");
    }

    private void buildTableDirectivas(){
        directivas.put("ORG", new Directiva("ORG"));
        directivas.put("END", new Directiva("END"));
        directivas.put("EQU", new Directiva("EQU"));
        directivas.put("START", new Directiva("START"));
        directivas.put("DC.B", new Directiva("DC.B"));
        directivas.put("DC.W", new Directiva("DC.W"));
        directivas.put("BSZ", new Directiva("BSZ"));
        directivas.put("ZMB", new Directiva("ZMB"));
        directivas.put("FCB", new Directiva("FCB"));
        directivas.put("FCC", new Directiva("FCC"));
        directivas.put("FILL", new Directiva("FILL"));
    }
    
    public List<String> getMnemonicos() {
        return mnemonicos;
    }

    public void setMnemonicos(List<String> mnemonicos) {
        this.mnemonicos = mnemonicos;
    }

    public HashMap<String, Mnemonico> getMnemonicosINH() {
        return mnemonicosINH;
    }

    public void setMnemonicosINH(HashMap<String, Mnemonico> mnemonicosINH) {
        this.mnemonicosINH = mnemonicosINH;
    }

    public HashMap<String, Mnemonico> getMnemonicosIMM() {
        return mnemonicosIMM;
    }

    public void setMnemonicosIMM(HashMap<String, Mnemonico> mnemonicosIMM) {
        this.mnemonicosIMM = mnemonicosIMM;
    }

    public HashMap<String, Mnemonico> getMnemonicosDIR() {
        return mnemonicosDIR;
    }

    public void setMnemonicosDIR(HashMap<String, Mnemonico> mnemonicosDIR) {
        this.mnemonicosDIR = mnemonicosDIR;
    }

    public HashMap<String, Mnemonico> getMnemonicosEXT() {
        return mnemonicosEXT;
    }

    public void setMnemonicosEXT(HashMap<String, Mnemonico> mnemonicosEXT) {
        this.mnemonicosEXT = mnemonicosEXT;
    }

    public HashMap<String, Mnemonico> getMnemonicosREL() {
        return mnemonicosREL;
    }

    public void setMnemonicosREL(HashMap<String, Mnemonico> mnemonicosREL) {
        this.mnemonicosREL = mnemonicosREL;
    }

    public HashMap<String, Etiqueta> getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(HashMap<String, Etiqueta> etiquetas) {
        this.etiquetas = etiquetas;
    }

    public HashMap<String, Directiva> getDirectivas() {
        return directivas;
    }

    public void setDirectivas(HashMap<String, Directiva> directivas) {
        this.directivas = directivas;
    }
    
    
    
    
}
