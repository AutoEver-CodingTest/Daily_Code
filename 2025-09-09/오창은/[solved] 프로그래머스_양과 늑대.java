import java.util.*;

class Solution {
    int answer = 0;
   
    public int solution(int[] info, int[][] edges) {
        int nodeSize = info.length;
        List<Integer>[] tree = new ArrayList[nodeSize];
        for (int i=0; i<nodeSize; i++) {
            tree[i] = new ArrayList<>();
        }
        
        for (int[] edge: edges) {
            tree[edge[0]].add(edge[1]);
        }
        
        List<Integer> nextNodes = new ArrayList<>();
        int start = 0;
        nextNodes.add(start);
        dfs(tree, info, start, 0, 0, nextNodes);
        
        return answer;
    }
    
    public void dfs(List<Integer>[] tree, int[] info, int curr, int sheep, int wolf, List<Integer> nextNodes) {
        if (info[curr] == 0) 
            sheep++;
        else 
            wolf++;
        
        if (wolf >= sheep) return; 
        
        answer = Math.max(answer, sheep);
        
        List<Integer> newNext = new ArrayList<>(nextNodes);
        newNext.remove(Integer.valueOf(curr));
        for (int child: tree[curr]) {
            newNext.add(child);
        }
        
        for (int next: newNext) {
            dfs(tree, info, next, sheep, wolf, newNext);
        }
    }
}