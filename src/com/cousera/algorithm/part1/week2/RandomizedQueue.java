package com.cousera.algorithm.part1.week2;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class RandomizedQueue<Item> implements Iterable<Item> {

    private class DynamicArray<Item> implements Iterable<Item> {
        static final int MIN_LEN = 10;
        private int length;
        private Object[] data;
        private int count = 0;

        public DynamicArray(int len) {
            data = new Object[len];
            length = len;
        }

        public int size() {
            return count;
        }

        public void push(Item item) {
            if (item == null) throw new IllegalArgumentException();
            data[count++] = item;
            refreshArray();
        }

        public Item pop() {
            return removeItem(count - 1);
        }

        public Item removeRandom() {
            if (count == 0) throw new NoSuchElementException();
            return removeItem(StdRandom.uniformInt(count));
        }

        public Item removeItem(int i) {
            if (count == 0) throw new NoSuchElementException();
            Item item = (Item) data[i];
            data[i] = data[--count];
            refreshArray();
            return item;
        }

        public Item getRandom() {
            if (count == 0) throw new NoSuchElementException();
            return getItem(StdRandom.uniformInt(count));
        }


        public Item getItem(int i) {
            if (count == 0) throw new NoSuchElementException();
            Item it = (Item) data[i];
            return it;
        }


        public void refreshArray() {

            if (length == count) {
                length *= 2;
            } else if (count * 1.0 / length < 0.25 && length >= MIN_LEN) {
                length /= 2;
            }

            Object[] tmp = new Object[length];
            int t = 0;
            Object t1;
            for (int j = 0; j < count; j++) {
                t1 = data[j];
                if (t1 != null) {
                    tmp[t] = t1;
                    t++;
                }
            }
            data = tmp;

        }

        @Override
        public Iterator<Item> iterator() {
            return new it();
        }

        class it implements Iterator<Item> {
            private int[] init = new int[count];

            {
                for (int i = 0; i < count; i++) {
                    init[i] = i;
                }
                StdRandom.shuffle(init);
            }

            private int cnt = 0;

            @Override
            public boolean hasNext() {
                return cnt < count;
            }

            @Override
            public Item next() {
                if (cnt >= count) throw new NoSuchElementException();
                return (Item) data[init[cnt++]];
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        }
    }

    private final DynamicArray<Item> data;

    // construct an empty randomized queue
    public RandomizedQueue() {
        data = new DynamicArray<>(DynamicArray.MIN_LEN);

    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return data.size() == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return data.size();
    }

    // add the item
    public void enqueue(Item item) {
        data.push(item);
    }

    // remove and return a random item
    public Item dequeue() {
        return data.removeRandom();
    }

    // return a random item (but do not remove it)
    public Item sample() {
        return data.getRandom();
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return data.iterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> a = new RandomizedQueue<>();
        a.enqueue("a");
        a.enqueue("b");
        a.enqueue("c");
        a.enqueue("d");
        a.enqueue("e");
        a.enqueue("f");
        a.enqueue("g");
//
//        for (int i = 0; i < 7; i++) {
//            System.out.println(a.dequeue());
//        }
////        System.out.println(a.sample());
//
        for (String i : a) {
            System.out.println(i);
        }

    }

}