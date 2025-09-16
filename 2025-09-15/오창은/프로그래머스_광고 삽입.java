class Solution {
    public String solution(String play_time, String adv_time, String[] logs) {
        String answer = "";
      
        int time = toSecond(play_time);
        int adv = toSecond(adv_time);
        
      	int[] arr = new int[time]; 
        /*
       	System.out.println(time); 
       	System.out.println(time + adv); 
     	*/
        // 누적합 배열을 담는 배열 
        int[] sum = new int[time + 1];
        
        for (String log: logs) {
            int[] range = rangeToSecond(log);
            int start = range[0];
            int end = range[1];
            sum[start] += 1;
            sum[end] -= 1;
        }
        
        for (int i=1; i<time + 1; i++) {
            sum[i] += sum[i - 1];
        }
        
        long[] prefixSum = new long[time + 1];
        for (int i=1; i<time + 1; i++) {
            prefixSum[i] = prefixSum[i-1] + sum[i];
        }
      
        long max = prefixSum[adv - 1];
        int maxIdx = 0;
        // 누적합의 범위의 합이 가장 클 때의 시작 인덱스를 리턴 
        for (int i=adv; i<time+1; i++) {
            long rangeSum = prefixSum[i] - prefixSum[i - adv];
       		if (max < rangeSum) {
                max = rangeSum;
                maxIdx = i - adv + 1;
            }     
        }
        
        return toTime(maxIdx);
    }
    
    private int toSecond(String time) {
        String[] parts = time.split(":");
        int hh = Integer.parseInt(parts[0]) * 3600;
        int mm = Integer.parseInt(parts[1]) * 60;
        int ss = Integer.parseInt(parts[2]);
        
        return hh + mm + ss;
    }
    private int[] rangeToSecond(String timeRange) {
        String[] parts = timeRange.split("-");
        String startTime = parts[0];
        String endTime = parts[1];
        
        int start = toSecond(startTime);
        int end = toSecond(endTime);
        return new int[] {start, end};
    }
    private String toTime(int second) {
        int hh = second / 3600;
        second %= 3600; 
        int mm = second / 60;
        int ss = second % 60;
        return String.format("%02d:%02d:%02d", hh, mm, ss);
    }
}
