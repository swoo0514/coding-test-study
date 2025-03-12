package org.week1.impl_datastructure;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class DoublyLinkedListTest {

    private DoublyLinkedList<Integer> ddl;
    @BeforeEach
    void before() {
        ddl = new DoublyLinkedList<>();
        ddl.addFirst(3);
        ddl.addLast(5);
    }

    @Test
    void size() {
        assertThat(ddl.size()).isEqualTo(2);
    }

    @Test
    void isEmpty() {
        boolean ans = ddl.isEmpty();
        assertThat(ans).isFalse();
    }

    @Test
    void search() {
        int val = ddl.search(1).getValue();
        Assertions.assertThat(val).isEqualTo(5);
    }

    @Test
    void addFirst() {
        ddl.addFirst(100);
        ddl.addFirst(50);
        assertThat(ddl.search(0).getValue()).isEqualTo(50);
    }

    @Test
    void addLast() {
        ddl.addLast(80);
        ddl.addLast(10);
        assertThat(ddl.search(ddl.size() - 1).getValue()).isEqualTo(10);
    }

    @Test
    void add() {
        ddl.add(1, 100);
        assertThat(ddl.search(1).getValue()).isEqualTo(100);
    }

    @Test
    void removeFirst() {
        Integer value = ddl.removeFirst();
        assertThat(value).isEqualTo(3);
    }

    @Test
    void removeLast() {
        Integer value = ddl.removeLast();
        assertThat(value).isEqualTo(5);
    }

    @Test
    void remove() {
        ddl.removeFirst();
        Integer value = ddl.remove(0);
        assertThat(value).isEqualTo(5);
    }

    @Test
    void indexOf() {
        int idx = ddl.indexOf(5);
        assertThat(idx).isEqualTo(1);
    }

    @Test
    void contains() {
        assertThat(ddl.contains(3)).isTrue();
    }

    @Test
    void clear() {
        ddl.clear();
        assertThrows(NoSuchElementException.class, () -> ddl.removeFirst());
    }
}