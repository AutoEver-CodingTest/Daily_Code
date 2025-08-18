import java.util.*;
class Solution {
    public String[] solution(String[] record) {
        
       
        List<String[]> answer3 = new ArrayList<>();
       
        Map<String,String> map = new HashMap<>();
        
        for (String r : record) {
            String[] str = r.split(" ");
            if (str[0].equals("Enter")) {
                map.put(str[1], str[2]);
                answer3.add(new String[]{str[1],"님이 들어왔습니다."}) ;
            } else if(str[0].equals("Leave")) {
                answer3.add(new String[]{str[1],"님이 나갔습니다."}) ;
            } else {
                map.put(str[1], str[2]);
            }
        }
        String[] answer = new String[answer3.size()];
        for (int i = 0; i < answer3.size();i++)  {
            answer[i] = map.get(answer3.get(i)[0]) + answer3.get(i)[1];
        }
        
        
        return answer;
    }
}
