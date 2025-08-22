import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] parts = br.readLine().split(" ");

        int N = Integer.parseInt(parts[0]);
        int K = Integer.parseInt(parts[1]);

        boolean[] visited = new boolean[100_001];
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(N);
        visited[N] = true;

        int level = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i=0; i<size; i++) {
                int curr = queue.poll();

                if (curr == K) {
                    System.out.println(level);
                    return;
                }

                int[] nextNum = { curr + 1, curr - 1, curr * 2 };
                for (int next: nextNum) {
                    if (0 <= next && next <= 100_000) {
                        if (!visited[next]) {
                            visited[next] = true;
                            queue.offer(next);
                        }
                    }
                }
            }
            level++;
            //System.out.println(queue);
        }
        // System.out.println(level);
        //System.out.println(N + " " + K);
    }
}