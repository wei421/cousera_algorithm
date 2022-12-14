import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // creates n-by-n grid, with all sites initially blocked
    private static final byte OPEN = Byte.parseByte("1", 2);
    private static final byte CLOSE = Byte.parseByte("0", 2);
    private static final byte TOPCONNECTED = Byte.parseByte("10", 2);
    private static final byte BOTTOMCONNECTED = Byte.parseByte("100", 2); // 100
    private static final byte PERCOLATED = Byte.parseByte("111", 2);      // 111
    private static final byte ISFULL = Byte.parseByte("11", 2);


    private final byte[] status;
    private final WeightedQuickUnionUF unionFind;
    private final int num;          // constand number of n
    private int openCount = 0;
    private boolean isPercolated = false;

    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        status = new byte[n * n];
        for (int i = 0; i < n * n; i++) {
            status[i] = CLOSE;
        }
        unionFind = new WeightedQuickUnionUF(n * n);
        num = n;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (isOpen(row, col)) return;
        int id = getId(row, col);
        status[id] = (byte) (status[id] | OPEN);
        openCount++;


        int tid;
        // not in the first col.
        if (col > 1) {
            tid = id - 1;
            union(id, tid);
        }

        // not in the last col.
        if (col < num) {
            tid = id + 1;
            union(id, tid);
        }

        // not in the first row.
        if (row > 1) {
            tid = id - num;
            union(id, tid);
        } else {
            // connect to headPoint
            status[id] = (byte) (status[id] | TOPCONNECTED);
        }

        // not in the last row.
        if (row < num) {
            tid = id + num;
            union(id, tid);
        } else {
            // connect to endPoint
            status[id] = (byte) (status[id] | BOTTOMCONNECTED);
        }

        int proot = unionFind.find(id);
        status[proot] = (byte) (status[proot] | status[id]);

        if (status[proot] == PERCOLATED) {
            isPercolated = true;
        }
    }


    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row <= 0 || col <= 0 || row > num || col > num) throw new IllegalArgumentException();
        return (status[getId(row, col)] & OPEN) == OPEN;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row <= 0 || col <= 0 || row > num || col > num) throw new IllegalArgumentException();
        int pid = unionFind.find(getId(row, col));
        byte stat = status[pid];
        return (stat & ISFULL) == ISFULL;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openCount;
    }

    // does the system percolate?
    public boolean percolates() {
        return isPercolated;
    }

    // test client (optional)
    public static void main(String[] args) {

    }

    private void union(int p, int q) {
        if (isOpen(q)) {
            int qroot = unionFind.find(q);
            status[p] = (byte) (status[p] | status[qroot]);
            unionFind.union(p, q);
        }
    }

    private int getId(int row, int col) {
        return col + num * (row - 1) - 1;
    }

    private boolean isOpen(int id) {
        if (id >= num * num) return true; // heading & ending open default

        int row = id / num + 1;
        int col = id - (row - 1) * num + 1;
        return isOpen(row, col);
    }
}

