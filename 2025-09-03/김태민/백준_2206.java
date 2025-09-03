import java.util.*;
import java.io.*;
public class Main {
    
    static int [][] array;
    static boolean [][][] visit4;
    static int n,m;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        array = new int[n][m];
        visit4 = new boolean[n][m][2];

        for (int i = 0; i < n;i++) {
            String str = br.readLine();
            for (int j = 0;j < m;j++) {
                array[i][j] = str.charAt(j) - '0';
            }
        }

        System.out.println(bfs2206());

    }

    public static int bfs2206() {
        Queue<int[]> q = new LinkedList<>();
        int [] dx = {1,-1,0,0};
        int [] dy = {0,0,1,-1};
        q.offer(new int[]{0,0,1,0});
        while(!q.isEmpty()) {
            int [] cur = q.poll();
            if (cur[0] == n - 1  && cur[1] == m - 1) {
                return cur[2];
            }
            for (int i = 0; i < 4;i++) {
                int nx = cur[0] + dx[i];
                int ny = cur[1] + dy[i];
                if (nx >= 0 && ny >= 0 && nx < n && ny < m && !visit4[nx][ny][cur[3]]) {
                    if (array[nx][ny] == 1 && cur[3] == 0) {
                        visit4[nx][ny][1] = true;
                        q.offer(new int[]{nx,ny,cur[2] + 1, 1});
                    } else if(array[nx][ny] == 1 && cur[3] == 1) {
                        continue;
                    } else {
                        visit4[nx][ny][cur[3]] = true;
                        q.offer(new int[]{nx,ny,cur[2] + 1, cur[3]});
                    }

                }
            }
        }
        return -1;

    }
}
