import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int R, C;
    static char[][] graph;
    static boolean[] visited;
    static int[] dx = { 0, 0, -1, 1 };
    static int[] dy = { 1, -1, 0, 0 };
    static int maxDist = 0;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] parts = br.readLine().split(" ");

        R = Integer.parseInt(parts[0]);
        C = Integer.parseInt(parts[1]);
        graph = new char[R][C];
        visited = new boolean[26];
        for (int i=0; i<R; i++) {
            String row = br.readLine();
            for (int j=0; j<C; j++) {
                graph[i][j] = row.charAt(j);
            }
        }

        visited[graph[0][0] - 'A'] = true;
        dfs(0, 0, 1);

        System.out.println(maxDist);
    }

    public static void dfs(int x, int y, int depth) {
        maxDist = Math.max(maxDist, depth);

        for (int i=0; i<4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];

            if (nx < 0 || ny < 0 || nx >= C || ny >= R) continue;

            int index = graph[ny][nx] - 'A';
            if (!visited[index]) {
                visited[index] = true;
                dfs(nx, ny, depth + 1);
                visited[index] = false;
            }
        }
    }
}