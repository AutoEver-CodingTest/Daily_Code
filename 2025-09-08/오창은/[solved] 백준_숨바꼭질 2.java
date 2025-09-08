import java.util.*;
import java.lang.*;
import java.io.*;

class Main {
    static final int MAX = 100_000;
    static int N, K;
    static int[] dist; 
    static int[] ways;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        String[] parts = br.readLine().split(" ");
        N = Integer.parseInt(parts[0]);
        K = Integer.parseInt(parts[1]);

        dist = new int[MAX + 1];
        ways = new int[MAX + 1];

        bfs();

        System.out.println(dist[K]);
        System.out.println(ways[K]);
    }

    public static void bfs() {
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(N);

        Arrays.fill(dist, -1);
        dist[N] = 0;
        ways[N] = 1;

        while (!queue.isEmpty()) {
            int curr = queue.poll();

            int[] nextArr = { curr -1, curr + 1, curr * 2 };
            for (int next: nextArr) {
                if (next < 0 || next > MAX) continue; 

                // 1. 첫 방문시  
                if (dist[next] == -1) { 
                    dist[next] = dist[curr] + 1; 
                    ways[next] = ways[curr];
                    queue.offer(next);
                } else { // 2. 재방문시 
                    if (dist[next] == dist[curr] + 1)
                        ways[next] += ways[curr];
                }
            }
        }
    }
}