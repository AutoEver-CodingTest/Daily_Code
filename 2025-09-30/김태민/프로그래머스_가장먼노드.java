import java.util.*;
class Solution {
    List<ArrayList<Integer>> list = new ArrayList<>();
    boolean [] visit;
    int answer = 0;
    int max = 0;
    public int solution(int n, int[][] edge) {
        
        visit = new boolean[n+1];
        
        for (int i = 0;i <= n;i++) {
            list.add(new ArrayList<>());
        }
        
        
        for (int[] e : edge) {
            list.get(e[0]).add(e[1]);
            list.get(e[1]).add(e[0]);
        }
        bfs(1,n);
        
        return answer;
    }
    
    public void bfs(int start,int n) {
        visit[start] = true;
        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{start, 0});
        while(!q.isEmpty()) {
            int [] cur = q.poll();
            if (max < cur[1]) {
                max = cur[1];
                answer = 1;
            } else if (max == cur[1]) {
                answer++;
            }
            
            for (int nxt : list.get(cur[0])) {
                if (!visit[nxt]) {
                    visit[nxt] = true;
                    q.offer(new int[]{nxt, cur[1] + 1});
                }
            }
            
          
        }
    }
}
