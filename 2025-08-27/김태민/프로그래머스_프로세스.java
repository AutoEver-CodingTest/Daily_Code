import java.util.*;
class Solution {
    public int solution(int[] priorities, int location) {
        int answer = 1;
        Queue<int[]> q = new LinkedList<>();
        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
        for (int i = 0 ; i < priorities.length ; i++) {
            q.offer(new int[]{i, priorities[i]});
            pq.offer(priorities[i]);
        }
        
        while(!q.isEmpty()) {
            int [] a = q.peek();
            
            if (a[1] >= pq.peek()) {
                if (a[0] == location) {
                    
                    return answer;
                }
                
                q.poll();
                pq.poll();
                answer++;
            } else {
                q.poll();
                q.offer(a);
            }
        }
        
        return answer;
    }
}
