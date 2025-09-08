import java.util.*;
import java.io.*;
public class Main {
    
    static int [][] array;
    static int [][] array4;
    static boolean [][] visit2;
    static int n,m,h;
    static int min = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        StringTokenizer st;
        array = new int[n][n];
        array4 = new int[n][n];

        for (int i = 0; i < n;i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                array4[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        int type = 1;
        visit2 = new boolean[n][n];
        for (int i = 0;i < n;i++) {
            for (int j = 0; j < n;j++) {
                if (!visit2[i][j] && array4[i][j] == 1) {
                    dfs13(i,j,type);
                    type++;
                }
            }
        }


        for (int i = 1;i <= type;i++) {
            visit2 = new boolean[n][n];
            bfs15(i);
        }
        System.out.println(min);
    }

    public static void dfs13(int x,int y,int type) {
        visit2[x][y] = true;
        array[x][y] = type;
        int [] dx = { 1, -1 ,0 ,0};
        int [] dy = {0, 0, 1, -1};
        for (int i = 0; i < 4;i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];
            if (nx >= 0 && ny >= 0 && nx < n && ny < n &&
                !visit2[nx][ny] && array4[nx][ny] == 1) {
                array[nx][ny] = type;
                dfs13(nx,ny,type);
            }
        }
    }

    public static void bfs15(int type) {
        int [] dx = { 1, -1 ,0 ,0};
        int [] dy = {0, 0, 1, -1};
        Queue<int[]> q = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (array[i][j] == type) {
                    q.offer(new int[]{i,j, 0});
                    visit2[i][j] = true;
                }
            }
        }
        while(!q.isEmpty()) {
            int [] cur = q.poll();
            for (int i = 0;i < 4;i++) {
                int nx = cur[0] + dx[i];
                int ny = cur[1] + dy[i];

                if (nx < 0 || ny < 0 || nx >= n || ny >= n) continue;

                if (visit2[nx][ny]) continue;
                if (array[nx][ny] == 0) {
                    visit2[nx][ny] = true;
                    q.offer(new int[]{nx,ny,cur[2] + 1});
                    continue;
                }

                if (array[nx][ny] != type) {
                    if (cur[2] < min) {
                        min = cur[2];
                        break;
                    }
                }


            }
        }
    }}
