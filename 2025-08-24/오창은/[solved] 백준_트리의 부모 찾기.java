import java.io.*;
import java.util.*;

public class Main {
    static List<List<Integer>> graph;
    static boolean[] visited;
    static int[] parent;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        graph = new ArrayList<>();

        // 1~N
        for (int i=0; i<=N; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i=0; i<N-1; i++) {
            String[] parts = br.readLine().split(" ");
            int u = Integer.parseInt(parts[0]);
            int v = Integer.parseInt(parts[1]);

            graph.get(u).add(v);
            graph.get(v).add(u);
        }

        parent = new int[N + 1];
        visited = new boolean[N + 1];
        bfs(1);

        for (int i=2; i<=N; i++) {
            System.out.println(parent[i]);
        }
    }

    static public void bfs(int start) {
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);
        visited[start] = true;

        while (!queue.isEmpty()) {
            int parentNode = queue.poll();

            for (int child: graph.get(parentNode)) {
                if (!visited[child]) {
                    visited[child] = true;
                    queue.offer(child);
                    parent[child] = parentNode;
                }
            }
        }
    }
}