package code.data_structure.pacman_graf;

import java.util.Iterator;

public class Graf implements Iterable{
    static VertexNode head;
    int size;

    public Graf(){
        head = null;
        size = 0;
    }

    public void addVertex(int vertex){
        VertexNode newNode = new VertexNode(vertex);
        if (head == null){
            head = newNode;
        } else {
            VertexNode current = head;
            while(current.next != null){
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }

    public void addEdge(int sourceVertex, int destinationVertex, int weight){
        VertexNode source = getVertexNode(sourceVertex);
        VertexNode destination = getVertexNode(destinationVertex);

        if (source != null && destination != null){
            EdgeNode newEdge = new EdgeNode(destination, weight);
            if (source.edgeHead == null){
                source.edgeHead = newEdge;
            } else {
                EdgeNode current = source.edgeHead;
                while(current.next != null){
                    current = current.next;
                }
                current.next = newEdge;
            }
        }
    }

    public static VertexNode getVertexNode(int vertexID){
        VertexNode current = head;
        while(current != null){
            if (current.vertexID == vertexID){
                return current;
            }
            current = current.next;
        }
        return null;
    }

    public void clear(){
        head = null;
    }

    @Override
    public Iterator iterator() {
        return new getAllVertex(this);
    }
}

class getAllVertex implements Iterator{
    VertexNode current;

    getAllVertex(Graf graf){
        this.current = graf.head; //<-- Ambil head dari graf (Aditya)
    }

    @Override
    public boolean hasNext() {
        return current != null;
    }

    @Override
    public Integer next() {
        Integer vertexID = (Integer)current.vertexID;
        current = current.next;
        return vertexID;
    }

}
