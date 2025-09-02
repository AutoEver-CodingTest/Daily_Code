import java.util.*;

class Solution {
    public int solution(int n, int k, int[] enemy) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        
        for (int i=0; i<enemy.length; i++) {
            maxHeap.offer(enemy[i]);
            n -= enemy[i];
            if (n < 0) {
                if (k > 0) {
                	int max = maxHeap.poll();
                    n += max;
                    k--;
                } else {
        	        return i;
                }
            }
        }
        
        return enemy.length;
    }
}