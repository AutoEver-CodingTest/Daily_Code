import java.util.*;

class Solution {
    public int solution(int[] priorities, int location) {
        int answer = 0;
     
        int size = priorities.length;
        
        // arr[] = {인덱스, 우선순위}
        int[] arr = new int[size];
        Queue<int[]> queue = new LinkedList<>();
       	PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder()); 
        for (int i=0; i<size; i++) {
            queue.offer(new int[]{i, priorities[i]});
       		pq.offer(priorities[i]); 
        }
        
        while (!pq.isEmpty()) {
            int[] process = queue.poll();
            int priority = pq.peek();
           
            // 우선순위가 가장 높으면
            if (process[1] == priority) {
                pq.poll();
                answer++;
                if (process[0] == location) {
					break;	
                }
            } else {
                // 우선순위가 높은게 아니라면 가장 뒤로
                queue.offer(process);
            }
        }
        
        // System.out.println(pq);
        
        return answer;
    }
}