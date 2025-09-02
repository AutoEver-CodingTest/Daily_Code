class Solution {
  	/* 
    public int find(int n) {
        int count = 0;
       	for (int i=1; i<Math.sqrt(n); i++) {
           	if (n % i == 0) {
                count++; 
            }
        }
        if (n % Math.sqrt(n) == 0) {
			count = count * 2 + 1;
        } else {
            count = count * 2;
        }
        
        return count;
    }
    */
    
    
    public int[] solution(int e, int[] starts) {
    	int[] cnt = new int[e+1];
   		for (int i=1; i<=e; i++) {
            for (int j=i; j<=e; j+=i) {
                cnt[j]++;
            }
        } 
        
        int n = starts.length;
        
        int[] answer = new int[n];    
        int[] dp = new int[e + 1];
        
        dp[e] = e;
        for (int i=e-1; i>=1; i--) {
       		if (cnt[i] >= cnt[dp[i + 1]]) {
                dp[i] = i;
            } else {
                dp[i] = dp[i + 1];
            }
        }
        
        for (int i=0; i<starts.length; i++) {
            int start = starts[i];
           	answer[i] = dp[start];
        }
        
        return answer;
    }
}