import java.util.*;

class Solution {
    public int solution(int bridge_length, int weight, int[] truck_weights) {
        int answer = 0;
       
        Queue<Integer> bridge = new LinkedList<>();
      	for (int i=0; i<bridge_length; i++) {
            bridge.offer(0);
        } 
        
        int time = 0;
        int currWeight = 0;
        int idx = 0;
        while (!bridge.isEmpty()) {
            time++;
           
            currWeight -= bridge.poll();
            if (idx < truck_weights.length) {
                if (currWeight + truck_weights[idx] <= weight) {
                    bridge.offer(truck_weights[idx]);
                    currWeight += truck_weights[idx];
                    idx++;
            	}
                else {
                    bridge.offer(0);
                }
            }
        }
        
        return time;
    }
}