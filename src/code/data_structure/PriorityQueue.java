package code.data_structure;
class PriorityQueueNode<T> {
    PriorityQueueNode<T> next;
    
    T data;
    double priority;

    public PriorityQueueNode(T data, double priority){
        this.data = data;
        this.priority = priority;
        this.next = null;
    }
}

public class PriorityQueue<T> {
    PriorityQueueNode<T> head;
    
    public PriorityQueue(){
        head = null;
    }

    public T enqueue(T data, double priority){
        PriorityQueueNode<T> nn = new PriorityQueueNode<>(data, priority);
        if(head == null || head.priority > priority){
            nn.next = head;
            head = nn;
            return data;
        }

        PriorityQueueNode<T> current = head;
        while (current.next != null && current.next.priority <= priority) {
            current = current.next;
        }

        nn.next = current.next;
        current.next = nn;
        return nn.data;
    }

    public T dequeue(){
        T data;

        if(head == null){
            System.out.println("No Priority Queue");
            return null;
        }

        data = head.data;
        head = head.next;
        return data;
    }

    public T front(){
        return head.data;
    }

    public boolean isEmpty(){
        return head == null;
    }
}
