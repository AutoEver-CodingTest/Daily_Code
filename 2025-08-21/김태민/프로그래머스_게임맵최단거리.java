import java.util.*;

class Solution {
    static boolean[][] visit;
    static int n,m;
    public int solution(int[][] maps) {
        int answer = 0;
        n = maps.length;
        m = maps[0].length;
        visit = new boolean[maps.length][maps[0].length];
        
        return bfs(maps);
    }
    
    public int bfs(int[][] maps) {
        Queue<int[]> queue = new LinkedList<>();
        int [] dx = {1,-1,0,0};
        int [] dy = {0,0,1,-1};
        queue.offer(new int[]{0,0,1});
        visit[0][0] = true;
        int count = 1;
        while(!queue.isEmpty()) {
            
            int [] a = queue.poll();
            if (a[0] == n - 1 && a[1] == m - 1) {
                return a[2];
            }
            for (int i = 0 ; i < 4 ; i++) {
                int cx = a[0] + dx[i];
                int cy = a[1] + dy[i];
                if (cx >= 0 && cy >= 0 && cx < n && cy < m && maps[cx][cy] == 1 
                    && !visit[cx][cy]) {
                    visit[cx][cy] = true;
                    queue.offer(new int[]{cx,cy,a[2] + 1});
                    count++;
                }
            }
        }
        return -1;
    }
}
