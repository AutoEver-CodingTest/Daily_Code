import java.util.*;
class Solution {
    static int[] dx = {-1,1,0,0};
    static int[] dy = {0,0,-1,1};
    static char[][] arr;
    static int n,m;

    public int solution(String[] board) {
        m = board.length;       // 행
        n = board[0].length();  // 열
        
        arr = new char[m][n];
        
        int startX=0, startY=0;
        for(int i=0; i<m; i++){
            for(int j=0; j<n; j++ ){
                arr[i][j]=board[i].charAt(j);
                if(arr[i][j]=='R'){
                    startX=j; startY=i;
                }
            }
        }
        return bfs(startY, startX, arr);

    }
    static int bfs(int y, int x, char[][] arr){
        boolean[][] visited = new boolean[m][n];
        visited[y][x] = true;
        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{y,x,0});
        
        while(!q.isEmpty()){
            int[]pre = q.poll();
            int py= pre[0], px=pre[1], cnt=pre[2];
            
            if (arr[py][px] == 'G') return cnt;;
            
            for(int i=0; i<4; i++){
                int nowX = px, nowY=py;
                while(true){
                    int nextX = nowX +dx[i];
                    int nextY = nowY +dy[i];
                    
                    if (nextX < 0 || nextX >= n || nextY < 0 || nextY >= m) break;
                    if (arr[nextY][nextX] == 'D') break;
                    nowX = nextX; nowY= nextY;
                }
                if(!visited[nowY][nowX]){
                    visited[nowY][nowX]=true;
                    q.offer(new int[]{nowY,nowX,cnt+1});
                }
            }
        }
        return -1;
    }
}
