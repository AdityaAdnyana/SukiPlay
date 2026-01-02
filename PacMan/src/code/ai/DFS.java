package code.ai;

import java.util.HashMap;
import code.data_structure.*;
import code.data_structure.pacman_graf.*;

public class DFS implements PathFinding{
    HashMap<VertexNode, Boolean> isVisited;
    HashMap<VertexNode, VertexNode> prev;
    Stack<VertexNode> stack;
    Stack<Integer> path;

    public DFS() {
        this.isVisited = new HashMap<>(500);
        this.prev = new HashMap<>(500);
        this.stack = new Stack<>();
        this.path = new Stack<>();
    }

    @Override
    public Stack<Integer> findPath(int startID, int targetID) {
        isVisited.clear();
        stack.clear();
        path.clear();
        prev.clear();

        boolean isFound = false;

        VertexNode start = Graf.getVertexNode(startID);
        VertexNode target = Graf.getVertexNode(targetID);

        if (start == null || target == null)
            return null;

        stack.push(start);

        while (!stack.isEmpty()) {
            VertexNode current = stack.pop();
            boolean isVisitedCurrent = isVisited.getOrDefault(current, false);

            if (current == target) {
                isFound = true;
                break;
            }

            if (!isVisitedCurrent) {
                isVisited.put(current, true);

                for (EdgeNode neighborEdge : current) {
                    VertexNode neighborVertex = neighborEdge.getVertex();

                    boolean isVisitedNeighbor = isVisited.getOrDefault(neighborVertex, false);

                    if (!isVisitedNeighbor) {
                        // isVisited.put(neighborVertex, null);
                        stack.push(neighborVertex);
                        prev.put(neighborVertex, current);
                    }
                }
            }
        }
        return getTargetPath(isFound, target);
    }

    public Stack<Integer> getTargetPath(boolean isFound, VertexNode target) {

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
