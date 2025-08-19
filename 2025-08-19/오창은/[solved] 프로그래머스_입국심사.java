class Solution {
    public long solution(int n, int[] times) {
        long answer = 0;
        long MAX_MAN = 1_000_000_000;
        long MAX_WORK_TIME = 1_000_000_000;
        
        long left = 1; // 기다리는 시간 최소값
        long right = MAX_MAN * MAX_WORK_TIME; // 입국 심사 최악의 기다리는 시간 
        while (left <= right) {
        	long mid = (left + right) / 2;  
           
            long sum = 0;
            for (int time: times) {
               	sum += mid / time;
            }
            
            if (sum >= n) {
                answer = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        
        return answer;
    }
}