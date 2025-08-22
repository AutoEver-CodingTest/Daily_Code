import java.util.*;
class Solution {
    public int solution(String[][] clothes) {
        int answer = 1;
        
        Map<String,Integer> map = new HashMap<>();
        for (String [] str : clothes) {
            map.put(str[1],map.getOrDefault(str[1],0) + 1);
        }
        Set<String> set = map.keySet();
        for (String str : set) {
            answer *= (map.get(str) + 1);
        }
                       
        answer -= 1;
        
        
        
        return answer;
    }
}
