import java.util.*;

class Solution {
    int[] dx = {0, 0, -1, 1};
    int[] dy = {1, -1, 0, 0};
    
    int[] start;
    int[] lever;
    int[] end;
    
    int n,m;
    char[][] map;
   
    public int solution(String[] maps) {
        int answer = 0;
       
        n = maps.length;
        m = maps[0].length();
        map = new char[n][m];
        for (int i=0; i<n; i++) {
            char[] row = maps[i].toCharArray();
            for (int j=0; j<m; j++) {
            	map[i][j] = row[j];
                
                if (row[j] == 'S') {
                    start = new int[] {i, j};
                } else if (row[j] == 'L') {
                    lever = new int[] {i, j};
                } else if (row[j] == 'E') {
                    end = new int[] {i, j};
                }
                
            }
        }
       
        int toLever = bfs(start, lever);
        if (toLever == -1) return -1;
        
        int toEnd = bfs(lever, end);
        if (toEnd == -1) return -1;
        
        // System.out.println(start[0] + " " + start[1]);
        // System.out.println(lever[0] + " " + lever[1]);
        
        return toLever + toEnd;
    }
   
    public int bfs(int[] start, int[] end) {
    	int[][] distance = new int[n][m];
      	for (int i=0; i<n; i++) {
            Arrays.fill(distance[i], -1);
        } 
        
        Queue<int[]> queue = new LinkedList<>();
        int startY = start[0];
        int startX = start[1];
       	queue.offer(new int[] {startY, startX});
      	distance[startY][startX] = 0;
        
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
         	int cx = curr[1];
         	int cy = curr[0];
           
            if (cx == end[1] && cy == end[0]) {
                return distance[cy][cx];
            }
            
            for (int i=0; i<4; i++) {
            	int ny = cy + dy[i];
            	int nx = cx + dx[i];
                
                if (nx >= 0 && nx < m && ny >=0 && ny < n) {
                    if (map[ny][nx] != 'X' && distance[ny][nx] == -1) {
                       	distance[ny][nx] = distance[cy][cx] + 1;
                        queue.offer(new int[]{ny, nx});
                    }
                }
            }
        }
        return -1;
    }
}