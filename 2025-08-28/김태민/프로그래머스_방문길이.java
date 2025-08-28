import java.util.*;
class Solution {
    public int solution(String dirs) {
        int answer = 0;
        
        int [][] array = new int[11][11];
        Set<String> set = new HashSet<>();
        
        int [] dx = {1,-1};
        int [] dy = {1,-1};
        int cx = 0;
        int cy = 0;
        
        for (int i = 0;i < dirs.length() ; i++) {
            char c = dirs.charAt(i);   
            int n;
            if (c == 'U') {
                n = cy + dy[0];
                if (n <= 5 && n >= -5) {
                    
                    set.add(cx + " " + cy + "," + cx + " " + n);
                    set.add(cx + " " + n + "," + cx + " " + cy);
                    cy = n;
                }
            } else if (c == 'D') {
                n = cy + dy[1];
                if (n <= 5 && n >= -5) {
                    set.add(cx + " " + cy + "," + cx + " " + n);
                    set.add(cx + " " + n + "," + cx + " " + cy);
                    cy = n;
                }
            } else if (c == 'R') {
                n = cx + dx[0];
                if (n <= 5 && n >= -5) {
                    set.add(cx + " " + cy + "," + n + " " + cy);
                    set.add(n + " " + cy + "," + cx + " " + cy);
                    cx = n;
                }
            } else {
                n = cx + dx[1];
                if (n <= 5 && n >= -5) {
                    set.add(cx + " " + cy + "," + n + " " + cy);
                    set.add(n + " " + cy + "," + cx + " " + cy);
                    cx = n;
                }
            }
            
        }
        
        
        return set.size() / 2;
    }
    
    
}
