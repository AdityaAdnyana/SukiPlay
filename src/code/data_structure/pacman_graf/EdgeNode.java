package code.data_structure.pacman_graf;

public class EdgeNode{
    EdgeNode next;
    int weight;
    VertexNode vertex;

    public EdgeNode(VertexNode vertex, int weight){
        this.vertex = vertex;
        this.weight = weight;
        this.next = null;
    }

    public VertexNode getVertex(){
        return vertex;
    }

    public int getWeight(){
        return weight;
    }
   
}


