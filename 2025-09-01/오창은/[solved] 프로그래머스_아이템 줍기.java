import java.util.;

class Solution {
    int[][] map = new int[102][102];
    boolean[][] visited = new boolean[102][102];
    int[] dx = {0, 0, -1, 1};
    int[] dy = {1, -1, 0, 0};
    
    public int solution(int[][] rectangle, int characterX, int characterY, int itemX, int itemY) {
        for (int[] r rectangle) {
            int x1 = r[0]  2;
            int y1 = r[1]  2;
            int x2 = r[2]  2;
            int y2 = r[3]  2;
            
            for (int x=x1; x=x2; x++) {
                map[y1][x] = 1;
                map[y2][x] = 1;
            }
            
            for (int y=y1; y=y2; y++) {
                map[y][x1] = 1;
                map[y][x2] = 1;
            }
        }
        
        for (int[] r rectangle) {
            int x1 = r[0]  2;
            int y1 = r[1]  2;
            int x2 = r[2]  2;
            int y2 = r[3]  2;
           
            for (int x = x1+1; x  x2; x++) {
                for (int y = y1+1; y  y2; y++) {
            		map[y][x] = 0;        
                }    
            }
        }
        
        int result = bfs(characterX  2, characterY  2, itemX  2, itemY  2);
        result = 2;
        
        return result;
    }
    
    public int bfs(int startX, int startY, int targetX, int targetY) {
        Queueint[] queue = new LinkedList();
        queue.offer(new int[] {startX, startY, 0});
        visited[startY][startX] = true; 
        
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int cx = curr[0], cy = curr[1], distance = curr[2];
            
            if (cx == targetX && cy == targetY) {
                return distance; 
            }
            for (int i=0; i4; i++) {
                int nx = cx + dx[i];
                int ny = cy + dy[i];
                
                if (nx = 0 && nx  102 && ny = 0 && ny  102) {
                    if (!visited[ny][nx] && map[ny][nx] == 1) {
                        visited[ny][nx] = true;
                        queue.offer(new int[] {nx, ny, distance + 1});
                    }
                }
            }
        }
        return -1;
    }
}