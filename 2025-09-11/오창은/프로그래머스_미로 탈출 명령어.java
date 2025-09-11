class Solution {
    char[] moves = {'d', 'l', 'r', 'u'};
    int[] dx = { 0, -1, 1, 0 };
    int[] dy = { 1, 0, 0, -1 };
    char[] result;
    int width, height;
    int targetY, targetX;
    int moveNum;
    public String solution(int n, int m, int x, int y, int r, int c, int k) {
        result = new char[k];
     
        height = n;
        width = m; 
        targetY = r-1;
        targetX = c-1;
        moveNum = k;
        
        int dist = Math.abs(r - x) + Math.abs(c - y);
        if (k < dist || (k-dist) % 2 != 0) return "impossible";
        
        StringBuilder sb = new StringBuilder();
        if (dfs(0, y-1, x-1)) {
            for (char ch: result) {
                sb.append(ch);    
            }
        	return sb.toString();
        } else {
            return "impossible";
        }
    }
    
    private boolean dfs(int depth, int x, int y) {
        int dist = Math.abs(targetX - x) + Math.abs(targetY - y);
        if (dist > moveNum - depth) return false;
        
        if (depth == moveNum && x == targetX && y == targetY) {
            return true; 
        }
        for (int i=0; i<4; i++) {
           	int nx = x + dx[i]; 
           	int ny = y + dy[i]; 
            
            if (nx < 0 || nx >= width || ny < 0 || ny >= height) continue; 
            result[depth] = moves[i];
            if (dfs(depth + 1, nx, ny)) {
                return true;
            }
        }
        
        return false; 
    }
}
