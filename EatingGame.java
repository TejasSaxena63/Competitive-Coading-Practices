import java.util.*;
import java.io.*;
import java.util.StringTokenizer;

public class EatingGame {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine().trim());
            StringTokenizer st = new StringTokenizer(br.readLine());
            int[] a = new int[n];
            int max = 0;
            for (int i = 0; i < n; i++) {
                a[i] = Integer.parseInt(st.nextToken());
                if (a[i] > max) max = a[i];
            }

            int count = 0;
            for (int i = 0; i < n; i++) {
                if (a[i] == max) count++;
            }

            sb.append(count).append('\n');
        }

        System.out.print(sb);
    }
}
