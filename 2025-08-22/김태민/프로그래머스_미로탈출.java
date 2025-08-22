import java.util.*;
class Solution {
    static boolean lever = false;
    static int n,m;
    static int [] s;
    static int [] e;
    static int [] l;
    
    public int solution(String[] maps) {
        int answer = 0;
        s = new int[2];
        e = new int[2];
        l = new int[2];
        n = maps.length;
        m = maps[0].length();
        
        
        
        for (int i = 0; i < maps.length;i++) {
            
            for (int j = 0; j < maps[i].length();j++)  {
                if (maps[i].charAt(j) == 'S') {
                    s[0] = i;
                    s[1] = j;
                } else if (maps[i].charAt(j) == 'E') {
                    e[0] = i;
                    e[1] = j;
                } else if (maps[i].charAt(j) == 'L') {
                    l[0] = i;
                    l[1] = j;
                }
            }          
        }
        int a = bfs(maps,s,l);
        if (a == -1) return -1;
        int b = bfs(maps,l,e);
        if (b == -1) return -1;
        
        return a + b;
    }
    
    public int bfs(String[] maps,int [] start, int [] end) {
        boolean [][] visit = new boolean[n][m];
        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{start[0],start[1],0});
        visit[start[0]][start[1]] = true;
        int[] dx = {1, -1,0,0};
        int[] dy = {0,0,1,-1};
        while (!q.isEmpty()) {
            
            int[] a = q.poll();
            if (a[0] == end[0] && a[1] == end[1]) {
                return a[2];
            }
            for (int i = 0;i < 4;i++) {
                int cx = a[0] + dx[i];
                int cy = a[1] + dy[i];
                if (cx >= 0 && cy >= 0 && cx < n && cy < m && 
                    maps[cx].charAt(cy) != 'X' && !visit[cx][cy]) {
                    visit[cx][cy] = true;
                    q.offer(new int[]{cx,cy,a[2] + 1});
                }
            }
        }
        return  -1;
    }
    
    
}
