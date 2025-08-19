import java.util.*;
class Solution {
    public int[] solution(int[] progresses, int[] speeds) {
        
        int count = 0;
        List<Integer> list = new ArrayList<>();
      
        Queue<Integer> q = new LinkedList<>();
        for (int i = 0 ; i < progresses.length ; i++) {
            int day = (int) Math.ceil((double) (100 - progresses[i]) / speeds[i]);
            q.offer(day);
        }
        
        while(!q.isEmpty()) {
            int p = q.poll();
            count++;
            while(!q.isEmpty() && q.peek() <= p) {
                q.poll();
                count++;
            }
            list.add(count);
            count = 0;
        }
        int[] answer = new int[list.size()];
        for (int i = 0; i < list.size();i++) {
            answer[i] = list.get(i);
        }
        return answer;
    }
}
