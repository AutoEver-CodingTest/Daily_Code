import java.util.*;

class Solution {
    public int solution(int[] order) {
        int n = order.length;
        int answer = 0;
        int seq = 0;                 
        Stack<Integer> stack = new Stack<>();

        for (int box = 1; box <= n; box++) {
            stack.push(box);         

           
            while (!stack.isEmpty() && stack.peek() == order[seq]) {
                stack.pop();
                answer++;
                seq++;
            }
        }

        return answer;
    }
}
