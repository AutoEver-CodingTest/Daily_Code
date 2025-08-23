import java.io.*;
import java.util.*;

public class Main {
    static int N, M;
    static int[] inputs;
    static boolean[] visited;
    static int[] result;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] parts = br.readLine().split(" ");

        N = Integer.parseInt(parts[0]);
        M = Integer.parseInt(parts[1]);

        inputs = new int[N];
        visited = new boolean[N];
        result = new int[M];
        String[] inputParts = br.readLine().split(" ");
        for (int i=0; i<N; i++) {
            inputs[i] = Integer.parseInt(inputParts[i]);
        }

        Arrays.sort(inputs);
        dfs(0, 0);
    }
    public static void dfs(int curr, int depth) {
        if (depth == M) {
            for (int num: result) {
                System.out.print(num + " ");
            }
            System.out.println();
            return;
        }

        for (int i=0; i<N; i++) {
            if (!visited[i]) {
                visited[i] = true;
                result[depth] = inputs[i];
                dfs(i, depth + 1);
                visited[i] = false;
            }
        }
    }
}