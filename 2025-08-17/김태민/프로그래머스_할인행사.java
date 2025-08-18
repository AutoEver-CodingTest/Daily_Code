import java.util.*;     
class Solution {
    public int solution(String[] want, int[] number, String[] discount) {
        int answer = 0;
        
        Map<String,Integer> map = new HashMap<>();
        for (int i = 0; i < want.length;i++) {
            map.put(want[i],number[i]);
        }
        
        for (int i = 0; i <= discount.length - 10;i++){
            boolean result = true;
            Map<String,Integer> map2 = new HashMap<>();
            for (int j = i; j < i + 10;j++) {
                map2.put(discount[j], map2.getOrDefault(discount[j],0) + 1);
            }
            for (String s : want) {
                if (map2.getOrDefault(s,0) != map.get(s)) {
                    result = false;
                    break;
                }
            }
            if (result) {
                answer++;
            }
        } 
        
        return answer;
    }
}
