class Solution {
    public int[] solution(int[] sequence, int k) {
        int[] answer = {};
      
        int size = sequence.length;
        int[] sum = new int[size + 1];
      
        // 1 2 3 4 5
        // 0 1 3 6 10 15
        for (int i=1; i<=size; i++) {
            sum[i] = sum[i-1] + sequence[i-1];
        }
       
        int start = 0;
        int end = 0;
        int left = 0;
        int minLen = size;
        for (int right = 0; right < size; right++) {
            while (left <= right) {
           		int rangeSum = sum[right + 1] - sum[left];
                if (rangeSum == k) {
                    int len = right - left + 1;
                   	if (len < minLen) {
                        minLen = len;
                        start = left; 
                        end = right; 
                    }
                    ++left;
                    break;
                } else if (rangeSum > k) {
                    left++;
                } else {
                    break; 
                }
            }
        }
       
        /*
        for (int s: sum) {
            System.out.println(s + " ");
        }
        */
        return new int[] { start, end };
    }
}