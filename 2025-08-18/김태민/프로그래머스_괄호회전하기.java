import java.util.*;
class Solution {
    public int solution(String s) {
        int answer = 0;
        
        for (int i = 0 ; i< s.length() ; i++) {
            if (result(s)) {
                answer++;
            }
            s = s.substring(1) + s.charAt(0);  
            
        }
        return answer;
    }
    
    public boolean result(String s) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0 ; i< s.length() ; i++) {
            if (s.charAt(i) == ']') {
                if (!stack.isEmpty() && stack.peek() == '[') {
                    stack.pop();
                } else {
                    return false;
                }
            } else if( s.charAt(i) == '}') {
                if (!stack.isEmpty() &&stack.peek() == '{') {
                    stack.pop();
                } else {
                    return false;
                }
                
            } else if( s.charAt(i) == ')') {
                if (!stack.isEmpty() && stack.peek() == '(') {
                    stack.pop();
                } else {
                    return false;
                }
                
            } else {
                stack.push(s.charAt(i));
            }
        }
        if (stack.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
}
