import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;


public class RandomWord {
    public static void main(String[] args) {
        String tmp;
        int i = 1;
        String finalString = "";
        while (!StdIn.isEmpty()) {
            tmp = StdIn.readString();
            if (StdRandom.bernoulli(1.0 / i)) {
                finalString = tmp;

            }
            i++;

        }
        StdOut.println(finalString);

    }

}
