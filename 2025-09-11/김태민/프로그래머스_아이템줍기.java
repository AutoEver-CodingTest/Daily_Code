import java.util.*;
class Solution {
    
    static int[][] board;
    
    public int solution(int[][] rectangle, int characterX, int characterY, int itemX, int itemY) {
        int answer = 0;
        board = new int[102][102];
        for (int[] r : rectangle) {
            int x1 = r[0] * 2;
            int y1 = r[1] * 2;
            int x2 = r[2] * 2;
            int y2 = r[3] * 2;
            
            for (int i = x1; i <= x2;i++) {
                for (int j = y1; j <= y2;j++) {
                    board[i][j] = 1;
                }
            }
            
            
        }
        
        for (int[] r : rectangle) {
            int x1 = r[0] * 2;
            int y1 = r[1] * 2;
            int x2 = r[2] * 2;
            int y2 = r[3] * 2;
            
            for (int i = x1 + 1; i < x2;i++) {
                for (int j = y1 + 1; j < y2;j++) {
                    board[i][j] = 0;
                }
            }
            
            
        }
        
        
        return bfs(characterX*2,characterY*2,itemX*2,itemY*2);
    }
    
    public int bfs(int sx,int sy, int ex,int ey) {
        boolean [][] visit = new boolean[102][102];
        int [] dx = {1,-1,0,0};
        int [] dy = {0,0,1,-1};
        
        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{sx,sy,0});
        visit[sx][sy] = true;
        
        while(!q.isEmpty()) {
            int [] cur = q.poll();
            
            if (cur[0] == ex && cur[1] == ey) {
                return cur[2] / 2;
            }
            
            for (int i = 0; i < 4;i++) {
                
                int nx = cur[0] + dx[i];
                int ny = cur[1] + dy[i];
                if (nx < 0 || ny < 0 || nx > 100 || ny > 100) continue;
                if (visit[nx][ny] || board[nx][ny] != 1) continue;
                q.offer(new int[]{nx,ny, cur[2] + 1});
                visit[nx][ny] = true;
            }
        }
        return -1;
        
        
    }
    
    
    
}
