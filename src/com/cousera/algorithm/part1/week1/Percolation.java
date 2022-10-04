import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // creates n-by-n grid, with all sites initially blocked
    private final boolean[][] isOpen;
    private final WeightedQuickUnionUF unionFind;
    private final int headPoint;  // default open
    private final int endPoint;   // default open
    private final int num;          // constand number of n
    private int openCount = 0;

    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        isOpen = new boolean[n][n];
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                isOpen[row][col] = false;
            }
        }
        unionFind = new WeightedQuickUnionUF(n * n + 2);
        headPoint = n * n;
        endPoint = n * n + 1;
        num = n;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (isOpen(row, col)) return;

        isOpen[row - 1][col - 1] = true;
        openCount++;
        int id = getId(row, col);

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
            union(id, headPoint);
        }

        // not in the last row.
        if (row < num) {
            tid = id + num;
            union(id, tid);
        } else {
            // connect to endPoint
            union(id, endPoint);
        }
    }


    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row <= 0 || col <= 0 || row > num || col > num) throw new IllegalArgumentException();
        return isOpen[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        int id = getId(row, col);
        return unionFind.find(headPoint) == unionFind.find(id);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openCount;
    }

    // does the system percolate?
    public boolean percolates() {
        return unionFind.find(headPoint) == unionFind.find(endPoint);
    }

    // test client (optional)
    public static void main(String[] args) {

    }

    private void union(int p, int q) {
        if (isOpen(p) && isOpen(q)) {
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

//class UF {
//    private final int[] id;
//    private final int[] sz;
//    private int count;
//
//    public UF(int n) {
//        id = new int[n];
//        sz = new int[n];
//        for (int i = 0; i < n; i++) {
//            id[i] = i;
//            sz[i] = 1;
//        }
//        this.count = n;
//    }
//
//    public boolean connected(int p, int q) {
//        return find(p) == find(q);
//    }
//
//    public int find(int p) {
//        while (id[p] != p) {
//            id[p] = id[id[p]];  // Path compression
//            p = id[p];
//        }
//        return p;
//    }
//
//    public void union(int p, int q) {
//        int grpa = find(p);
//        int grpb = find(q);
//        if (grpa == grpb) return;
//
//        if (sz[grpa] >= sz[grpb]) {
//            // union grpb to grpa
//            sz[grpa] += sz[grpb];
//            id[grpb] = grpa;
//        } else {
//            sz[grpb] += sz[grpa];
//            id[grpa] = grpb;
//        }
//        count--;
//    }
//
//    public int count() {
//        return this.count;
//    }
//}
