import java.util.*;

class Solution {
    int height, width;
    int[] dx = { 0, 0, -1, 1 };
    int[] dy = { 1, -1, 0, 0 };
    boolean[][] visited;
    public int[] solution(String[] maps) {
        height = maps.length;
        width = maps[0].length();
        visited = new boolean[height][width];
        
        int food = 0;
        List<Integer> land = new ArrayList<>();
        for (int i=0; i<height; i++) {
           	for (int j=0; j<width; j++) {
                if (maps[i].charAt(j) == 'X' || visited[i][j]) continue;
                food = bfs(i, j, maps);
                land.add(food);
            } 
        }
        
        if (land.isEmpty()) {
            return new int[] {-1};
        } 
        
        Collections.sort(land);
        int n = land.size();
        int[] answer = new int[n];
        for (int i=0; i<n; i++) {
            answer[i] = land.get(i); 
        }	

        return answer;
    }
    public int bfs(int y, int x, String[] maps) {
       	Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[] {x, y});
        visited[y][x] = true;
        
        int food = 0;
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int cx = curr[0];
            int cy = curr[1];
         
            food += maps[cy].charAt(cx) - '0';
            
            for (int i=0; i<4; i++) {
                int nx = cx + dx[i];
                int ny = cy + dy[i];
                
                if (nx >= 0 && nx < width && ny >=0 && ny < height) {
                    if (maps[ny].charAt(nx) != 'X' && !visited[ny][nx]) {
                        visited[ny][nx] = true;
                        queue.offer(new int[] {nx, ny});
                    }
                }
            }
        }
       	return food; 
    }
}