package de.lukasrost.bwinf2019.superstar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class Vertex {
    private String content;
    private ArrayList<Vertex> adjacency = new ArrayList<>();

    Vertex(String content){
        this.content = content;
    }

    String getContent() {
        return content;
    }

    ArrayList<Vertex> getAdjacency() {
        return adjacency;
    }

    void addAllToAdjacency(Vertex... vertices){
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
