import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

import java.awt.*;

public class PercolationStats {

    private static final double COEF = 1.96;
    private double[] threshold;
    private int trailNum;


    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Paras invalid.");
        }
        threshold = new double[trials];
        trailNum = trials;
        Percolation percolation;
        for (int t = 0; t < trials; t++) {

            percolation = new Percolation(n);
            int row;
            int col;
            while (!percolation.percolates()) {
                col = StdRandom.uniformInt(n) + 1;
                row = StdRandom.uniformInt(n) + 1;
                percolation.open(row, col);
            }
            draw(percolation, n);
            threshold[t] = percolation.numberOfOpenSites() * 1.0 / (n * n);

        }
//        StdOut.println("mean                    = " + mean());
//        StdOut.println("stddev                  = " + stddev());
//        StdOut.println("95% confidence interval = [" + confidenceLo() + ", " + confidenceHi()  + "]");

    }

    public static void draw(Percolation perc, int N) {
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setXscale(-.05 * N, 1.05 * N);
        StdDraw.setYscale(-.05 * N, 1.05 * N);   // leave a border to write text
        StdDraw.filledSquare(N / 2.0, N / 2.0, N / 2.0);

        // draw N-by-N grid
        int opened = 0;
        for (int row = 1; row <= N; row++) {
            for (int col = 1; col <= N; col++) {
                if (perc.isFull(row, col)) {
                    StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
                    opened++;
                } else if (perc.isOpen(row, col)) {
                    StdDraw.setPenColor(StdDraw.WHITE);
                    opened++;
                } else
                    StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.filledSquare(col - 0.5, N - row + 0.5, 0.45);
            }
        }

        // write status text
        StdDraw.setFont(new Font("SansSerif", Font.PLAIN, 12));
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.text(.25 * N, -N * .025, opened + " open sites");
        if (perc.percolates()) StdDraw.text(.75 * N, -N * .025, "percolates");
        else StdDraw.text(.75 * N, -N * .025, "does not percolate");

        StdDraw.pause(2000);

    }


    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(threshold);
    }


    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(threshold);
    }


    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - COEF * stddev() / Math.sqrt(trailNum);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + COEF * stddev() / Math.sqrt(trailNum);
    }

    // test client (see below)
    public static void main(String[] args) {
        PercolationStats ps = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        StdOut.println("95% confidence interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
    }


}
