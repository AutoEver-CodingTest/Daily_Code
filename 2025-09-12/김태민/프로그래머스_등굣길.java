import java.util.*;
class Solution {
    
    public int solution(int m, int n, int[][] puddles) {
        int answer = 0;
        boolean [][] check = new boolean[m + 1][n + 1];
        int [][] dp = new int[m+1][n+1];
        
        for (int [] p : puddles) {
            check[p[0]][p[1]] = true;
        }
        
        for (int i = 1;i <= m;i++) {
            for (int j = 1;j <= n;j++) {
                if (i == 1 && j == 1) {
                    dp[i][j] = 1;
                } else {
                    if (check[i][j]) {
                        dp[i][j] = 0;
                    } else {
                        dp[i][j] = (dp[i-1][j] + dp[i][j-1]) % 1000000007;
                    }
                    
                }
                
            }
        }
        
        
        return dp[m][n];
    }
    
    
}
