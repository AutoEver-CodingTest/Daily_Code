import java.util.*;
class Solution {
    public int solution(int[] citations) {
        int answer = 0;
        int count = 0;
        Arrays.sort(citations);
        for (int i = 0;i < citations.length; i++) {
            count = citations.length - i;
            if (count <= citations[i]) {
                answer = count;
                break;
            }
                
        }
        return answer;
    }
}
