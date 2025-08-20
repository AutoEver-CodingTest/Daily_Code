import java.util.*;

class Solution {
	Map<String, Integer> map = new HashMap<>();
    Map<String, String> parentMap = new HashMap<>();
    
    public int[] solution(String[] enroll, String[] referral, String[] seller, int[] amount) {
  		int size = enroll.length;	
        int[] answer = new int[size];
        
        for (int i=0; i<size; i++) {
            map.put(enroll[i], 0);
            parentMap.put(enroll[i], referral[i]);
        }
        
        for (int i=0; i<seller.length; i++) {
            String man = seller[i];
            int profit = amount[i] * 100;
            dfs(man, profit);
        }
        
        for (int i=0; i<size; i++) {
            String man = enroll[i];
            answer[i] = map.get(man);
        }
        // System.out.println(map);
        return answer;
    }
    public void dfs(String man, int profit) {
        if (man.equals("-") || profit == 0) {
            return; 
        }
        int give = profit / 10;
        int keep = profit - give;
      	map.put(man, map.get(man) + keep); 
        
        dfs(parentMap.get(man), give);
    }
}