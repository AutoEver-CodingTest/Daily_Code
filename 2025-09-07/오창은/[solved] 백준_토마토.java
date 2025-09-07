import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int M;
    static int N;
    static int[][] box;

    static int[] dx = { 0, 0, -1, 1 };
    static int[] dy = { 1, -1, 0, 0 };

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] parts = br.readLine().split(" ");

        M = Integer.parseInt(parts[0]);
        N = Integer.parseInt(parts[1]);

        box = new int[N][M];
        for (int i=0; i<N; i++) {
            parts = br.readLine().split(" ");
            for (int j=0; j<M; j++) {
                box[i][j] = Integer.parseInt(parts[j]);
            }
        }

        boolean isAllRipened = true;
        for (int i=0; i<N; i++) {
            for (int j=0; j<M; j++) {
                if (box[i][j] == 0)
                   isAllRipened = false;
            }
        }

        if (isAllRipened) {
            System.out.println(0);
            return;
        }

        bfs();

        int max = 0;
        for (int i=0; i<N; i++) {
            for (int j=0; j<M; j++) {
                if (box[i][j] == 0) {
                    System.out.println(-1);
                    return;
                }
                max = Math.max(max, box[i][j]);
            }
        }
        System.out.println(max - 1);
    }

    public static void bfs() {
        Queue<int[]> queue = new LinkedList<>();
        for (int i=0; i<N; i++) {
            for (int j=0; j<M; j++) {
                if (box[i][j] == 1) {
                    queue.offer(new int[] {i, j});
                }
            }
        }

        while(!queue.isEmpty()) {
            int[] curr = queue.poll();
            int cy = curr[0];
            int cx = curr[1];

            for (int i=0; i<4; i++) {
                int ny = cy + dy[i];
                int nx = cx + dx[i];

                if (ny < 0 || nx < 0 || ny >= N || nx >= M) continue;
                if (box[ny][nx] == 0) {
                    box[ny][nx] = box[cy][cx] + 1;
                    queue.offer(new int[] {ny, nx});
                }
            }
        }
    }
}