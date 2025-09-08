import java.util.*;
class Solution {
    public int[] solution(int[] sequence, int k) {
        int[] answer = new int[2];
        int n = sequence.length;
        int size = Integer.MAX_VALUE;
        int bestL = -1;
        int bestR = -1;
        int left = 0;
        int right = 0;
        int sum = 0;
        while(left < n) {
            while (right < n && sum < k) {
                sum += sequence[right++];
            }
            
            if (sum == k) {
                int len = right - left;
                if (len < size) {
                    bestL = left;
                    bestR = right - 1;
                    size = len;
                }
            }
            
            sum -= sequence[left++];
            if (right == n && sum < k) {
                break;
            }
        }
        
        return new int[]{bestL,bestR};
    }
}
