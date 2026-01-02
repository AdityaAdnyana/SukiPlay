package code.ai;

import java.util.HashMap;

import code.data_structure.pacman_graf.*;
import code.data_structure.PriorityQueue;
//import code.data_structure.SingleLinkedList;
import code.data_structure.Stack;

public class Dijkstra implements PathFinding{
    private HashMap<VertexNode, VertexNode> prev;
    private HashMap<VertexNode, Integer> minDist;
    private HashMap<VertexNode, Boolean> isVisited;
    private PriorityQueue<VertexNode> PQ;
    private Stack<Integer> shortestPath;

    public Dijkstra() {
        prev = new HashMap<>(500);
        minDist = new HashMap<>(500);
        isVisited = new HashMap<>(500);
        PQ = new PriorityQueue<>();
        shortestPath = new Stack<Integer>();
    }
    
    @Override
    public Stack<Integer> findPath(int startID, int targetID) {
        minDist.clear();
        prev.clear();
        isVisited.clear();
        PQ.clear();
        shortestPath.clear();

        VertexNode startNode = Graf.getVertexNode(startID);
        VertexNode targetNode = Graf.getVertexNode(targetID);

        if(startNode == null || targetNode == null) return null;

        // if (startNode == null) {
        //     System.out.println("ERROR: Hantu berdiri di tile invalid! ID: " + startID);
        //     return null;
        // }
        // if (targetNode == null) {
        //     System.out.println("ERROR: Pacman berdiri di tile invalid! ID: " + targetID);
        //     return null;
        // }

        System.out.println(startNode.getVertexID());
        System.out.println(targetNode.getVertexID());

        minDist.put(startNode, 0);
        PQ.enqueue(startNode, 0);

        while (!PQ.isEmpty()) {
            VertexNode currentVertex = PQ.dequeue();

            // if(currentVertex == targetNode) break;

            if (!(isVisited.getOrDefault(currentVertex, false))) {
                isVisited.put(currentVertex, true);
                for (EdgeNode neighborEdge : currentVertex) {
                    VertexNode neighborVertex = neighborEdge.getVertex();

                    int newDist = minDist.get(currentVertex) + neighborEdge.getWeight();
                    int oldDist = minDist.getOrDefault(neighborVertex, Integer.MAX_VALUE);

                    if (newDist < oldDist) {
                        minDist.put(neighborVertex, newDist);
                        prev.put(neighborVertex, currentVertex);
                        PQ.enqueue(neighborVertex, newDist);
                    }
                }
            }

        }

        return getTargetShortestPath(targetNode);

    }

    private Stack<Integer> getTargetShortestPath(VertexNode target) {
        Integer targetMinDist = minDist.getOrDefault(target, Integer.MAX_VALUE);
        System.out.println(targetMinDist);
        if (targetMinDist == Integer.MAX_VALUE)
            return null;

        VertexNode current = target;

        while (prev.get(current) != null) {
            shortestPath.push(current.getVertexID());
            current = prev.get(current);
        }
        shortestPath.push(current.getVertexID());

        return shortestPath;
    }

}
