import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int N;
    static int[][] room;
    static int count = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        room = new int[N][N];
        for (int i=0; i<N; i++) {
            String[] input = br.readLine().split(" ");
            for (int j=0; j<N; j++) {
                room[i][j] = Integer.parseInt(input[j]);
            }
        }

        dfs(1, 0, 0);
        System.out.println(count);
    }

    // 0: 가로, 1: 세로, 2: 대각선
    public static void dfs(int x, int y, int dir) {
        if (x == N-1 && y == N-1) {
            count++;
            return;
        }

        if (dir == 0 || dir == 2) {
            int nx = x + 1, ny = y;
            if (nx < N && room[ny][nx] == 0) {
                dfs(nx, ny, 0);
            }
        }
        if (dir == 1 || dir == 2) {
            int ny = y + 1, nx = x;
            if (ny < N && room[ny][nx] ==0) {
                dfs(nx, ny, 1);
            }
        }
        if (dir == 0 || dir == 1 || dir ==2) {
            int nx = x + 1, ny = y + 1;
            if (nx < N && ny < N) {
                if (room[y][nx] == 0 && room[ny][x] == 0 && room[ny][nx] == 0) {
                    dfs(nx, ny, 2);
                }
            }
        }
    }
}