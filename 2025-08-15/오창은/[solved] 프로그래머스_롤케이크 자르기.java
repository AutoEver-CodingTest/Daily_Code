import java.util.*;

class Solution {
    public int solution(int[] topping) {
        int n = topping.length;
        
        int[] leftCount = new int[n];
        int[] rightCount = new int[n];
        
        Map<Integer, Integer> leftMap = new HashMap<>();
     	for (int i=0; i<n; i++) {
            leftMap.put(topping[i], leftMap.getOrDefault(topping[i], 0) + 1);
            leftCount[i] = leftMap.size();
        }
        
        Map<Integer, Integer> rightMap = new HashMap<>();
     	for (int i=n-1; i>=0; i--) {
            rightMap.put(topping[i], rightMap.getOrDefault(topping[i], 0) + 1);
            rightCount[i] = rightMap.size();
        }
       
        int count = 0;
        for (int i=0; i<n-1; i++) {
            if (leftCount[i] == rightCount[i+1]) {
      			count++; 
            }
        }
        
        return count;
    }
}