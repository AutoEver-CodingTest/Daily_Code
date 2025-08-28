import java.util.*;
class Solution {
    public int solution(int x, int y, int n) {
        int answer = 0;
        return bfs(x,y,n);
    }
    
    public int bfs(int x,int y, int n) {
        Queue<int[]> q = new LinkedList<>();
        boolean [] visit = new boolean[y + 1];
        q.offer(new int[] {x, 0});
        while(!q.isEmpty()) {
            int [] a = q.poll();
            if (a[0] == y) {
                return a[1];
            }
            for (int i = 0; i < 3;i++) {
                int na = 0;
                if (i == 0) {
                    na = a[0] + n;
                } else if (i == 1) {
                    na = a[0] * 2;
                } else if (i == 2) {
                    na = a[0] * 3;
                }
                if (na <= y && !visit[na]) {
                    q.offer(new int[] {na,a[1] + 1});
                    visit[na] = true;
                }
            }
        }
        return -1;
    }
}
