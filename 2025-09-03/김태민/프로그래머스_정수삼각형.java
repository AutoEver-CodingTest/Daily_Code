import java.util.*;
class Solution {
    public int solution(int[][] triangle) {
        int answer = 0;
        int n = triangle.length;
        int m = triangle[n - 1].length;
        int [][] dp = new int[n][m];
        dp[0][0] = triangle[0][0];
         
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < triangle[i].length; j++) {
                if (j == 0) {
                    dp[i][j] = dp[i-1][j] + triangle[i][j];
                } else if (j == triangle[i].length - 1) {
                    dp[i][j] = dp[i-1][j-1] + triangle[i][j];
                } else {
                    dp[i][j] = Math.max(dp[i-1][j-1],dp[i-1][j]) + triangle[i][j];
                }
                
            }
        }
        
        for (int i = 0; i < m;i++) {
            if (answer < dp[n-1][i]) {
                answer = dp[n-1][i];
            }
        }
        return answer;
    }
}
