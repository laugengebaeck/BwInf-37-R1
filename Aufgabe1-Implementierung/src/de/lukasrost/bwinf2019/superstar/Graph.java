package de.lukasrost.bwinf2019.superstar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Graph {
    private ArrayList<Vertex> vertices = new ArrayList<>();
    private Vertex current;

    public Graph(Vertex... nodes1){
        vertices.addAll(Arrays.asList(nodes1));
        current = vertices.get(0);
    }

    public void setCurrent(Vertex vertex){
        current = vertex;
    }

    public ArrayList<Vertex> depthFirstSearch(Vertex goal){
        return depthFirstSearch(current,goal,new ArrayList<>());
    }

    public ArrayList<Vertex> depthFirstSearch(Vertex start, Vertex goal, ArrayList<Vertex> visited){
        if (start.equals(goal)){
            System.out.println("[FOUND] Knoten gefunden " + start.toString());
            return visited;
        } else {
            visited.add(start);
            System.out.println("[EXPAND] Besuche Knoten " + start.toString() );
            for (Vertex vt : start.getAdjacency()){
                if (!visited.contains(vt)){
                    visited = depthFirstSearch(vt,goal,visited);
                }
            }
        }
        return visited;
    }

    public ArrayList<Vertex> breadthFirstSearch(Vertex goal){
        return breadthFirstSearch(current,goal);
    }

    public ArrayList<Vertex> breadthFirstSearch(Vertex start, Vertex goal){
        ArrayList<Vertex> visited = new ArrayList<>();
        LinkedList<Vertex> queue = new LinkedList<>();
        queue.push(start);
        visited.add(start);
        System.out.println("[EXPAND] Besuche Knoten " + start.toString());
        while (!queue.isEmpty()){
            Vertex v = queue.pop();
            if (v.equals(goal)){
                System.out.println("[FOUND] Knoten gefunden: " + v.toString());
                return visited;
            }
            for (Vertex child: v.getAdjacency()) {
                if (!visited.contains(child)){
                    queue.push(child);
                    visited.add(child);
                    System.out.println("[EXPAND] Besuche Knoten " + child.toString());
                }
            }
        }
        return visited;
    }
}
