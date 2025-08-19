import java.util.*;

class Solution {
   	class Job {
        int id; // 작업 번호 
        int start; // 작업 시작 시간 
        int length; // 작업 소요 시간
        
        public Job(int id, int start, int length) {
            this.id = id;
            this.start = start;
            this.length = length;
        }
    } 
    
    public int solution(int[][] jobs) {
        int n = jobs.length;
       
        Arrays.sort(jobs, (a, b) -> {
           if (a[0] == b[0]) return a[1] - b[1];
           return a[0] - b[0];
        });
       
        PriorityQueue<Job> pq = new PriorityQueue<>((a, b) -> {
            if (a.length != b.length) return a.length - b.length;
            if (a.start != b.start) return a.start - b.start;
            return a.id - b.id;
        });
        
        int time = 0; // 현재 시각
        int total = 0; // 총 반환 시간의 합 
        int idx = 0; // 아직 넣지 않은 작업 인덱스 
        
        while (idx < n || !pq.isEmpty()) {
           	while (idx < n && jobs[idx][0] <= time) {
               	pq.offer(new Job(idx, jobs[idx][0], jobs[idx][1]));
                idx++; // 다음 작업으로
            } 
            
            if (!pq.isEmpty()) {
               	Job curr = pq.poll();
                time += curr.length; // 현재 시간 + 작업 실행 후 종료 시간 
                total += (time - curr.start); // 반환 시간 = 종료 시간 - 요청 시간
            } else {
                time = jobs[idx][0]; // 다음 작업 시작 시간 
            }
        }
        
        return total / n;
    }
}