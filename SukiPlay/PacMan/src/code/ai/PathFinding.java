package code.ai;

import code.data_structure.Stack;

public interface PathFinding {
    public Stack<Integer> findPath(int startID, int targetID);
}
