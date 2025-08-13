import java.util.*;

class Solution {
    public int[] solution(int[] progresses, int[] speeds) {
        int[] answer = {};
       
        int size = progresses.length;
        int[] works = new int[size];
        for (int i=0; i<size; i++) {
            works[i] = (100 - progresses[i] + speeds[i] - 1) / speeds[i];
        }
        
        List<Integer> counts = new ArrayList<>();
        int max = 0;
        int count = 0;
        for (int work: works) {
           	if (max < work) {
                // 기존 count 기록
                if (count > 0) { 
                	counts.add(count);
                }
                max = work;
                count = 1;
            } else {
           		count++; 
            }
        }
        counts.add(count);
        
        answer = new int[counts.size()];
        for (int i=0; i<counts.size(); i++) {
           	answer[i] = counts.get(i); 
        }
        // System.out.print(Arrays.toString(works));
        return answer;
    }
}