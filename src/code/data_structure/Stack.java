package code.data_structure;
class StackNode<T>{
    T data;
    StackNode<T> next;
    
    public StackNode(T data){
        this.data = data;
        this.next = null;
    }
    
}

public class Stack<T>{
    StackNode<T> top;

    public Stack(){
        this.top = null;
    }
    public T push(T data){
        StackNode<T> newNode = new StackNode<>(data);

        newNode.next = top;
        top = newNode;
        return data;
    }

    public T pop(){

        if(isEmpty()){
            System.out.println("Stack Is Empty");
            return null;
        }

        T poppedData = top.data;
        top = top.next;
        return poppedData;
    }

    public T peek(){
        if(isEmpty()){
            System.out.println("Stack Is Empty");
            return null;
        }
        return top.data;
    }

    public boolean isEmpty(){
        return top == null;
    }

    public void clear(){
        this.top = null;
    }
}
