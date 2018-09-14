package de.lukasrost.bwinf2019.superstar;

import java.util.ArrayList;
import java.util.Arrays;

class Graph {
    private ArrayList<Vertex> vertices = new ArrayList<>();
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
        return modifiedDFS(current,new ArrayList<>(),null);
    }

    private String modifiedDFS(Vertex start, ArrayList<Vertex> visited, Vertex parent){
        visited.add(start);
        Vertex vt = null;

        for (Vertex vertex : vertices) {
            if (!vertex.equals(start) && !visited.contains(vertex) && hasEdge(start,vertex)){
                vt = vertex;
                break;
            }
        }

        if (vt != null){
            return modifiedDFS(vt,visited,start);
        } else {
            for (Vertex vertex : vertices){
                if (!vertex.equals(start) && !vertex.equals(parent) && !hasEdge(vertex,start)){
                    return "";
                }
            }
            return start.getContent();
        }
    }
}
