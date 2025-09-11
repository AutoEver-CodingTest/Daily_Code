import java.util.*;
class Solution {
    public long solution(int n, int[] works) {
        long answer = 0;
        
        PriorityQueue<Integer> pq = new PriorityQueue<>((a,b) -> b - a);
        
        for (int w : works) {
            pq.offer(w);
        }
        
        for (int i = 0; i < n;i++) {
            if (!pq.isEmpty()) {
               int a = pq.poll() - 1;
                if (a > 0) {
                    pq.offer(a);
                }  
            } else {
                return 0;
            }
            
            
        }
        
        long sum = 0 ;
        
        while(!pq.isEmpty()) {
            sum += Math.pow(pq.poll(),2);
        }
        
        
        return sum;
    }
    
    
    
}
