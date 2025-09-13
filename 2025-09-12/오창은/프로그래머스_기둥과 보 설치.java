import java.util.*;

class Solution {
    int n;
    boolean[][] pillars;
    boolean[][] beams;
    public int[][] solution(int n_, int[][] build_frame) {
       	n = n_; 
      
        pillars = new boolean[n+1][n+1];
        beams = new boolean[n+1][n+1];
       
        for (int[] cmd: build_frame) {
            int x = cmd[0];
            int y = cmd[1];
            int type = cmd[2]; // 0: 기둥, 1: 보
            int op = cmd[3]; // 0: 삭제, 1: 설치 
            
            if (op == 1) { // 설치 
                if (canBuild(x, y, type)) {
                    if (type == 0) pillars[x][y] = true; 
                    else beams[x][y] = true;
                } 
            } else { // 삭제 
                // 일단 삭제 
                if (type == 0) pillars[x][y] = false;
                else beams[x][y] = false;
                
                if (!isValid()) {
                    if (type == 0) pillars[x][y] = true;
                    else beams[x][y] = true;
                }
            }
        }
        
        List<int[]> list = new ArrayList<>();
        for (int i=0; i<=n; i++) {
            for (int j=0; j<=n; j++) {
            	if (pillars[i][j]) list.add(new int[] {i, j, 0});   
            	if (beams[i][j]) list.add(new int[] {i, j, 1});   
            }
        }
        
        list.sort((a,b) -> {
            if (a[0] != b[0]) return a[0] - b[0];
            if (a[1] != b[1]) return a[1] - b[1];
            return a[2] - b[2];
        });
        
        return list.toArray(new int[list.size()][]);
    }
    
    private boolean canBuild(int x, int y, int type) {
        if (type == 0) { // 기둥일 때 
            return (y == 0 // 바닥 위 
                || beams[x][y] || (x > 0 && beams[x - 1][y]) // 보 위 
                || (y > 0 && pillars[x][y-1])); // 기둥 위 	
        } else { // 보일 때 
            return (y > 0 && (pillars[x][y-1] || pillars[x + 1][y - 1])) // 기둥 위 
                    || ((x > 0 && beams[x - 1][y]) && beams[x + 1][y]); // 보 위 
        }
    }
                    
    private boolean isValid() {
        for (int i=0; i<=n; i++) {
            for (int j=0; j<=n; j++) {
                if (pillars[i][j] && !canBuild(i, j, 0)) return false; 
                if (beams[i][j] && !canBuild(i, j, 1)) return false; 
            }
        }
        return true;
    }               
}
