import java.util.*;
class Solution {
    public int solution(int[] scoville, int K) {
        int answer = 0;
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int n : scoville) {
            pq.offer(n);
        }
        while(pq.peek() < K && pq.size() >= 2) {
            int a = pq.poll();
            int b = pq.poll();
            int c = a + (b * 2);
            pq.offer(c);
            answer++;
        }
        if (pq.peek() < K) {
            return -1;
        }
        return answer;
    }
}
