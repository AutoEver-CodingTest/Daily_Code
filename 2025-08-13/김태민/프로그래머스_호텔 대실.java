import java.util.*;
class Solution {
    public int solution(String[][] book_time) {
        int answer = 0;
        Arrays.sort(book_time, (a, b) -> a[0].compareTo(b[0]));
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int i = 0 ; i < book_time.length ; i++) {
            int start = toTime(book_time[i][0]);
            int end = toTime(book_time[i][1]) + 10;
            if (!pq.isEmpty() && pq.peek() <= start) {
                pq.poll();
            }
            pq.offer(end);
            
            answer = Math.max(answer,pq.size());
        }
        
        return answer;
    }
    
    public static int toTime(String str) {
        String[] strTime = str.split(":");
        return Integer.parseInt(strTime[0]) * 60 + Integer.parseInt(strTime[1]);
    }
}
