import java.util.*;
import java.io.*;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            long a = Long.parseLong(st.nextToken());
            long b = Long.parseLong(st.nextToken());

            int layers = 0;
            long size = 1;
            long white = a, dark = b;

            while (true) {
                boolean useWhite = (layers % 2 == 0);
                if (useWhite) {
                    if (white >= size) {
                        white -= size;
                        layers++;
                        size *= 2;
                    } else break;
                } else {
                    if (dark >= size) {
                        dark -= size;
                        layers++;
                        size *= 2;
                    } else break;
                }
            }

            sb.append(layers).append("\n");
        }

        System.out.print(sb);
    }
}
