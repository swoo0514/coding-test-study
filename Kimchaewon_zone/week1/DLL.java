/* 링크드 리스트(DLL) 구현하기
 *  자바에서는 링크드 리스트를 클래스로 가지고 있기 때문에
 *  직접 클래스를 만들어 구현합니다. */

import java.util.Scanner;
class Node {
    // prev | data | next
    int data;
    Node prev, next;

    // 노드 생성 시
    public Node(int data){
        this.data = data;
        this.prev = null;
        this.next = null;
    }
}

class DLL {
    Node head, tail;
    public DLL(){
        this.head = null;
        this.tail = null;
    }

    public int get(int index){ // DLL 탐색
        if (index < 0 || index >= this.size()){ // 배열의 위치가 OutofBounds일 때
            return -1;
        }
        Node current = head;
        int n = 0;
        while(n != index){          // head에서부터 원하는 위치가 될 때까지
            current = current.next; // 포인터를 움직입니다.
            n += 1;
        }
        return current.data;        // 해당 위치의 값을 반환합니다.
    }

    // 올바른 탐색을 위해서는 DLL의 크기를 알 수 있어야 합니다.
    public int size(){
        int idx = 0;
        Node current = head;
        while (current != null){ // current != tail로 했을 때는 끝의 한 요소를 세지 않습니다.
                                 // null로 설정하면, 비어있을 때도 size를 0으로 반환할 수 있습니다.
            idx += 1;
            current = current.next;
        }
        return idx;
    }

    public boolean isEmpty(){ // DLL이 비어있는지 확인합니다.
        if (this.size() == 0) return true;
        return false;
    }

    // 삽입 부분
    public void insertFront(int data){ // DLL의 제일 앞에 삽입합니다.
        Node newNode = new Node(data);
        if (this.isEmpty()){ // DLL이 비어있을 때는 head와 tail을 설정해줍니다.
            this.head = newNode;
            this.tail = newNode;
        }
        else{ // DLL이 비어있지 않을 때
            newNode.next = this.head; // head가 가리키고 있는 노드를 newNode와 연결합니다.
            this.head.prev = newNode; // 원래 head가 가리켰던 노드의 prev가 newNode를 가리키도록 합니다.
            this.head = newNode;      // head를 newNode로 설정합니다.
        }
    }
    public void insertBack(int data){ // DLL의 맨 뒤에 삽입합니다.
        Node newNode = new Node(data);
        if (this.isEmpty()){
            this.head = newNode;
            this.tail = newNode;
        }
        else{
            newNode.prev = this.tail; // newNode의 prev가 기존의 맨 뒤 노드를 가리키도록 합니다.
            this.tail.next = newNode; // 기존의 맨 뒷 노드가 newNode를 가리키도록 합니다.
            this.tail = newNode;      // tail을 newNode로 설정합니다.
        }
    }
    // 원하는 위치에 삽입
    public void insertAt(int data, int index){
        Node newNode = new Node(data);
        if (this.isEmpty()) {
            this.head = newNode;
            this.tail = newNode;
        }
        else if (index == 0) this.insertFront(data);
        else if (index == (this.size())) this.insertBack(data);
        else if (index < 0 || index >= this.size()){
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds");
        }
        else{
            Node current = head;
            int idx = 0;
            while (idx != (index - 1)){ // 원하는 위치의 한 칸 앞까지 currentNode를 옮깁니다.
                current = current.next;
                idx += 1;
            }
            // 만약 current <-> newNode <-> nextNode 순을 원한다면
            newNode.next = current.next; // newNode-> nextNode
            newNode.prev = current;      // current <- newNode
            current.next.prev = newNode; // newNode <- nextNode
            current.next = newNode;      // current -> newNode

        }
    }

    // 삭제
    // 자바에서는 Garbage Collector가 있어서 memory free를 안해도 된다고 합니다.
    public void deleteFront(){ // 맨 앞의 노드를 삭제합니다.
        if (this.isEmpty()) {
            System.out.println("비어있어서 삭제할 수 없습니다.");
            return;
        }
        else {
            this.head = this.head.next; // 기존의 head가 가리키던 다음 노드를 head로 설정합니다.
            this.head.prev = null;      // 바뀐 headNode의 prev를 null로 하여 맨 앞의 노드와의 연결을 끊습니다.
        }
    }
    // 맨 뒤 삭제
    public void deleteBack(){ // 맨 뒷 노드를 삭제합니다.
        if (this.isEmpty()) {
            System.out.println("비어있어서 삭제할 수 없습니다.");
            return;
        }
        else {
            this.tail = this.tail.prev; // 기존의 tail이 가리키던 이전 노드를 tail로 설정합니다.
            this.tail.next = null;      // 바뀐 tailNode의 next를 null로 하여 맨 뒤의 노드와의 연결을 끊습니다.
        }
    }

    public void deleteAt(int index){  // 원하는 위치의 노드를 삭제합니다.
        if (index < 0 || index >= this.size()){
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds");
        }
        else if (this.isEmpty()){
            System.out.println("비어있어서 삭제할 수 없습니다.");
        }
        else if (index == 0) this.deleteFront();
        else if (index == this.size() - 1) this.deleteBack();
        else {
            Node current = head;
            int idx = 0;
            while (idx != index){ // 삭제할 노드까지 currentNode를 이동시킵니다.
                current = current.next;
                idx += 1;
            }
            // prevNode <-> currentNode <-> nextNode에서 currentNode를 삭제한다고 할 때,
            current.next.prev = current.prev;  // prevNode <- nextNode
            current.prev.next = current.next;  // prevNode -> nextNode

            current.next = null; // currentNode -> X
            current.prev = null; // X <- currentNode
        }
    }

    // 원하는 값을 삭제(같은 값이 있을 때는, 앞에 있는 노드를 삭제합니다.)
    public void deleteValue(int value){
        Node current = head;
        int idx = 0;
        // 유효한 범위에 있고, value값을 가진 노드의 index를 찾습니다.
        while (idx >= 0 && idx < this.size() && current.data != value){
            current = current.next;
            idx += 1;
        }
        if (idx == this.size()) // 원하는 값을 찾지 못했다면,
            throw new IndexOutOfBoundsException("Value " + value + " does not exist");
        this.deleteAt(idx); // 원하는 값을 찾았다면, 해당 위치의 노드를 삭제합니다.
    }

    // 출력
    public void print(){
        for (int i = 0; i < this.size(); i++){
            System.out.print(this.get(i)+ " ");
        }
        System.out.println();
    }

}
public class Main {
    public static void main(String[] args) {
        // 입력
        Scanner sc = new Scanner(System.in);
        DLL dll = new DLL();

        // 삽입
        dll.insertFront(5);
        dll.insertBack(6);
        dll.insertAt(7, 1);
        dll.insertAt(4, 0);// [4 5 7 6]
        dll.insertAt(5,2); // [4 5 5 7 6]
        dll.print();

        // 삭제
        dll.deleteAt(3); // [4 5 5 6]
        dll.deleteValue(5); // [4 5 6]
        dll.deleteFront(); // [5 6]
        dll.deleteBack(); // [5]

        // 탐색
        dll.insertBack(7);
        System.out.println(dll.get(1)); // [7]

        // 출력
        dll.print();
    }
}
