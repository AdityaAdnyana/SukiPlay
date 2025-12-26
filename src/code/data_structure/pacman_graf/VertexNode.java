package code.data_structure.pacman_graf;

import java.util.Iterator;

public class VertexNode implements Iterable<EdgeNode>{
    int vertexID;   
    EdgeNode edgeHead;
    VertexNode next;

    public VertexNode(int vertex){
        this.vertexID = vertex;
        this.next = null;
    }

    public EdgeNode edgeHead(){
        return edgeHead;
    }

    public int getVertexID(){
        return vertexID;
    }

    @Override
    public Iterator<EdgeNode> iterator() {
        return new getAllEdge(this);
    }
}

class getAllEdge implements Iterator<EdgeNode>{

    EdgeNode current;

    getAllEdge(VertexNode vertex){
        this.current = vertex.edgeHead();
    }

    @Override
    public boolean hasNext() {
        return current != null;
    }

    @Override
    public EdgeNode next() {
        EdgeNode data = current;
        current = current.next;
        return data;
    }

}

