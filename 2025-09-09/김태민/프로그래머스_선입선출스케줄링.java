class Solution {
    public int solution(int n, int[] cores) {
        int answer = 0;
        
        int m = cores.length;
        
        long left = 0;
        long right = 10000*n;
        
        while(left < right) {
            long sum = m;
            long mid = (left+right)/ 2;
            for (int c : cores) {
                sum += mid/c;
            }
            if (sum >= n) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        
        long T = left; 

        
        long startedBeforeT = m;
        for (int c : cores) startedBeforeT += (T - 1) / c;

        long remain = n - startedBeforeT;

       
        for (int i = 0; i < m; i++) {
            if (T % cores[i] == 0) {
                remain--;
                if (remain == 0) return i + 1;
            }
        }
        
        return -1;
        
        
        
        
    }
}
