package com.company;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BST<J extends Comparable<J>, R> implements Iterable<J> {
    private Node root;

    public BST(){
        root = null;
    }

    private class Node{
        private J key;
        private R value;
        private Node Left, Right;

        private Node(J key, R value){
            this.key = key;
            this.value= value;
            Left = null;
            Right = null;
        }

        @Override
        public String toString() {
            return "[" + "key=" + key + " | value=" + value + "]";
        }
    }

    public void put(J key, R value){
        root = put(root, key, value);
    }
    private Node put(Node cursor, J key, R value){
        if (cursor == null){
            cursor = new Node(key, value);
            return cursor;
        }

        int compareRes = key.compareTo(cursor.key);

        if (compareRes < 0) cursor.Left = put(cursor.Left, key, value);
        else if(compareRes > 0) cursor.Right = put(cursor.Right, key, value);
        else System.out.println("Exists!");

        return cursor;
    }


    public R get(J key){
        return get(root, key);
    }
    private R get(Node cursor, J key){
        if (cursor == null) return null;
        if (cursor.key.compareTo(key) == 0) return cursor.value;
        if (cursor.key.compareTo(key) > 0) return get(cursor.Left, key);

        return get(cursor.Right, key);
    }

    public void delete(J key){
        delete(root, key);
    }
    private Node delete(Node cursor, J key){
        if (cursor == null) return cursor;

        int compareRes = key.compareTo(cursor.key);

        if (compareRes < 0) cursor.Left = delete(cursor.Left, key);
        else if (compareRes > 0) cursor.Right = delete(cursor.Right, key);
        else {
            if (cursor.Left == null) return cursor.Right;
            else if (cursor.Right == null) return cursor.Left;

            cursor.key = minNode(cursor.Right);
            cursor.Right = delete(cursor.Right, cursor.key);
        }
        return cursor;
    }

    private J minNode(Node cursor){
        J min = cursor.key;

        while(cursor.Left != null){
            min = (cursor.Left).key;
            cursor = cursor.Left;
        }

        return min;
    }
    private J maxNode(Node cursor){
        J max = cursor.key;

        while(cursor.Right != null){
            max = (cursor.Right).key;
            cursor = cursor.Right;
        }

        return max;
    }



    public Iterator<J> iterator(){
        return new BSTIterator(root);
    }

    private class BSTIterator implements Iterator<J>{
        List<J> keys = new ArrayList<>();
        Iterator<J> iterator;

        public BSTIterator(Node root) {
            traverse(root);
            iterator = keys.iterator();
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public J next() {
            return iterator.next();
        }

        @Override
        public void remove() {
            Iterator.super.remove();
        }

        private void traverse(Node cursor) {
            if (cursor == null) return;

            if (cursor.Left != null) traverse(cursor.Left);

            keys.add(cursor.key);

            if (cursor.Right != null) traverse(cursor.Right);
        }
    }


    public void inOrder(){
        inOrder(root);
    }

    public void postOrder(){
        postOrder(root);
    }

    private void postOrder(Node cursor){
        if (cursor != null){
            postOrder(cursor.Right);

            System.out.println(cursor);

            postOrder(cursor.Left);
        }
    }

    private void inOrder(Node cursor){
        if (cursor != null){
            inOrder(cursor.Left);

            System.out.println(cursor);

            inOrder(cursor.Right);
        }
    }

    private int size = 0;

    public int size(){
        size(root, 0);
        return size;
    }
    private void size(Node cursor, int size){
        this.size = size;
        if (cursor != null){
            size(cursor.Left, this.size);
            size(cursor.Right, this.size + 1);
        }
    }

}
