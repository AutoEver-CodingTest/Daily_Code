import java.util.*;
import java.io.*;

public class Main {
    static int N, M;
    static int[] selected;
    static boolean[] visited;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] parts = br.readLine().split(" ");

        N = Integer.parseInt(parts[0]);
        M = Integer.parseInt(parts[1]);

        visited = new boolean[N+1];
        selected = new int[M];
        dfs(0);
    }

    public static void dfs(int depth) {
        if (depth == M) {
            for (int num: selected) {
                System.out.print(num + " ");
            }
            System.out.println();
            return;
        }

        for (int i=1; i<=N; i++) {
            if (!visited[i]) {
                visited[i] = true;
                selected[depth] = i;
                dfs(depth + 1);
                visited[i] = false;
            }
        }
    }
}