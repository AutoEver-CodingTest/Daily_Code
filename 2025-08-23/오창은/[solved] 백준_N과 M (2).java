import java.io.*;

public class Main {
    static int N, M;
    // static boolean[] visited;
    static int[] selected;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] parts = br.readLine().split(" ");

        N = Integer.parseInt(parts[0]);
        M = Integer.parseInt(parts[1]);

        // visited = new boolean[N + 1];
        selected = new int[M];
        dfs(1, 0);
    }
    public static void dfs(int curr, int depth) {
        if (depth == M) {
            for (int num: selected) {
                System.out.print(num + " ");
            }
            System.out.println();
            return;
        }

        for (int i=curr; i<=N; i++) {
            selected[depth] = i;
            dfs(i + 1, depth + 1);

            // 항상 i보다 큰 숫자만 탐색하므로 visited가 필요가 없다
            /*
            if (!visited[i]) {
                visited[i] = true;
                selected[depth] = i;
                dfs(i + 1, depth + 1);
                visited[i] = false;
            }
             */
        }
    }
}