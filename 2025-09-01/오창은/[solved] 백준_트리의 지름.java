import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int N;

    static class Node {
        int to, weight;
        public Node (int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    static List<Node>[] tree;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        tree = new ArrayList[N+1]; // 1~N
        for (int i=1; i<=N; i++) {
            tree[i] = new ArrayList<>();
        }

        for (int i=0; i<N; i++) {
            String[] parts = br.readLine().split(" ");
            int from = Integer.parseInt(parts[0]);
            for (int j=1; j<parts.length; j+=2) {
                int num = Integer.parseInt(parts[j]);
                if (num == -1) break;
                int weight = Integer.parseInt(parts[j+1]);

                tree[from].add(new Node(num, weight));
            }
        }

        // System.out.print(tree[4].get(2).weight + " ");
        int[] result1 = bfs(1);
        int farNode = result1[0];

        int[] result2 = bfs(farNode);
        int maxDistance = result2[1];
        System.out.println(maxDistance);
    }

    public static int[] bfs(int start) {
        boolean[] visited = new boolean[N+1];
        int[] distance = new int[N+1];
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);
        visited[start] = true;

        int target = start;
        int maxDistance = 0;
        while (!queue.isEmpty()) {
            int curr = queue.poll();

            for (Node next: tree[curr]) {
                if (!visited[next.to]) {
                    visited[next.to] = true;
                    queue.offer(next.to);
                    distance[next.to] = distance[curr] + next.weight;
                }

                if (distance[next.to] > maxDistance) {
                    maxDistance = distance[next.to];
                    target = next.to;
                }
            }
        }
        return new int[] {target, maxDistance};
    }
}