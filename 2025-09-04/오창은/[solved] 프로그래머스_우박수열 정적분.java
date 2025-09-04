import java.util.*;

class Solution {
    public double[] solution(int k, int[][] ranges) {
        // k로 만들 수 있는 자연수 
        List<Integer> list = new ArrayList<>();
        while (k >= 1) {
            list.add(k);
            if (k == 1) break;
            
            if (k % 2 == 0) {
                k /= 2;
            } else {
                k = k * 3 + 1;
            }
        }
        
        // 각 구간의 합 
        int n = list.size();
        double[] parts = new double[n - 1];
        for (int i=0; i<n-1; i++) {
            parts[i] = ((double)list.get(i) + (double)list.get(i + 1)) / 2;
        }
        
        // 각 구간 누적합 
        double[] sum = new double[n];
        for (int i=1; i<n; i++) {
            sum[i] = parts[i-1] + sum[i-1];
        }
       
        // 각 정적분 구간에 따른 결과값 
      	int rangeLen = ranges.length;
        // System.out.println(rangeLen+ " "); 
        // System.out.println(n + " "); 
        double[] answer = new double[rangeLen];
        
        for (int i=0; i<rangeLen; i++) {
            int start = ranges[i][0];
            int end = ranges[i][1];
           
            if (n - 1 + end < start) {
                answer[i] = -1.0f;
            } else {
            	answer[i] = sum[n - 1 + end] - sum[start];
            }
        }
      
        /* 
        for (int num: list) {
           	System.out.print(num + " "); 
        }
        for (double s: sum) {
           	System.out.print(s + " "); 
        }
        */
        
        return answer;
    }
}