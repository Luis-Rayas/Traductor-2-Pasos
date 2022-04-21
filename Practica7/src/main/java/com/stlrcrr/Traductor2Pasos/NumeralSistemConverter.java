/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.stlrcrr.Traductor2Pasos;

import java.util.Stack;

/**
 *
 * @author Luis
 */
public class NumeralSistemConverter {
    //decimal a hexa
    //binario a hexa
    //octal a hexa

    public static String decimalToHexadecimal(String number, int cantChars) {
        try {
            Integer numberDecimal = Integer.parseInt(number);
            String numberHex = "";
            numberHex = decimalToHexadecimal(numberDecimal);
            if (numberHex.length() < cantChars) {
                for (int i = numberHex.length(); i < cantChars; i++) {
                    numberHex = "0" + numberHex;
                }
            } else {
                numberHex = numberHex.substring(numberHex.length()-cantChars, numberHex.length());
            }

            return numberHex;
        } catch (NumberFormatException e) {
            return "FDR";
        }
    }

    public static String binaryToHexadecimal(String number) {
        try {
            int numberDecimal = binartToDecimal(Integer.parseInt(number));
            if (validateBinario(number)) {
                return decimalToHexadecimal(numberDecimal);
            } else {
                return "FDR";
            }
        } catch (NumberFormatException e) {
            return "FDR";
        }
    }

    public static String octalToHexadecimal(String octal) {
        try {
            int decimalNumber = Integer.parseInt(octal);
            if (validateOctal(decimalNumber)) {
                return decimalToHexadecimal(decimalNumber);
            } else {
                return "FDR";
            }
        } catch (NumberFormatException e) {
            return "FDR";
        }
    }

    public static String decimalToBinario(String decimal, int cantDigits) {
        return decimalToBinary(Integer.parseInt(decimal), cantDigits);
    }

    //De Decimal a otras bases
    private static String decimalToBinary(int decimal, int cantDigits) {
        String binario = "";
        /*while (decimal > 0) {
            binario = decimal % 2 + binario;
            decimal = decimal / 2;
        }*/
        binario = Long.toBinaryString(decimal);
        if (binario.length() < cantDigits) {
            for (int i = binario.length(); i < cantDigits; i++) {
                binario = "0" + binario;
            }
        } else {
            binario = binario.substring(binario.length()-cantDigits, binario.length());
        }

        return binario;
    }

    private static String decimalToOctal(int decimal) {
        int residuo;
        String octal = "";
        char[] caracteresOctales = {'0', '1', '2', '3', '4', '5', '6', '7'};
        while (decimal > 0) {
            residuo = decimal % 8;
            char caracter = caracteresOctales[residuo];
            octal = caracter + octal;
            decimal = decimal / 8;
        }
        return octal;
    }

    private static String decimalToHexadecimal(int decimal) {
        /*int residuo;
        String hexadecimal = "";
        char[] caracteresHexadecimales = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        if(decimal >= 0){
            while (decimal > 0) {
                residuo = decimal % 16;
                char caracterHexadecimal = caracteresHexadecimales[residuo];
                hexadecimal = caracterHexadecimal + hexadecimal;
                decimal = decimal / 16;
            }
        }*/
        return Long.toHexString(decimal).toUpperCase();
    }

// Conversiones de otras bases a decimal
    private static int binartToDecimal(int binario) {
        int decimal = 0;
        int potencia = 0;
        // Ciclo infinito hasta que binario sea 0
        while (true) {
            if (binario == 0) {
                break;
            } else {
                int temp = binario % 10;
                decimal += temp * Math.pow(2, potencia);
                binario = binario / 10;
                potencia++;
            }
        }
        return decimal;
    }

    private static int octalToDecimal(int octal) {
        int decimal = 0;
        int potencia = 0;
        // Ciclo infinito que se rompe cuando octal es 0
        while (true) {
            if (octal == 0) {
                break;
            } else {
                int temp = octal % 10;
                decimal += temp * Math.pow(8, potencia);
                octal = octal / 10;
                potencia++;
            }
        }
        return decimal;
    }

    public static int hexadecimalToDecimal(String hexadecimal) {
        String caracteresHexadecimales = "0123456789ABCDEF";
        hexadecimal = hexadecimal.toUpperCase();
        int decimal = 0;
        for (int i = 0; i < hexadecimal.length(); i++) {
            char caracter = hexadecimal.charAt(i);
            int posicionEnCaracteres = caracteresHexadecimales.indexOf(caracter);
            decimal = 16 * decimal + posicionEnCaracteres;
        }
        return decimal;
    }

// Validadores
    private static boolean validateBinario(String binario) {
        // Comprobar si solo se compone de unos y ceros
        String binarioComoCadena = String.valueOf(binario);
        for (int i = 0; i < binarioComoCadena.length(); i++) {
            char caracter = binarioComoCadena.charAt(i);
            if (caracter != '0' && caracter != '1') {
                return false;
            }
        }
        return true;
    }

    private static boolean validateOctal(int octal) {
        // comprobar si solo tiene números del 0 al 7
        String octalComoCadena = String.valueOf(octal);
        String caracteresOctales = "01234567";
        for (int i = 0; i < octalComoCadena.length(); i++) {
            char caracter = octalComoCadena.charAt(i);
            // Si no se encuentra dentro de los caracteres válidos, regresamos false
            if (caracteresOctales.indexOf(caracter) == -1) {
                return false;
            }
        }
        return true;
    }

    private static boolean validateHexadecimal(String hexadecimal) {
        hexadecimal = hexadecimal.toUpperCase();
        // Comprobar si solo tiene números del 0 al 9 y letras de la A a la F
        String caracteresHexadecimales = "0123456789ABCDEF";
        for (int i = 0; i < hexadecimal.length(); i++) {
            char caracter = hexadecimal.charAt(i);
            // Si no se encuentra dentro de los caracteres válidos, regresamos false
            if (caracteresHexadecimales.indexOf(caracter) == -1) {
                return false;
            }
        }
        return true;
    }

    private static String complementoA2(Integer valor) {
        String valorOriginal = decimalToBinario(valor.toString(), 8);
        StringBuilder complemento1 = new StringBuilder();
        String complemento2 = "";
        for (int i = 0; i < valorOriginal.length(); i++) {
            complemento1.append(1 ^ valorOriginal.charAt(i));
        }
        complemento2 = addBinary(complemento1.toString(), "1");
        return complemento2;
    }

    private static String addBinary(String a, String b) {
        Stack<Boolean> sa = new Stack<>();
        Stack<Boolean> sb = new Stack<>();
        for (char ch : a.toCharArray()) {
            sa.push(ch == '1');
        }
        for (char ch : b.toCharArray()) {
            sb.push(ch == '1');
        }
        Stack<Boolean> ans = new Stack<>();
        boolean ta, tb, s, car = false;
        while (true) {
            if (!sa.isEmpty()) {
                ta = sa.pop();
            } else {
                ta = false;
            }
            if (!sb.isEmpty()) {
                tb = sb.pop();
            } else {
                tb = false;
            }
            s = ta ^ tb ^ car;
            car = (ta & tb) | (ta & car) | (tb & car);
            ans.push(s);
            if (sa.empty() && sb.empty()) {
                break;
            }
        }
        if (car) {
            ans.push(car);
        }
        StringBuilder res = new StringBuilder();
        while (!ans.empty()) {
            boolean tmp = ans.pop();
            if (tmp) {
                res.append('1');
            } else {
                res.append('0');
            }
        }
        return res.toString();
    }
}
