import java.io.*;
import java.util.*;

public class Main {
    static int N, M;
    static int[] arr;
    static int[] selected;
    static StringBuilder sb = new StringBuilder();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] parts = br.readLine().split(" ");
        N = Integer.parseInt(parts[0]);
        M = Integer.parseInt(parts[1]);
        
        arr = new int[N];
        selected = new int[M];

        parts = br.readLine().split(" ");
        for (int i=0; i<parts.length; i++) {
            arr[i] = Integer.parseInt(parts[i]);
        }
        
        Arrays.sort(arr);
        dfs(0, 0);
        System.out.println(sb.toString());
    }
    
    public static void dfs(int start, int depth) {
        if (depth == M) {
            for (int num: selected) {
                sb.append(num + " ");
            }
            sb.append("\n");
            return;
        }
        
        for (int i=start; i<N; i++) {
            selected[depth] = arr[i];
            dfs(i + 1, depth + 1); 
        }
    }
}