import java.util.*;
import java.io.*;
public class Main {
    static int N;
    static char[][] RGB;
    static boolean[][] visited;
    static boolean[][] visitedrg;
    static int[] dx = {-1,1,0,0};
    static int[] dy = {0,0,-1,1};
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        RGB = new char[N][N];
        visited= new boolean[N][N];
        visitedrg= new boolean[N][N];
        for(int i=0; i<N; i++){
            String tmp = br.readLine();
            for(int j=0; j<N; j++){
                RGB[i][j]= tmp.charAt(j);
            }
        }
        int np=0; int p=0;
        for(int i=0; i<N; i++){
            for(int j=0; j<N; j++){
                if(!visited[i][j]) {
                    np++;
                    bfs(i,j);
                }
                if(!visitedrg[i][j]){
                    p++;
                    bfsRG(i,j);
                }
            }
        }
        System.out.println(np+" "+p);

    }
    static void bfs(int a, int b){
        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{a,b});
        visited[a][b]=true;

        while(!q.isEmpty()){
            int p[] = q.poll();
            int py = p[0];
            int px = p[1];

            char str = RGB[py][px];

            for(int i=0; i<4; i++){
                int nx = px +dx[i];
                int ny = py +dy[i];

                if(nx>=N ||ny>= N||nx<0||ny<0)continue;
                if(visited[ny][nx]) continue;
                if(RGB[ny][nx]!= str) continue;
                q.offer(new int[]{ny,nx});
                visited[ny][nx]=true;

            }
        }
    }
    static void bfsRG(int a, int b){
        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{a,b});
        visitedrg[a][b]=true;

        char str = RGB[a][b];
        while(!q.isEmpty()){
            int p[] = q.poll();
            int py = p[0];
            int px = p[1];

            for(int i=0; i<4; i++){
                int nx = px +dx[i];
                int ny = py +dy[i];

                if(nx>=N ||ny>= N||nx<0||ny<0)continue;
                if(visitedrg[ny][nx]) continue;
                if(str == 'B'){
                    if(RGB[ny][nx] != 'B') continue;
                } else {
                    if(RGB[ny][nx] == 'B') continue;
                }
                q.offer(new int[]{ny,nx});
                visitedrg[ny][nx]=true;
            }
        }
    }
}
