import java.util.*;

class Solution {
    public int[] solution(String[] gems) {
        int[] answer = {};
      
        Map<String, Integer> gemsMap = new HashMap<>();
        for (String gem: gems) {
        	gemsMap.put(gem, gemsMap.getOrDefault(gem, 0) + 1);    
        }
        
        //System.out.println(gemsMap);
        int totalGemsCounts = gemsMap.size(); // 보석 종류
        
        int[] min = {0, gems.length-1};
        int left = 0;
        int right = 0;
        Map<String, Integer> currMap = new HashMap<>();

        for (right = 0; right < gems.length; right++) {
           	String gem = gems[right]; 
            currMap.put(gem, currMap.getOrDefault(gem, 0) + 1);
            // System.out.println(currMap);
            
            // 필요한 보석 종류가 됐으면 왼쪽 포인터 이동
            while (currMap.size() >= totalGemsCounts) {                        
                // System.out.println(right + " " + left);
                if (right - left < min[1] - min[0]) {
                    min[1] = right; // 1-based
                    min[0] = left;
                }
                
           		String removeGem = gems[left];	
                currMap.put(removeGem, currMap.getOrDefault(removeGem, 0) - 1);
                if (currMap.get(removeGem) == 0) {
                    currMap.remove(removeGem);
                }
            	// System.out.println(currMap);
                left++;
            }

        }
        
        return new int[] { min[0] + 1, min[1] + 1};
    }
}