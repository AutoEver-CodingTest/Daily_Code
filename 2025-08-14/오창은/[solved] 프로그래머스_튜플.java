import java.util.*;

class Solution {
    public int[] solution(String s) {
        int[] answer = {};
     
        s = s.substring(1, s.length()-1);
       
        // 값을 순회하면서 {} 사이 값들을 
        // String[] 에 저장
        String strNums = "";
       	StringBuilder sb = new StringBuilder(); 
        for (char ch: s.toCharArray()) {
            if (ch == '{' || ch == '}') continue;
            sb.append(ch);
        }
        strNums = sb.toString();
       	// System.out.println(strNums); 
  
        Map<Integer, Integer> countMap = new HashMap<>();
        String[] arrNums = strNums.split(",");
        for (String strNum: arrNums) {
            int num = Integer.parseInt(strNum);
           	countMap.put(num, countMap.getOrDefault(num, 0) + 1); 
        }
        
        List<Integer> list = new ArrayList<>(countMap.keySet());
        list.sort((a, b) -> countMap.get(b) - countMap.get(a));
        // System.out.println(list);
      
        answer = new int[list.size()];
        for (int i=0; i<list.size(); i++) {
            answer[i] = list.get(i);
        }
        
        return answer;
    }
}