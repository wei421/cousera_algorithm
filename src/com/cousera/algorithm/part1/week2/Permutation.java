package com.cousera.algorithm.part1.week2;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
    public static void main(String[] args) {
        String tmp;
        int i = Integer.parseInt(args[0]);
        RandomizedQueue<String> q = new RandomizedQueue<>();
        int j = 0;
        while (!StdIn.isEmpty()) {
            tmp = StdIn.readString();
            if (j < i) {
                q.enqueue(tmp);

            } else {
                int pos = StdRandom.uniformInt(j + 1);
                if (pos < i) {
                    q.dequeue();
                    q.enqueue(tmp);
                }
            }
            j++;
        }

        for (int k = 0; k < i; k++) {
            StdOut.println(q.dequeue());
        }

    }
}
