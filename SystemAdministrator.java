import java.util.*;
import java.io.*;

public class SystemAdministrator {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int v = Integer.parseInt(st.nextToken());

        StringBuilder sb = new StringBuilder();

        if (m < n - 1) {
            System.out.println(-1);
            return;
        }

        // Collect all nodes except v
        int[] others = new int[n - 1];
        int idx = 0;
        for (int i = 1; i <= n; i++) {
            if (i != v) others[idx++] = i;
        }

        // Split into two branches: branch1 has 1 node, branch2 has the rest (n-2 nodes)
        int s1 = 1;
        int s2 = n - 2; // could be 0 only if n==2, but n>=3 so s2>=1

        int g1 = others[0];
        // nodes2 = [v, others[1], others[2], ..., others[n-2]]
        int len2 = s2 + 1;
        int[] nodes2 = new int[len2];
        nodes2[0] = v;
        for (int i = 0; i < s2; i++) nodes2[i + 1] = others[1 + i];

        // Max edges possible = C(s1+1,2) + C(s2+1,2)
        long maxEdges = (long) (s1 + 1) * s1 / 2 + (long) (s2 + 1) * s2 / 2;
        if (m > maxEdges) {
            System.out.println(-1);
            return;
        }

        List<int[]> edges = new ArrayList<>();

        // chain for branch1: v - g1
        edges.add(new int[]{v, g1});

        // chain for branch2: v - nodes2[1] - nodes2[2] - ... 
        for (int i = 0; i < len2 - 1; i++) {
            edges.add(new int[]{nodes2[i], nodes2[i + 1]});
        }

        int used = edges.size(); // = n - 1
        int extraNeeded = m - used;

        // Fill extra edges only within nodes2 clique (skip already-used consecutive pairs)
        outer:
        for (int i = 0; i < len2 && extraNeeded > 0; i++) {
            for (int j = i + 2; j < len2 && extraNeeded > 0; j++) {
                edges.add(new int[]{nodes2[i], nodes2[j]});
                extraNeeded--;
            }
        }

        for (int[] e : edges) {
            sb.append(e[0]).append(' ').append(e[1]).append('\n');
        }

        System.out.print(sb);
    }
}
