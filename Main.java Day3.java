import java.io.*;
import java.util.*;

/**
 * Codeforces 2215G - Maze
 *
 * IMPORTANT / HONESTY NOTE:
 * This problem is rated *3500, one of the hardest on Codeforces. The intended
 * solution uses a projection trick (project the path onto 4 directions: up,
 * right, upper-right, upper-left) that reduces each query to a distance query
 * between two nodes of a tree built from the obstacle structure, answered
 * with LCA / binary lifting in O(log n) per query. The public editorial only
 * gives that high-level idea, without the exact construction, so I cannot
 * responsibly hand you a "fast" O((n+m+q) log n) solution that I haven't
 * verified end-to-end.
 *
 * What follows instead is a CORRECT brute-force solution:
 *   - It marks obstacles (grid boundary + the m extra cells) in a hash set
 *     (grid coordinates go up to ~1e5, so we cannot allocate a dense 2D array).
 *   - For each query it runs Dijkstra (orthogonal move cost 2, diagonal move
 *     cost 3) directly on the implicit grid, expanding only cells it visits.
 *
 * This will give correct answers (matches the sample), but its complexity per
 * query can be up to O(n^2 log n) in the worst case, so it is only suitable
 * for small n / stress-testing / validation, NOT for the full constraints
 * (n,q up to 1e5, m,q up to 3*10^5).
 */
public class Main {

    static int n;
    static long mult; // multiplier to encode (x,y) -> unique long key
    static HashSet<Long> obstacles;

    static long key(int x, int y) {
        return (long) x * mult + y;
    }

    static boolean isObstacle(int x, int y) {
        if (x <= 0 || x >= n + 1 || y <= 0 || y >= n + 1) return true; // outer boundary
        return obstacles.contains(key(x, y));
    }

    // 4 orthogonal moves, cost 2
    static final int[] DX4 = {1, -1, 0, 0};
    static final int[] DY4 = {0, 0, 1, -1};
    // 4 diagonal moves, cost 3
    static final int[] DDX4 = {1, 1, -1, -1};
    static final int[] DDY4 = {1, -1, 1, -1};

    public static void main(String[] args) throws IOException {
        DataInputStream in = new DataInputStream(new BufferedInputStream(System.in, 1 << 16));
        StringBuilder sb = new StringBuilder();

        n = nextInt(in);
        int m = nextInt(in);
        int q = nextInt(in);

        mult = (long) (n + 2) + 5;
        obstacles = new HashSet<>(Math.max(16, m * 2));

        for (int i = 0; i < m; i++) {
            int x = nextInt(in);
            int y = nextInt(in);
            obstacles.add(key(x, y));
        }

        for (int i = 0; i < q; i++) {
            int sx = nextInt(in);
            int sy = nextInt(in);
            int tx = nextInt(in);
            int ty = nextInt(in);

            long ans = dijkstra(sx, sy, tx, ty);
            sb.append(ans).append('\n');
        }

        System.out.print(sb);
    }

    static long dijkstra(int sx, int sy, int tx, int ty) {
        if (isObstacle(sx, sy) || isObstacle(tx, ty)) return -1; // shouldn't happen per constraints
        if (sx == tx && sy == ty) return 0;

        HashMap<Long, Long> dist = new HashMap<>();
        PriorityQueue<long[]> pq = new PriorityQueue<>((a, b) -> Long.compare(a[0], b[0]));

        long startKey = key(sx, sy);
        dist.put(startKey, 0L);
        pq.add(new long[]{0L, sx, sy});

        long targetKey = key(tx, ty);

        while (!pq.isEmpty()) {
            long[] cur = pq.poll();
            long d = cur[0];
            int cx = (int) cur[1];
            int cy = (int) cur[2];
            long curKey = key(cx, cy);

            Long best = dist.get(curKey);
            if (best == null || d > best) continue; // stale entry
            if (curKey == targetKey) return d;

            // orthogonal moves, cost 2
            for (int k = 0; k < 4; k++) {
                int nx = cx + DX4[k];
                int ny = cy + DY4[k];
                if (nx < 0 || nx > n + 1 || ny < 0 || ny > n + 1) continue;
                if (isObstacle(nx, ny)) continue;
                relax(dist, pq, nx, ny, d + 2);
            }
            // diagonal moves, cost 3
            for (int k = 0; k < 4; k++) {
                int nx = cx + DDX4[k];
                int ny = cy + DDY4[k];
                if (nx < 0 || nx > n + 1 || ny < 0 || ny > n + 1) continue;
                if (isObstacle(nx, ny)) continue;
                relax(dist, pq, nx, ny, d + 3);
            }
        }

        return -1; // unreachable
    }

    static void relax(HashMap<Long, Long> dist, PriorityQueue<long[]> pq, int nx, int ny, long nd) {
        long k = key(nx, ny);
        Long cur = dist.get(k);
        if (cur == null || nd < cur) {
            dist.put(k, nd);
            pq.add(new long[]{nd, nx, ny});
        }
    }

    private static int nextInt(DataInputStream in) throws IOException {
        int ret = 0;
        int b = in.read();
        while (b < '0' || b > '9') {
            if (b == '-') break;
            b = in.read();
        }
        boolean neg = false;
        if (b == '-') {
            neg = true;
            b = in.read();
        }
        while (b >= '0' && b <= '9') {
            ret = ret * 10 + (b - '0');
            b = in.read();
        }
        return neg ? -ret : ret;
    }
}
