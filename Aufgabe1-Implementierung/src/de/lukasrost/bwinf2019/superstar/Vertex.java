package de.lukasrost.bwinf2019.superstar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class Vertex {
    private String content; //Inhalt
    private ArrayList<Vertex> adjacency = new ArrayList<>(); //Adjazenzliste

    Vertex(String content){
        this.content = content;
    }

    String getContent() {
        return content;
    }

    ArrayList<Vertex> getAdjacency() {
        return adjacency;
    }

    void addAllToAdjacency(Vertex... vertices){ //Knoten zu Adjazenzliste hinzufügen
        adjacency.addAll(Arrays.asList(vertices));
        adjacency.sort(Comparator.comparing(Vertex::getContent));
    }

    @Override
    public String toString(){
        return "Vertex[" + content + "]";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Vertex && content.equals(((Vertex) obj).getContent());
    }
}
