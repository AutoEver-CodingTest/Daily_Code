import java.util.*;
class Solution {
    static int[][] array;
    static boolean[][] visit;
    static int n,m;
    static int result;
    public int[] solution(String[] maps) {
        
        
        n = maps.length;
        m = maps[0].length();
        visit = new boolean[maps.length][maps[0].length()];
        array = new int[maps.length][maps[0].length()];    
        for (int i = 0; i < maps.length;i++) {
            for (int j = 0;j < maps[i].length();j++) {
                if (maps[i].charAt(j) != 'X') {
                    array[i][j] = maps[i].charAt(j) - '0';
                }
            }
        }
        
        List<Integer> list = new ArrayList<>();
         for (int i = 0; i < maps.length;i++) {
            for (int j = 0;j < maps[i].length();j++) {
                if (!visit[i][j] && array[i][j] != 0) {
                    result = 0;
                    dfs(i,j);
                    list.add(result);
                }
            }
        }
        if (list.size() == 0) {
            int[] answer = {-1};
            return answer;
        } else {
            int[] answer = new int[list.size()];
        for (int i = 0;i < list.size();i++) {
            answer[i] = list.get(i);
        }
            Arrays.sort(answer);
            return answer;
        }
        
        
        
    }
    
    public void dfs(int x,int y) {
        visit[x][y] = true;
        result += array[x][y];
        int [] dx = {1,-1,0,0};
        int [] dy = {0,0,1,-1};
        for (int i = 0;i < 4;i++) {
            int cx = x + dx[i];
            int cy = y + dy[i];
            if (cx >= 0 && cy >= 0 && cx < n && cy < m && !visit[cx][cy] && array[cx][cy] != 0) {
                dfs(cx,cy);
            }
        }
    }
}
