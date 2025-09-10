import java.util.*;
class Solution {
    
    static List<Integer>[] list; 
    static boolean[] visit;
    static int answer = 0;
    
    public int solution(int[] info, int[][] edges) {

        
        int n = info.length;
        list = new ArrayList[n];
        
        for (int i = 0; i < n;i++) {
            list[i] = new ArrayList<>();
        }
        
        for (int[] e : edges) {
            list[e[0]].add(e[1]);
        }
        
        visit = new boolean[n];
        List<Integer> avail = new ArrayList<>(list[0]);
        
        dfs(1,0,avail,info);
        
        
        return answer;
    }
    
    public void dfs(int sheep, int wolf, List<Integer> avail, int[] info) {
        answer = Math.max(answer,sheep);
        
        for (int i = 0; i < avail.size();i++) {
            int node = avail.get(i);
            int ns = sheep + (info[node] == 0 ? 1 : 0);
            int nw = wolf + (info[node] == 1 ? 1 : 0);
            
            if (nw >= ns) {
                continue;
            }
            
            List<Integer> next = new ArrayList<>(avail);
            next.remove(i);
            next.addAll(list[node]);
            dfs(ns,nw,next,info);
        }
        
    }
}
