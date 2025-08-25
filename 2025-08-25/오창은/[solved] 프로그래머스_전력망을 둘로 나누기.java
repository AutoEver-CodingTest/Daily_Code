import java.util.*;

class Solution {
    List<List<Integer>> graph = new ArrayList<>();
	boolean[] visited;
    public int solution(int n, int[][] wires) {
        int answer = -1;
       
        for (int i=0; i<=n; i++) {
            graph.add(new ArrayList<>());
        }
        
        // 줄 한개씩 끊고, 
        // 방문하지 않은 점이 있다면 한번 더 bfs 돌려서 
        // 방문한 개수의 차를 min 값에 넣는다???
        for (int[] wire: wires) {
            int u = wire[0];
            int v = wire[1];
      		
            graph.get(u).add(v); 
            graph.get(v).add(u); 
        }
       
        int min = n;
        for (int[] wire: wires) {
            int u = wire[0];
            int v = wire[1];
            
            graph.get(u).remove(Integer.valueOf(v)); // 간선 제거 
            graph.get(v).remove(Integer.valueOf(u));
            
        	visited = new boolean[n+1];
        	int num = bfs(u);
        	// System.out.print(num + " ");
            // 간선의 차이 
            // num, n-1-num
            int diff = Math.abs(n - num*2);
           	min = Math.min(min, diff); 
            
            graph.get(u).add(v); // 끊고 다시 연결 
            graph.get(v).add(u);
        }
        
        return min;
    }
    
    // 간선 개수 세기 
    public int bfs(int start) {
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);
        visited[start] = true;
        int nodeCount = 0;
        
        while (!queue.isEmpty()) {
            int curr = queue.poll();
           	nodeCount++; 
            // System.out.print(curr + " ");
            
            for (int next: graph.get(curr)) {
               	if (!visited[next]) {
                    visited[next] = true;
                    queue.offer(next);
                } 
            }
        }
        return nodeCount;
    }
}