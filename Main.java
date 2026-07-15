import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            long n = Long.parseLong(st.nextToken());
            long k = Long.parseLong(st.nextToken());

            long ans;
            if (k >= n) {
                ans = n;
            } else {
                ans = k;                 // each of the k numbers starts as "1"
                long remaining = n - k;  // leftover budget to spend on upgrades
                int level = 1;

                while (remaining > 0) {
                    long costPerNumber = 1L << level;      // 2^level
                    long fullRoundCost = k * costPerNumber;

                    if (fullRoundCost <= remaining) {
                        remaining -= fullRoundCost;
                        ans += k;
                        level++;
                    } else {
                        long howMany = remaining / costPerNumber;
                        ans += howMany;
                        break;
                    }
                }
            }

            sb.append(ans).append('\n');
        }

        System.out.print(sb);
    }
}
