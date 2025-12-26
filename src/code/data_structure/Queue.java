package code.data_structure;
class QueueNode<T> {
    QueueNode<T> next;
    
    T data;

    public QueueNode(T data){
        this.data = data;
        this.next = null;
    }
}

public class Queue<T> {
    QueueNode<T> head;
    QueueNode<T> tail;
    
    public Queue(){
        head = null;
        tail = null;
    }

    public T enqueue(T data){
        QueueNode<T> nn = new QueueNode<>(data);
        if(head == null && tail == null){
            head = nn;
            tail = nn;
            head.next = tail;
            return nn.data;
        }

        tail.next = nn;
        tail = tail.next;
        return nn.data;
    }

    public T dequeue(){
        T data;

        if(head == null){
            System.out.println("No Queue");
            return null;
        }

        data = head.data;
        if (head == tail) {
            head = null;
            tail = null;
            return data;
        }

        head = head.next;
        return data;
    }

    public T front(){
        return head.data;
    }

    public boolean isEmpty(){
        return head == null;
    }

    public void clear(){
        this.head = null;
        this.tail = null;
    }
}
