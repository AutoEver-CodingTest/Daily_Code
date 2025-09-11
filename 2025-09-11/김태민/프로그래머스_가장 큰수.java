import java.util.*;
class Solution {
    public String solution(int[] numbers) {
        String answer = "";
        
        String [] numStr = new String[numbers.length];
        
        for (int i = 0 ;i < numbers.length;i++) {
            numStr[i] = Integer.toString(numbers[i]);
        }
        
        Arrays.sort(numStr, (a,b) -> (b + a).compareTo(a+b));
        
        if (numStr[0].equals("0")) {
            return "0";
        }
        
        for (String s : numStr) {
            answer += s;
        }
        
        return answer;
    }
}
