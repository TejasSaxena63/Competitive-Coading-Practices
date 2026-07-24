import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.HashMap;

public class Yamakasi {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line = br.readLine();
        if (line == null || line.trim().isEmpty()) return;
        int t = Integer.parseInt(line.trim());
        
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            long s = Long.parseLong(st.nextToken());
            long x = Long.parseLong(st.nextToken());
            
            int[] a = new int[n];
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) {
                a[i] = Integer.parseInt(st.nextToken());
            }
            
            long count = 0;
            long currentSum = 0;
            int currentMax = Integer.MIN_VALUE;
            
            HashMap<Long, Integer> prefixSumCount = new HashMap<>();
            prefixSumCount.put(0L, 1);
            
            int l = 0;
            for (int r = 0; r < n; r++) {
                currentSum += a[r];
                // Maintain constraints using sliding window or prefix frequency map based on conditions
                // ...
            }
            
            sb.append(count).append("\n");
        }
        System.out.print(sb);
    }
}