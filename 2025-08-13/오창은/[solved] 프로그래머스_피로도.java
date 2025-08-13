import java.util.*;

class Solution {
    int n;
    int max = 0;
    boolean[] visited;
    
    public int solution(int k, int[][] dungeons) {
        int answer = -1;
   
        n = dungeons.length;
        visited = new boolean[n];
       
        dfs(k, dungeons, 0);
        
        return max;
    }
    public void dfs(int myStress, int[][] dungeons, int count) {
        max = Math.max(max, count);
        
        for (int i=0; i<dungeons.length; i++) {
            int need = dungeons[i][0];
            int cost = dungeons[i][1];
            if (!visited[i] && myStress >= need) { // 방문하지 않았고, 최소 필요 피로도보다 클 때
                visited[i] = true;
                dfs(myStress - cost, dungeons, count + 1);
                visited[i] = false;
            }
        }
    }
}