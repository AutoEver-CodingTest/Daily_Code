import java.io.*;

public class Main {
    static int N, M;
    static int[] selected;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] parts = br.readLine().split(" ");

        N = Integer.parseInt(parts[0]);
        M = Integer.parseInt(parts[1]);

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
            dfs(i, depth + 1);
        }
    }
}