import java.util.*;
class Solution {
    static boolean [] visit;
    static int result = -1;
    static int min = 0;
    static int count = 0;
    public int solution(int k, int[][] dungeons) {
        int answer = -1;
        visit = new boolean[dungeons.length];
        
        dfs(k, dungeons,0);
        return result;
    }
    
    public static void dfs( int k,int[][] dungeons,int count) {
        
        result = Math.max(result,count);
        for (int i = 0 ; i < dungeons.length ; i++) {
            if (!visit[i] && k >= dungeons[i][0]) {
                visit[i] = true;
                dfs(k - dungeons[i][1],dungeons,count + 1);
                visit[i] = false;
            } 
        }
    }
}
