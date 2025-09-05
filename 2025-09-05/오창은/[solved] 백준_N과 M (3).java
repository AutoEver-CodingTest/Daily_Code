import java.io.*;

public class Main {
    static int N, M;
    static int[] selected;
    static StringBuilder sb = new StringBuilder();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] parts = br.readLine().split(" ");
        N = Integer.parseInt(parts[0]);
        M = Integer.parseInt(parts[1]);
        
        selected = new int[M];
        dfs(0);
        System.out.println(sb.toString());
    }
    
    public static void dfs(int depth) {
        if (depth == M) {
            for (int num: selected) {
                sb.append(num + " ");
            }
            sb.append("\n");
            return;
        }
        
        for (int i=1; i<=N; i++) {
            selected[depth] = i;
            dfs(depth + 1);
        }
    }
}