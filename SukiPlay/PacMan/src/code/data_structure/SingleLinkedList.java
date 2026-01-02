package code.data_structure;

import java.util.Iterator;

class SLLNode<T>{
    SLLNode<T> next;
    T data;
    boolean isDeleted;

    public SLLNode(T data){
        this.data = data;
        this.next = null;
    }

    public SLLNode<T> getNext(){
        return next;
    }

    public T getData(){
        return data;
    }
}

public class SingleLinkedList<T> implements Iterable<T>{
    SLLNode<T> head;
    int softDeletedCount;
    int sizeCount;

    public boolean isEmpty(){
        return head == null || softDeletedCount == sizeCount;
    }

    public int getSize(){
        return sizeCount;
    }

    public SLLNode<T> getHead(){
        return head;
    }
    
    public T insertFirst(T data){
        SLLNode<T> nn = new SLLNode<>(data);
        if(head == null){
            head = nn;
            sizeCount++;
            return head.data;
        }

        nn.next = head;
        head = nn;
        sizeCount++;
        return head.data;
    }

    public T insertLast(T data){
        SLLNode<T> nn = new SLLNode<>(data);

        if(head == null){
            head = nn;
            sizeCount++;
            return head.data;
        }

        SLLNode<T> current = head;
        while (current.next != null) {
            current = current.next;
        }
        current.next = nn;
        sizeCount++;
        return nn.data;
    }

    public T insertAfter(T searchData, T newData){
        SLLNode<T> nn = new SLLNode<>(newData);

        SLLNode<T> current = head;

        while (current != null){
            if (current.data.equals(searchData)) {
                nn.next = current.next;
                current.next = nn;
                sizeCount++;
                return nn.data;
                
            }
            current = current.next;
        }
        ////System.out.println(searchData + "is not found");
        return null;
    }

    //============//
    //Delete First//
    //============//
    public void softDeleteFirst(){
        deleteFirst(false);
    }
    public void hardDeleteFirst(){
        deleteFirst(true);
    }

    private void deleteFirst(boolean permanent){
        if(head == null){
            ////System.out.println("No Data Found");
            return;
        }
        if(permanent){
            ////System.out.println("First Data: " + head.data.toString() + " Is PERMANENTLY deleted");
            if(head.isDeleted) softDeletedCount--;
            head = head.next;
            sizeCount--;
            
        }else{
            if(head.isDeleted == false){
                ////System.out.println("First Data: " + head.data.toString() + " Is SOFTLY deleted");
                softDeletedCount++;
                head.isDeleted = true;
            }
                
        }
    }

    //===========//
    //Delete Last//
    //===========//
    public void softDeleteLast(){
        deleteLast(false);
    }
    public void hardDeleteLast(){
        deleteLast(true);
    }

    private void deleteLast(boolean permanent){
        if(head == null){
            ////System.out.println("No Data Found");
            return;
        }
        if(head.next == null){
            deleteFirst(permanent);
            return;
        }

        SLLNode<T> current = head;
        while (current.next.next != null) {
            current = current.next;
        }
        if(permanent){
            ////System.out.println("Last Data: " + current.next.data.toString() + " Is PERMANENTLY deleted");
            if(current.next!= null && current.next.isDeleted == true) softDeletedCount--;
            current.next = null;
            sizeCount--;
        }else{
            if(!current.next.isDeleted){
                ////System.out.println("Last Data: " + current.next.data.toString() + " Is SOFTLY deleted");
                current.next.isDeleted = true;
                softDeletedCount++;
            }
        }
        return;
    }

    //===========//
    //Delete Node//
    //===========//
    public void softDeleteNode(T data){
        deleteNode(false, data);
    }
    public void hardDeleteNode(T data){
        deleteNode(true, data);
    }

    private void deleteNode(boolean permanent, T data){
        if(head == null){
       //     System.out.println("No Data Found");
            return;
        }

        if(head.data.equals(data)){
            deleteFirst(permanent);
            return;
        }
        
        if(head.next == null){
            //System.out.println("No Data Found");
            return;
        }

        SLLNode<T> current = head;
        while (current.next != null && !current.next.data.equals(data)) {
            current = current.next;
        }
        
        if (current.next == null) {
            //System.out.println("Data " + data + " Is Not Found");
            return;
        }

        if (permanent) { 
            //System.out.println("Data: " + current.next.data.toString() + " Is PERMANENTLY deleted");
            if (current.next.isDeleted) softDeletedCount--;
            current.next = current.next.next;
            sizeCount--;
           
        }else{
            if(!current.next.isDeleted){
                softDeletedCount++;
                current.next.isDeleted = true;
                //System.out.println("Data: " + current.next.data.toString() + " Is SOFTLY deleted");
            }
        }
        return;
    }

    //=============//
    //Recover First//
    //=============// 
    public T recoverFirst(){
        if(head != null){
            if(!head.isDeleted){
                //System.out.println("There Is Only One Node, And Head Is Not Currently Deleted");
                return head.data;
            }else{
                head.isDeleted = false;
                //System.out.println(head.data.toString() + " Recovered");
                softDeletedCount--;
                return head.data;
            }
        }

        //System.out.println("No Data Found");
        return null;
    }

    //============//
    //Recover Last//
    //============//
    public T recoverLast(){
        SLLNode<T> current = head;
        if(head == null){
            //System.out.println("No Data Found");
            return null;
        }

        if(head.next == null){
            return recoverFirst();
        }

        while (current.next != null) {
            current = current.next;
        }

        if(current.isDeleted){
            softDeletedCount--;
           current.isDeleted = false;
           //System.out.println(current.data.toString() + " Recovered");
        }else{
           //System.out.println("The Last Node " + current.data.toString() + " Is Not Deleted");
        }
        return current.data;
    }

    //============//
    //Recover Node//
    //============//
    public T recoverNode(T data){
        SLLNode<T> current = head;

        if(head == null){
            //System.out.println("No Data Is Found");
            return null;
        }
        if(head.data.equals(data)){
            return recoverFirst();
        }

        while (current != null && !current.data.equals(data)) {
            current = current.next;
        }

        if(current != null){
            if(current.isDeleted){
                softDeletedCount--;
                current.isDeleted = false;
            }
            //System.out.println(current.data.toString() + " Recovered");
            return current.data;
        }

        //System.out.println("Data " + data + " Is Not Found");
        return null;
    }

    public void recoverAll(){
        SLLNode<T> current = head;

        while (current != null) {
            current.isDeleted = false;
            current = current.next;
        }
        softDeletedCount = 0;
    }

    //======//
    //Search//
    //======//
    public T searchData(T data){
        SLLNode<T> current = head;

        while (current!= null && !current.data.equals(data)) {
            current = current.next;
        }

        if(current != null && !current.isDeleted){
            //System.out.println("Data " + data + " Is Found In The List");
            return current.data;
        }else {
            //System.out.println("Data " + data + " Is Not Found");
            return null;
        }
    }

    public T searchName(String name){
        SLLNode<T> current = head;

        while (current!= null && !current.data.equals(name)) {
            current = current.next;
        }

        if(current != null && !current.isDeleted){
            //System.out.println("Data " + name + " Is Found In The List");
            return current.data;
        }else {
            //System.out.println("Data " + name + " Is Not Found");
            return null;
        }
    }

    //=======//
    //Display//
    //=======//
    public String display(){
        SLLNode<T> current = head;

        StringBuilder allData = new StringBuilder();
        
        while (current != null) {
            if (!current.isDeleted) {
                allData.append(current.data.toString()).append("\n");
            }
            current = current.next;
        }
        return allData.toString();
    }

    public void clear(){
        head = null;
    }

    @Override
    public Iterator<T> iterator() {
        return new ListIterator<T>(this);
    }
}

class ListIterator<T> implements Iterator<T>{

    SLLNode<T> current;

    public ListIterator(SingleLinkedList<T> list){
        this.current = list.getHead();
        moveToValidNext();
    }

    public void moveToValidNext(){
        while(current != null && current.isDeleted){
            current = current.getNext();
        }
    }

    @Override
    public boolean hasNext() {
       return current != null;
    }

    @Override
    public T next() {
        T data = current.getData();
        
        current = current.getNext();

        moveToValidNext();

        return data;

    }

}

//@AdityaAdnyana's SLL Created 12/8/25