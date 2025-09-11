import java.util.*;
import java.io.*;
public class Main {
    static int[][] tetris;
    static boolean[][] visited;
    static int[] dx = {-1,1,0,0};
    static int[] dy = {0,0,-1,1};
    static int result;
    static int N,M;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine()," ");
        N = Integer.parseInt(st.nextToken());//행
        M = Integer.parseInt(st.nextToken());//열
        
        tetris = new int[N][M];
        visited = new boolean[N][M];
        for(int i=0; i<N; i++){
            st = new StringTokenizer(br.readLine()," ");
            for(int j=0; j<M; j++){
                tetris[i][j]=Integer.parseInt(st.nextToken());
            }   
        }
        
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                visited[i][j] = true;
                dfs(i, j, 1, tetris[i][j]); // DFS로 4칸 합
                visited[i][j] = false;

                checkExtraShape(i, j); //예외 처리
            }
        }
        System.out.println(result);
        
    }
    static void dfs(int a, int b, int depth, int sum){//행열
        if(depth==4){
            result = Math.max(result, sum);
            return;
        }  
        for(int i=0; i<4; i++){
            int nx = b+ dx[i];
            int ny = a+dy[i];

            if(nx>=M ||ny>=N|| nx<0||ny<0) continue;
            if(visited[ny][nx]) continue;
            visited[ny][nx]=true;
            dfs(ny, nx, depth+1, sum+tetris[ny][nx]);
            visited[ny][nx]=false;
        }
    }
    static void checkExtraShape(int y,int x){
        for (int i = 0; i < 4; i++) {
            int temp = tetris[y][x];
            int cnt =0;
            for (int j = 0; j < 4; j++) {
                if(i==j) continue;
                int ny = y + dy[j];
                int nx = x + dx[j];
                if (ny < 0 || nx < 0 || ny >= N || nx >= M){
                    temp=0;
                    break;  
                }
                temp += tetris[ny][nx];
                cnt++;  
            }
            if(cnt ==3) result = Math.max(result, temp);
        }
    }
}
