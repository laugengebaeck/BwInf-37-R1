package de.lukasrost.bwinf2019.superstar;

public class Main {

    public static void main(String[] args) {
        System.out.println("Bitte Datei auswählen!");
        SuperstarHelper helper = new SuperstarHelper();
        helper.showFileSelectionWindow();
        helper.generateSolution();
        System.out.println(helper.getOutput());
    }
}
