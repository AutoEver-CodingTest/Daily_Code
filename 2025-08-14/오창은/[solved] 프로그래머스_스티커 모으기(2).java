import java.util.*;

class Solution {
    public int getMax(int sticker[]) {
     	int size = sticker.length;
      	if (size == 1) {
            return sticker[0];
        }
        
    	int[] dp = new int[size];
        dp[0] = sticker[0];
        dp[1] = Math.max(sticker[0], sticker[1]);
   
        for (int i=2; i<size; i++) {
            dp[i] = Math.max(dp[i-1], dp[i-2] + sticker[i]);
        }
        return dp[size-1];
    }
    
    public int solution(int sticker[]) {
        int size = sticker.length;
        
      	// 스티커가 한장만 있는 경우 	
        if (size == 1) {
            return sticker[0];
        }
        // 첫번째 스티커 제외하고 시작
        int max1 = getMax(Arrays.copyOfRange(sticker, 1, size));
       	// 마지막 스티커 제외하고 시작  
        int max2 = getMax(Arrays.copyOfRange(sticker, 0, size-1));
        
    	return Math.max(max1, max2);    
    }
}