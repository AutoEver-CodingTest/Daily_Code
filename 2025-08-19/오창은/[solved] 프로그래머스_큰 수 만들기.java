import java.util.*;

class Solution {
    public String solution(String number, int k) {
       	StringBuilder sb = new StringBuilder(); 
        Stack<Integer> stack = new Stack<>();
        
        for (char charNum: number.toCharArray()) {
            int num = charNum - '0';
            
            while (!stack.isEmpty() && k > 0 && stack.peek() < num) {
              	stack.pop();
                k--;
            }
            stack.push(num);
            // System.out.println(stack);
        }
       
        while (k > 0) {
            stack.pop();
            k--;
        }
        
        for (int num: stack) {
            sb.append(num);
        }
        
        return sb.toString();
    }
}