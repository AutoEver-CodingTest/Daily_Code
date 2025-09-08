import java.util.*;

class Solution {
    public int solution(int N, int number) {
        if (N == number) return 1; 
        
        // N을 i번 사용해서 만들 수 있는 집합 
        Set<Integer>[] dp = new HashSet[9];
        for (int i=0; i<9; i++) {
            dp[i] = new HashSet<>();
        }
        
        for (int i=1; i<9; i++) {
       		int repeatNum = Integer.parseInt(String.valueOf(N).repeat(i)); 
            dp[i].add(repeatNum);
            
            for (int j=1; j<i; j++) {
                for (int a: dp[j]) {
                    for (int b: dp[i - j]) {
                        dp[i].add(a + b);
                        dp[i].add(a - b);
                        dp[i].add(b - a);
                       	dp[i].add(a * b);
                        if (b != 0) dp[i].add(a / b);
                        if (a != 0) dp[i].add(b / a);
                    }
                }
            }
            
            if (dp[i].contains(number)) return i;
        }
    
        return -1;
    }
}