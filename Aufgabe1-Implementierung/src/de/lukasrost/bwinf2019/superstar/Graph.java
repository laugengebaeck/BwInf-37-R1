package de.lukasrost.bwinf2019.superstar;

import java.util.ArrayList;
import java.util.Arrays;

class Graph {
    private ArrayList<Vertex> vertices = new ArrayList<>();
    private ArrayList<Vertex> visited = new ArrayList<>();
    private Vertex current;
    private int anfrageCounter = 0;

    Graph(Vertex... nodes1){
        vertices.addAll(Arrays.asList(nodes1));
        current = vertices.get(0);
    }

    int getAnfrageCounter() {
        return anfrageCounter;
    }

    private boolean hasEdge(Vertex start, Vertex end){
        anfrageCounter++;
        return start.getAdjacency().contains(end);
    }

    String modifiedDFS(){
        return modifiedDFS(current,null);
    }

    private String modifiedDFS(Vertex start, Vertex parent){
        visited.add(start);
        Vertex next = null;

        for (Vertex vertex : vertices) {
            if (!vertex.equals(start) && !visited.contains(vertex) && hasEdge(start,vertex)){
                next = vertex;
                break;
            }
        }

        if (next != null){
            return modifiedDFS(next,start);
        } else {
            for (Vertex vis: visited){
                if (!vis.equals(start) && hasEdge(start,vis)){
                    return "";
                }
            }
            for (Vertex vertex : vertices){
                if (!vertex.equals(start) && !vertex.equals(parent) && !hasEdge(vertex,start)){
                    return "";
                }
            }
            return start.getContent();
        }
    }
}
