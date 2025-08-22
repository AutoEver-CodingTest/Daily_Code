import java.util.*;

class Solution {
    public long solution(int[] weights) {
        long answer = 0;
      
        Map<Double, Integer> map = new HashMap<>();
        Arrays.sort(weights);
        // 100, 100, 180, 270, 360
        
        for (int weight: weights) {
          	answer += map.getOrDefault((double)weight, 0);
          	answer += map.getOrDefault(weight * 2.0 / 3.0, 0); 
          	answer += map.getOrDefault(weight * 2.0 / 4.0, 0); 
          	answer += map.getOrDefault(weight * 3.0 / 4.0, 0); 
            
           	map.put((double)weight, map.getOrDefault((double)weight, 0) + 1);
        }
        
        return answer;
    }
}