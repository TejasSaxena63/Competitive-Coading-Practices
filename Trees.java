import java.util.*;
import java.io.*;

public class Trees {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = Integer.parseInt(st.nextToken());

        HashMap<Long, Integer> freq = new HashMap<>();
        int maxFreq = 0;

        for (int i = 0; i < n; i++) {
            int d = Math.min(i, n - 1 - i); // distance from nearer end (0-indexed)
            long c = (long) a[i] - d;       // required base value if this tree stays unchanged
            if (c < 1) continue;            // base must be a positive integer
            int f = freq.merge(c, 1, Integer::sum);
            if (f > maxFreq) maxFreq = f;
        }

        System.out.println(n - maxFreq);
    }
}
