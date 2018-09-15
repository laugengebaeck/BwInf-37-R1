package de.lukasrost.bwinf2019.superstar;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

class SuperstarHelper {
    private File inputFile;
    private ArrayList<Vertex> vertices = new ArrayList<>();
    private HashMap<String,Vertex> nameToVertex = new HashMap<>();
    private Graph graph;
    private String superStar;

    void showFileSelectionWindow(){
        //Benutzerauswahl der einzulesenden Datei und Umwandlung in ein File-Objekt
        JFileChooser chooser = new JFileChooser();
        File file = null;
        int rueckgabeWert = chooser.showOpenDialog(null);
        if (rueckgabeWert == JFileChooser.APPROVE_OPTION){
            file = chooser.getSelectedFile();
        } else {
            System.exit(0);
        }
        inputFile = file;
    }

    void readToGraph(){ //Einlesen der Eingabedatei
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile)))
        {
            boolean first = true;
            for (String line; (line = br.readLine()) != null;)
            {
                if (first){ //erste Zeile: Knoten
                    first = false;
                    for (String name : line.split(" ")){
                        Vertex v = new Vertex(name);
                        vertices.add(v);
                        nameToVertex.put(name,v);
                    }
                } else { //andere Zeilen: Kanten
                    String[] edge = line.split(" ");
                    nameToVertex.get(edge[0]).addAllToAdjacency(nameToVertex.get(edge[1]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        graph = new Graph(vertices.toArray(new Vertex[0]));
    }

    void generateSolution(){ //Tiefensuche durchführen
        superStar = graph.modifiedDFS();
    }

    String getOutput(){ //Ausgabe auf die Konsole
        if (!"".equals(superStar)){
            return "Superstar ist " + superStar + ".\nAnzahl der Anfragen: " + graph.getAnfrageCounter();
        } else {
            return "Es gibt keinen Superstar.\nAnzahl der Anfragen: " + graph.getAnfrageCounter();
        }
    }
}
