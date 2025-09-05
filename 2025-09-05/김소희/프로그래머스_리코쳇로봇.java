import java.util.*;
class Solution {
    static int[] dx = {-1,1,0,0};
    static int[] dy = {0,0,-1,1};
    static char[][] arr;
    static int X,Y;

    public int solution(String[] board) {
        X = board.length;       // 행
        Y = board[0].length();  // 열

        arr = new char[X][Y];
        
        int startX=0, startY=0;
        for(int i=0; i<X; i++){
            for(int j=0; j<Y; j++){
                arr[i][j]=board[i].charAt(j);
                if(arr[i][j]=='R'){ // 출발점 찾기
                    startX=j;
                    startY=i;
                }
            }
        }
        return bfs(startY,startX); // 행, 열 순서로 넘기기
    }

    static int bfs(int sy, int sx){
        boolean[][] visited= new boolean[X][Y];
        visited[sy][sx]=true;
        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{sy,sx,0});

        while(!q.isEmpty()){
            int[] pre = q.poll();
            int preY = pre[0];
            int preX = pre[1];
            int cnt = pre[2];

            if(arr[preY][preX]=='G') return cnt;

            for(int d=0; d<4; d++){
                int nowY = preY;
                int nowX = preX;

                // 미끄러지기
                while(true){
                    int nY = nowY + dx[d];
                    int nX = nowX + dy[d];
                    if(nY<0 || nY>=X || nX<0 || nX>=Y) break;
                    if(arr[nY][nX]=='D') break;
                    nowY = nY;
                    nowX = nX;
                }

                if(!visited[nowY][nowX]){
                    visited[nowY][nowX] = true;
                    q.offer(new int[]{nowY,nowX,cnt+1});
                }
            }
        }
        return -1;
    }
}
