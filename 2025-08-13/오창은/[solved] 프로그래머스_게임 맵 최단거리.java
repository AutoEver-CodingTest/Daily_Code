import java.util.*;

class Solution {
   
    int n, m;
    int[][] distance;
 	
    int[] dx = { 0, 0, -1, 1 };	 
 	int[] dy = { 1, -1, 0, 0 };	 
    
    public void bfs(int[][] maps) {
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[] {0, 0});
       	distance[0][0] = 1;
        
        while (!queue.isEmpty()) {
    		int[] curr = queue.poll();
            int cx = curr[1];
            int cy = curr[0];
        	
            // System.out.println(cx + " " + cy);
            
            for (int i=0; i<4; i++) {
                int nx = cx + dx[i];
                int ny = cy + dy[i];
            	// System.out.println(nx + " " + ny);
                if (nx >= 0 && nx < m && ny >= 0 && ny < n) {
            		// System.out.println(nx + " " + ny);
                    if (distance[ny][nx] == -1 && maps[ny][nx] == 1) { // 방문하지 않았을 때 && 길이 뚫려 있을 때
                        distance[ny][nx] = distance[cy][cx] + 1;
                        queue.offer(new int[] {ny, nx});
                    }
                }
            }
        }
    }
    
    public int solution(int[][] maps) {
        int answer = 0;
      
        n = maps.length;
        m = maps[0].length;
        // System.out.println(n + " " + m);

        distance = new int[n][m];
        for (int i=0; i<n; i++) {
           	Arrays.fill(distance[i], -1);
        }
        
        bfs(maps);
        
        /*
        for (int i=0; i<n; i++) {
            for (int j=0; j<m; j++) {
        		System.out.print(distance[i][j] + " ");
            }
            System.out.println();
        }
        */
        return distance[n-1][m-1];
    }
}