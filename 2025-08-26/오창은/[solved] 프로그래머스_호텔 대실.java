import java.util.*;

class Solution {
    public int solution(String[][] book_time) {
        int answer = 0;
      
        int n = book_time.length;
        int[][] book = new int[n][2];
        int[] room = new int[n];
        // 시작시간 기준으로 오름차순을 하고, 
       	// 한개씩 넣을 때 넣을거의 시작시간과 기존에 예약 중인 방 끝 시간 비교하여 
        // 모든 방에 대해 [1920, 1520, 1700] 끝 시간 배열 만들어서 
        // 순회하면서 넣을 방 찾고
        // 다 못넣는다면 방 추가
       
        for (int i=0; i<n; i++) {
            String s = book_time[i][0];
            String e = book_time[i][1];

            String[] parts = s.split(":");
            int sh = Integer.parseInt(parts[0]);
            int sm = Integer.parseInt(parts[1]);

            parts = e.split(":");
            int eh = Integer.parseInt(parts[0]);
            int em = Integer.parseInt(parts[1]);

            int start = sh*60 + sm;
            int end = eh*60 + em;
            
            book[i][0] = start;
            book[i][1] = end;
        }
        
        Arrays.sort(book, (a,b) -> a[0] - b[0]);
  
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int[] b: book) {
           	int start = b[0];
            int end = b[1];
            
            if (!pq.isEmpty() && pq.peek() <= start) {
                pq.poll();
            }
            pq.offer(end + 10); // 10분 더하기 
        }
        
        /*
        for (int[] b: book) {
            System.out.println(b[0] + " " + b[1]);
        }
        for (int r: room) {
            System.out.println(r + " ");
        }
        */
        return pq.size();
    }
}