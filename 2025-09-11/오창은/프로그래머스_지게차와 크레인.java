import java.util.*;

class Solution {
	int height;
   	int width; 
    char[][] arr;
   
	int[] dx = { 0, 0, -1, 1 };
	int[] dy = { 1, -1, 0, 0 };
    public int solution(String[] storage, String[] requests) {
        height = storage.length;
        width = storage[0].length(); 
        
        arr = new char[height][width];
        for (int i=0; i<height; i++) {
            arr[i] = storage[i].toCharArray();
        }
        
        for (String request: requests) {
            int strLength = request.length();
            char target = request.charAt(0);
            
            List<int[]> list = new ArrayList<>();
            if (strLength == 1) { // 지게차 
                for (int i=0; i<height; i++) {
                    for (int j=0; j<width; j++) {
                        if (arr[i][j] == target && canGoOutSide(i, j)) {
                           	list.add(new int[] {i, j} ); 
                            // arr[i][j] = '.';
                        }
                    }
                }
                
                for (int i=0; i<list.size(); i++) {
                   	int y = list.get(i)[0];
                   	int x = list.get(i)[1];
                    arr[y][x] = '.';
                }
            } else if (strLength == 2) { // 크레인 
                for (int i=0; i<height; i++) {
                    for (int j=0; j<width; j++) {
                       	if (arr[i][j] == target) {
                            arr[i][j] = '.';
                        } 
                    }
                }
            }
        }
        int answer = 0;
        
        for (int i=0; i<height; i++) {
            for (int j=0; j<width; j++) {
                if (arr[i][j] != '.') {
                    answer++;
                }
                // System.out.print(arr[i][j] + " ");
            }
            // System.out.println();
        }
        
        return answer;
    }
    private boolean canGoOutSide(int y, int x) {
        boolean[][] visited = new boolean[height][width]; 
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[] {x, y});
       	visited[y][x] = true;
        
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int cx = curr[0];
            int cy = curr[1];
           
            for (int i=0; i<4; i++) {
                int nx = cx + dx[i];
                int ny = cy + dy[i];
              
                if (nx < 0 || nx >= width || ny < 0 || ny >= height) {
                    return true;
                }
                if (arr[ny][nx] == '.') {
                    if (!visited[ny][nx]) {
                        visited[ny][nx] = true;	
                        queue.offer(new int[] {nx, ny}); 
                    }
                }
            }
        }
        return false;
    }
}
