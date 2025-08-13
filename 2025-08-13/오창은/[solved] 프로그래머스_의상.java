import java.util.*;

class Solution {
    public int solution(String[][] clothes) {
        int answer = 0;
        
        Map<String, Integer> map = new HashMap<>();
       
        for (String[] cloth: clothes) {
            String name = cloth[0];
            String type = cloth[1];
            map.put(type, map.getOrDefault(type, 0) + 1);
        }
       
        int total = 1;
        for (int num: map.values()) {
            total *= (num+1);
        }
        
        return total - 1;
    }
}