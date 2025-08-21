import java.util.*;

class Solution {
    public int solution(int[] queue1, int[] queue2) {    
        
        long total = 0;
        int size = queue1.length;     
        long sum1 = 0;
        long sum2 = 0;
        for (int i=0; i<size; i++) {
            sum1 += queue1[i];
       		sum2 += queue2[i]; 
        }
        
        total = sum1 + sum2;  
        if (total % 2 != 0) return -1;
      	long target = total / 2; 
        
        Queue<Integer> q1 = new LinkedList<>();
        Queue<Integer> q2 = new LinkedList<>();
        for (int i=0; i<size; i++) {
            q1.offer(queue1[i]);
            q2.offer(queue2[i]);
        }
                
       	int moveCount = 0;
        boolean isSuccess = false;
        while (moveCount <= 3*size) {
            if (sum1 == target) {
                return moveCount;
            } 
            
            int move = 0;
            if (target < sum2) {
               	move = q2.poll();
                q1.offer(move);
                sum1 += move;
                sum2 -= move;
            } else if (sum1 > target) {
               	move = q1.poll();
                q2.offer(move);
                sum2 += move;
                sum1 -= move;
            } 
        	moveCount++; 

        }
        
        return -1;
    }
}