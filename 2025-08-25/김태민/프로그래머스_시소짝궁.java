import java.util.*;

class Solution {
    public long solution(int[] weights) {
        long answer = 0;
        
        Arrays.sort(weights);
        
        Map<Double,Integer> map = new HashMap<>();
        
        for (int w : weights) {
            double a = (w * 2) / 3.0;
            double b = (w * 1) / 2.0;
            double c = (w * 3) / 4.0;
            double d = (w * 1);
            
            if (map.containsKey(a)) {
                answer += map.get(a);    
            }  if (map.containsKey(b)) {
                answer += map.get(b);   
            }  if (map.containsKey(c)) {
                answer += map.get(c);   
            }  if (map.containsKey(d)) {
                answer += map.get(d);   
            }
            
            map.put(d,map.getOrDefault(d,0) + 1);
        }
        
        return answer;
    }
}
