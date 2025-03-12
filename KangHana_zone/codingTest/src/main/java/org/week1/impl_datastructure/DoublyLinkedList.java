package KangHana_zone.codingTest.src.main.java.org.week1.impl_datastructure;

import java.util.List;
import java.util.NoSuchElementException;

/*
* DDL 메소드 정리
* # add 관련
*   - addFirst o
*   - addLast o
*   - add (특정 위치까지 지정) o
*
* # remove 관련
*   - removeFirst o
*   - removeLast o
*   - remove (특정 위치까지 지정) o
*
* # search (특정 위치 지정) o
* # indexOf o
* # contains o
* # isEmpty o
* # size o
* # clear o
* */
public class DoublyLinkedList<E> {

    private Node<E> head;
    private Node<E> tail;
    private int size;

    public DoublyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public Node<E> search(int idx) {
        if (idx >= size || idx < 0) {
            throw new IndexOutOfBoundsException();
        }
        Node<E> now = head;
        for (int i = 0 ; i < idx ; i++) {
            now = now.next;
        }
        return now;
    }

    public void addFirst(E value) {
        Node<E> addNode = new Node<>(value);

        if (head == null) {
            head = addNode;
            tail = addNode;
        } else {
            addNode.next = head;
            head.prev = addNode;
            head = addNode;
        }
        size++;
    }

    public void addLast(E value) {
        Node<E> addNode = new Node<>(value);

        if (size == 0) {
            addFirst(value);
            return ;
        } else {
            addNode.prev = tail;
            tail.next = addNode;
            tail = addNode;
        }
        size++;
    }

    public void add(int idx, E value) {
        if (idx > size || idx < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (idx == 0) {
            addFirst(value);
            return;
        } else if (idx == size) {
            addLast(value);
            return;
        } else {
            Node<E> prevNode = search(idx - 1);
            Node<E> nextNode = prevNode.next;

            Node<E> addNode = new Node<>(value);
            prevNode.next = addNode;
            nextNode.prev = addNode;
            addNode.prev = prevNode;
            addNode.next = nextNode;
        }
        size++;
    }

    public E removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();

        Node<E> headNode = head;
        E value = head.value;
        Node<E> nextNode = head.next;

        head.next = null;
        head.value = null;

        if (headNode.next != null) {
            nextNode.prev = null;
        }
        head = nextNode;
        size--;

        if (size == 0) tail = null;
        return value;
    }

    public E removeLast() {
        if (isEmpty()) throw new NoSuchElementException();

        Node<E> tailNode = tail;
        E value = tail.value;
        Node<E> prevNode = tail.prev;

        tail.prev = null;
        tail.value = null;

        if (prevNode != null) {
            prevNode.next = null;
        }
        tail = prevNode;
        size--;

        if (size == 0) head = null;
        return value;
    }

    public E remove(int idx) {
        if (idx >= size || idx < 0) {
            throw new IndexOutOfBoundsException();
        }

        if (idx == 0) {
            return removeFirst();
        } else if (idx == size - 1) {
            return removeLast();
        } else {
            Node<E> removeNode = search(idx);
            Node<E> prevNode = removeNode.prev;
            Node<E> nextNode = removeNode.next;

            E value = removeNode.value;

            removeNode.next = null;
            removeNode.prev = null;
            removeNode.value = null;
            prevNode.next = nextNode;
            nextNode.prev = prevNode;

            size--;
            return value;
        }
    }

    public int indexOf(Object value) {
        int idx = 0;
        Node<E> now = head;
        while (idx < size) {
            if (now.value.equals(value)) return idx;
            now = now.next;
            idx++;
        }
        return -1;
    }

    public boolean contains(Object value) {
        if (indexOf(value) == -1) return false;
        return true;
    }

    public void clear() {
        if (head == null) return;

        Node<E> now = head;
        int idx = 0;
        while (idx < size) {
            Node<E> nextNode = now.next;
            now.prev = null;
            now.value = null;
            now.next = null;

            now = nextNode;
            idx++;
        }

        head = null;
        tail = null;
        size = 0;
    }

    static class Node<E> {

        Node<E> prev;
        Node<E> next;
        E value;

        // 생성자
        public Node(E value) {
            this.value = value;
            this.prev = null;
            this.next = null;
        }
    }
}


