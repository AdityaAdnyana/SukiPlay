package code.ai;

import code.data_structure.*;
import code.data_structure.pacman_graf.*;

import java.util.HashMap;

public class BFS implements PathFinding{
    private HashMap<VertexNode, Boolean> isVisited;
    private HashMap<VertexNode, VertexNode> prev;
    private Queue<VertexNode> queue;
    private Stack<Integer> path;

    public BFS(){
        isVisited = new HashMap<>(500);
        prev = new HashMap<>(500);
        queue = new Queue<>();
        path = new Stack<>();
    }

    @Override
    public Stack<Integer> findPath(int startID, int targetID) {
        isVisited.clear();
        prev.clear();
        queue.clear();
        path.clear();
        boolean isFound = false;

        VertexNode start = Graf.getVertexNode(startID);
        VertexNode target = Graf.getVertexNode(targetID);

        if(start == null || target == null) return null;

        queue.enqueue(start);
        
        while(!queue.isEmpty()){
            VertexNode current = queue.dequeue();
            boolean isVisitedCurrent = isVisited.getOrDefault(current, false);
            
            if(!isVisitedCurrent){
                isVisited.put(current, true);

                if(current == target){
                    isFound = true;
                    break;
                }
                   
                for(EdgeNode neighbor : current){
                    VertexNode neighborVertex =  neighbor.getVertex();
                    boolean isVisitedNeighbor = isVisited.getOrDefault(neighborVertex, false);
                    
                    if(!isVisitedNeighbor){
                        queue.enqueue(neighborVertex);
                        prev.put(neighborVertex, current);
                    }
                }
            }
        }
        return getTargetPath(isFound, target);
    }

    private Stack<Integer> getTargetPath(boolean isFound, VertexNode target){
       
        if (isFound) {
            VertexNode current = prev.get(target);
            path.push(target.getVertexID());

            while (current != null) {
                Integer currentVertexID = current.getVertexID();

                path.push(currentVertexID);

                current = prev.get(current);
            }
        }
        return path;
    }

}
