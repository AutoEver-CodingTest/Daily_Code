import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] parts = br.readLine().split(" ");

        int N = Integer.parseInt(parts[0]);
        int K = Integer.parseInt(parts[1]);

        int MAX = 100001;
        int[] dist = new int[MAX];
        Arrays.fill(dist, -1);

        Deque<Integer> dq = new ArrayDeque<>();
        dq.add(N);
        dist[N] = 0;

        while (!dq.isEmpty()) {
            int x = dq.poll();
            if (x == K) {
                System.out.println(dist[K]);
                break;
            }

            int teleport = 2 * x;
            if (teleport < MAX && dist[teleport] == -1) {
                dist[teleport] = dist[x];
                dq.addFirst(teleport);
            }

            int[] walk = { x-1, x+1 };
            for (int nx: walk) {
                if (nx >= 0 && nx < MAX && dist[nx] == -1) {
                    dist[nx] = dist[x] + 1;
                    dq.addLast(nx);
                }
            }
        }
    }
}