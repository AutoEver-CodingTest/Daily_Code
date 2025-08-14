import java.util.*;

class Solution {
    public int solution(int cacheSize, String[] cities) {
       	if (cacheSize == 0) {
            return 5 * cities.length;
        } 
        
        int answer = 0;
     
        List<String> cache = new ArrayList<>();
        
        for (String city: cities) {
           	String cLow = city.toLowerCase(); 
            
            // cashe.size()는 항상 casheSize보다 작거나 같아야 해 
            if (!cache.contains(cLow)) {
                if (cache.size() == cacheSize) {
                   	cache.remove(0); 
                }
                cache.add(cLow);
                answer += 5;
            } else {
                int index = cache.indexOf(cLow);
                if (index != -1) {
                    cache.remove(index);
                    // cache.remove(cLow);
                    cache.add(cLow);
                    answer += 1;
                } 
            }
            // System.out.println(cache);
        }
        
        return answer;
    }
}