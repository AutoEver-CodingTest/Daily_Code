import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.*;

public class Main {
    static class Node {
        int to, weight;
        public Node(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }
    static int n;
    static List<Node>[] tree;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());

        tree = new ArrayList[n + 1];
        for (int i=1; i<=n; i++) {
            tree[i] = new ArrayList<>();
        }
        for (int i=0; i<n-1; i++) {
            String[] parts = br.readLine().split(" ");
            int u = Integer.parseInt(parts[0]);
            int v = Integer.parseInt(parts[1]);
            int weight = Integer.parseInt(parts[2]);

            tree[u].add(new Node(v, weight));
            tree[v].add(new Node(u, weight));
        }

        int[] result1 = bfs(1);
        int farNode = result1[0];

        int[] result2 = bfs(farNode);
        int answer = result2[1];

        System.out.println(answer);

        /*
        for (int i=1; i<=n; i++) {
            for (Node next: tree[i]) {
                System.out.print(next.to + " ");
            }
            System.out.println();
        }
         */
    }
    public static int[] bfs(int start) {
        boolean[] visited = new boolean[n + 1];
        int[] distance = new int[n + 1];
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);
        visited[start] = true;

        int maxDistance = 0;
        int farNode = start;
        while (!queue.isEmpty()) {
            int curr = queue.poll();

            for (Node next: tree[curr]) {
                if (!visited[next.to]) {
                    visited[next.to] = true;
                    distance[next.to] = distance[curr] + next.weight;
                    queue.offer(next.to);

                    if (distance[next.to] > maxDistance) {
                        maxDistance = distance[next.to];
                        farNode = next.to;
                    }
                }
            }
        }

        return new int[] {farNode, maxDistance};
    }
}