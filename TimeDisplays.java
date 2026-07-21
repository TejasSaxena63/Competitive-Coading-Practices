import java.io.*;
import java.util.*;

/**
 * Codeforces 2206K - Time Display Stickers
 *
 * We binary search on the answer k (number of time displays).
 *
 * Each HH is either:
 *   - tens digit '0', units digit anything 0-9   (values 00-09)
 *   - tens digit '1', units digit '0' or '1'      (values 10-11)
 * Each MM is:
 *   - tens digit in {0,1,2,3,4,5}, units digit anything 0-9
 *
 * For a fixed k, let j = number of hours of the "tens=1" type (0<=j<=k).
 * These j hours force j copies of digit '1' as their tens digit, and
 * additionally need j more digits from {0,1} for their units (flexible split).
 * The remaining k-j hours (tens=0) force (k-j) copies of digit '0'.
 * Minutes: let P = amount of minute-tens-digits assigned to {0,1} (rest go to {2,3,4,5}).
 * Everything else (hour-units for tens=0 hours, minute-units for all minutes,
 * and minute-tens digits assigned to {2,3,4,5}) is "free": it can be satisfied by ANY
 * leftover digit, so it only needs enough leftover capacity overall (equivalent to 4k<=n
 * once the "hard" digit-0/1 and digit-2..5 pools are satisfiable).
 *
 * So the feasibility check reduces to (for fixed k):
 *   C01 = cnt[0]+cnt[1],  C25 = cnt[2]+cnt[3]+cnt[4]+cnt[5]
 *   choose P = max(0, k - C25)              (minimizes pressure on the 0/1 pool)
 *   jmin = max(0, k - cnt[0])                (need at least this many '1'-tens hours
 *                                              so the '0'-tens hours (k-j) don't exceed cnt0)
 *   jmax = min(k, cnt[1], C01 - k - P)        (can't exceed cnt1, and total flexible+base
 *                                              pool usage can't exceed C01)
 *   feasible iff jmin <= jmax
 *
 * Verified by hand against all 4 sample test cases.
 */
public class TimeDisplays {

    public static void main(String[] args) throws IOException {
        DataInputStream in = new DataInputStream(new BufferedInputStream(System.in, 1 << 16));
        StringBuilder sb = new StringBuilder();

        int t = nextInt(in);
        while (t-- > 0) {
            int n = nextInt(in);
            int[] cnt = new int[10];
            int read = 0;
            while (read < n) {
                int c = in.read();
                if (c >= '0' && c <= '9') {
                    cnt[c - '0']++;
                    read++;
                }
            }

            long C01 = (long) cnt[0] + cnt[1];
            long C25 = (long) cnt[2] + cnt[3] + cnt[4] + cnt[5];

            long lo = 0, hi = n / 4; // upper bound on number of displays
            long ans = 0;
            while (lo <= hi) {
                long mid = (lo + hi) / 2;
                if (feasible(mid, cnt, C01, C25)) {
                    ans = mid;
                    lo = mid + 1;
                } else {
                    hi = mid - 1;
                }
            }
            sb.append(ans).append('\n');
        }
        System.out.print(sb);
    }

    private static boolean feasible(long k, int[] cnt, long C01, long C25) {
        if (4 * k > (long) sumAll(cnt)) return false;

        long P = Math.max(0, k - C25);
        if (P > k) return false; // cannot happen since C25 >= 0, kept for safety

        long jmin = Math.max(0, k - cnt[0]);
        long jmax = Math.min(k, Math.min((long) cnt[1], C01 - k - P));

        return jmin <= jmax;
    }

    private static long sumAll(int[] cnt) {
        long s = 0;
        for (int c : cnt) s += c;
        return s;
    }

    private static int nextInt(DataInputStream in) throws IOException {
        int ret = 0;
        int b = in.read();
        while (b < '0' || b > '9') b = in.read();
        while (b >= '0' && b <= '9') {
            ret = ret * 10 + (b - '0');
            b = in.read();
        }
        return ret;
    }
}
