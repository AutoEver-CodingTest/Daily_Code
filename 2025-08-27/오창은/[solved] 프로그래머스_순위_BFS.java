import java.util.*;

class Solution {
    List<List<Integer>> winGraph = new ArrayList<>();
    List<List<Integer>> loseGraph = new ArrayList<>();
    
    public int solution(int n, int[][] results) {
        for (int i=0; i<=n; i++) {
            winGraph.add(new ArrayList<>());
            loseGraph.add(new ArrayList<>());
        }

        for (int[] result: results) {
            int win = result[0];
            int lose = result[1];
            
            winGraph.get(win).add(lose);
            loseGraph.get(lose).add(win);
        }

        int answer = 0;
        for (int i=1; i<=n; i++) {
            int winCount = bfs(i, winGraph, n);
            int loseCount = bfs(i, loseGraph, n);
            
            if (winCount + loseCount == n-1) {
                answer++;
            }
        }

        return answer;
    }
    public int bfs(int start, List<List<Integer>> graph, int n) {
        boolean[] visited = new boolean[n+1];
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);
		visited[start] = true;
       
        int count = 0;
        while (!queue.isEmpty()) {
            int curr = queue.poll();
            for (int next: graph.get(curr)) {
                if (!visited[next]) {
                    visited[next] = true;
                    queue.offer(next);
                    count++;
                }
            }



        }
        return count;

    }
}