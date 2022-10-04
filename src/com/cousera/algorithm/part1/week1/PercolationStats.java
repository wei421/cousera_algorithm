import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

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
            threshold[t] = percolation.numberOfOpenSites() * 1.0 / (n * n);
        }
//        StdOut.println("mean                    = " + mean());
//        StdOut.println("stddev                  = " + stddev());
//        StdOut.println("95% confidence interval = [" + confidenceLo() + ", " + confidenceHi()  + "]");

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
        return mean() - COEF * stddev() / Math.sqrt(T);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + COEF * stddev() / Math.sqrt(T);
    }

    // test client (see below)
    public static void main(String[] args) {
        PercolationStats ps = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        StdOut.println("95% confidence interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
    }


}
