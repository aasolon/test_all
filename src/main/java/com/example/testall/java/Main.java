package com.example.testall.java;

public class Main {

    public static void main(String[] args) {
        String vat = "A";
        String result = "";
        switch (vat) {
            case "INCLUDED":
                result = "INCLUDED";
                break;
            case "MANUAL":
                result = "MANUAL";
                break;
            case "NO_VAT":
                result = "WITHOUT_VAT";
                break;
            case "ONTOP":
                result = "ONTOP";
                break;
        }
        int i = 0;
    }
}
