class Solution {
    int solution(int[][] land) {
        // 같은 열을 연속해서 밟을 수 없기 때문에 
        // greedy 방식이 최대값이 아닐 수 있다.
        
        // dp 방식
       	int size = land.length;
        int[][] dp = new int[size][4];
     
        // 첫번째 행 저장 
        for (int i=0; i<4; i++) {
            dp[0][i] = land[0][i]; 
        }
        
        for (int i=1; i<size; i++) {
            for (int j=0; j<4; j++) {
                int max = 0;
                for (int k=0; k<4; k++) {
                    if (j==k) continue;
                    max = Math.max(max, dp[i-1][k]);
                }
                dp[i][j] = land[i][j] + max;
            }
        }
      
        /*
        for (int[] row: dp) {
            for (int i=0; i<row.length; i++) {
        		System.out.print(row[i]);
            }
            System.out.println();
        } 
        */ 
        
        int maxSum = 0;
        for (int i=0; i<4; i++) {
            maxSum = Math.max(maxSum, dp[size-1][i]);
        }
        
        return maxSum;
    }
}