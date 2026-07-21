import java.io.*;
import java.util.*;

/**
 * Codeforces 2245A - Watchpig Pairs
 *
 * Piggy i (1-indexed) facing R needs (#L's strictly after i) >= k.
 * Piggy i facing L needs (#R's strictly before i) >= k.
 *
 * Key fact (checkable directly from the statement): a piggy at position i
 *   - CANNOT be L if (i-1) < k   (not enough positions before it)
 *   - CANNOT be R if (n-i) < k   (not enough positions after it)
 * If both fail for some i, the answer is -1. Otherwise some positions are
 * forced R or forced L, and the rest are free -- but even "free" positions
 * must respect the ACTUAL final counts, not just the "possible" bound, so a
 * simple greedy over forced positions is not enough (verified by hand and by
 * brute force against the sample where piggy 3 and 4 stay flexible).
 *
 * We fix the TOTAL number of R's in the final configuration, r (0..n), and
 * run a left-to-right DP with state = "R's placed so far" (prefix R count).
 * For position i:
 *   - choosing L requires (R's strictly before i) = prefix-before-i >= k
 *     (checkable immediately, causal)
 *   - choosing R requires (R's strictly after i) = r - (prefix through i) <= n-i-k
 *     i.e. prefix-through-i >= r-n+i+k (checkable immediately given r is fixed)
 * At the end, the prefix count must equal r exactly (consistency with the
 * chosen total). We take the minimum cost over all r.
 *
 * n <= 100, t <= 500, so this O(n^3) worst case DP (loop over r, then an
 * O(n * r) DP for each r) is fast enough in practice.
 *
 * Verified by hand and by brute force (exhaustive search over all 2^n
 * configurations) against the sample cases and many random small cases.
 */
public class WatchpigPairs {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int k = Integer.parseInt(st.nextToken());
            String s = br.readLine().trim();

            sb.append(solve(n, k, s)).append('\n');
        }
        System.out.print(sb);
    }

    private static final int INF = Integer.MAX_VALUE / 2;

    private static int solve(int n, int k, String s) {
        int best = INF;

        for (int r = 0; r <= n; r++) {
            int[] dp = new int[r + 1];
            Arrays.fill(dp, INF);
            dp[0] = 0;

            for (int i = 1; i <= n; i++) {
                int[] ndp = new int[r + 1];
                Arrays.fill(ndp, INF);

                int maxJPrev = Math.min(r, i - 1);
                char ch = s.charAt(i - 1);

                for (int jPrev = 0; jPrev <= maxJPrev; jPrev++) {
                    if (dp[jPrev] >= INF) continue;

                    // Option: this piggy becomes L
                    if (jPrev >= k) {
                        int cost = dp[jPrev] + (ch == 'L' ? 0 : 1);
                        if (cost < ndp[jPrev]) ndp[jPrev] = cost;
                    }

                    // Option: this piggy becomes R
                    int jNew = jPrev + 1;
                    if (jNew <= r) {
                        int threshold = r - n + i + k;
                        if (jNew >= threshold) {
                            int cost = dp[jPrev] + (ch == 'R' ? 0 : 1);
                            if (cost < ndp[jNew]) ndp[jNew] = cost;
                        }
                    }
                }
                dp = ndp;
            }

            if (dp[r] < best) best = dp[r];
        }

        return best >= INF ? -1 : best;
    }
}
