import java.io.*;
import java.util.*;

public class Main {
    static int N, M;
    static int[] inputs;
    // static Set<int[]> set;
    static boolean[] visited;
    static int[] selected;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] parts = br.readLine().split(" ");

        N = Integer.parseInt(parts[0]);
        M = Integer.parseInt(parts[1]);

        inputs = new int[N];
        // set = new HashSet<>();
        visited = new boolean[N + 1];
        selected = new int[M];
        String[] inputParts = br.readLine().split(" ");
        for (int i=0; i<N; i++) {
            inputs[i] = Integer.parseInt(inputParts[i]);
        }

        Arrays.sort(inputs);
        dfs(0);
    }
    public static void dfs(int depth) {
        if (depth == M) {
            for (int num: selected) {
                System.out.print(num +" ");
            }
            System.out.println();
            return;
        }
        
        // 같은 depth 내에서 중복 선택이 되지 않도록
        int prev = -1;
        for (int i=0; i<N; i++) {
            if (!visited[i] && prev != inputs[i]) {
                visited[i] = true;
                selected[depth] = inputs[i];
                prev = inputs[i];
                dfs(depth + 1);
                visited[i] = false;
            }
        }
    }
}