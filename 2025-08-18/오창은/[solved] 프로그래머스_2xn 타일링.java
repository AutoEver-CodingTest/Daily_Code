class Solution {
    public int solution(int n) {
        final int NUM = 1_000_000_007;
       
        int[] dp = new int[n];
        dp[0] = 1;
        dp[1] = 2;
        for (int i=2; i<n; i++) {
           	dp[i] = (dp[i-1] + dp[i-2]) % NUM;
        }
        
        return dp[n-1];
    }
}