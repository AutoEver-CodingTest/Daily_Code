import java.util.*;
class Solution {
    static int n,m;
   
    public int solution(String[] board) {
        int answer = 0;
        n = board.length;
        m = board[0].length();
        int [] start = new int[2];
        int [] end = new int[2];
        for (int i = 0;i < n; i++) {
            for (int j = 0;j < m;j++) {
                char c = board[i].charAt(j);
                if (c == 'R') {
                    start[0] = i;
                    start[1] = j;
                }
                if (c == 'G') {
                    end[0] = i;
                    end[1] = j;
                }
            }
        }
        
        return bfs(start,end,board);
    }
    
    public int bfs(int [] start,int [] end, String[] board) {
        int [] dx = {1,-1,0,0};
        int [] dy = {0,0,1,-1};
        
        boolean[][] visit = new boolean[n][m];
        Queue<int[]> q = new LinkedList<>(); 
        q.offer(new int[] { start[0], start[1], 0} );
        
        while(!q.isEmpty()) {
            int [] cur = q.poll();
            
            if (cur[0] == end[0] && cur[1] == end[1]) {
                return cur[2];
            }
            
            for (int i = 0; i < 4;i++) {
                int nx = cur[0];
                int ny = cur[1];
                while(true) {
                    int tx = nx + dx[i];   
                    int ty = ny + dy[i];
                    if (tx >= 0 && ty >= 0 && tx < n && ty < m &&
                       board[tx].charAt(ty) != 'D') {
                        nx = tx;
                        ny = ty;
                    } else {
                        break;
                    }
                    
                }
                if (nx == cur[0] && ny == cur[1]) {
                    continue;
                }
                if (!visit[nx][ny]) {
                    visit[nx][ny] = true;
                    q.offer(new int[] {nx,ny, cur[2]+1});
                }
            }
        }
        return -1;
    }
}
