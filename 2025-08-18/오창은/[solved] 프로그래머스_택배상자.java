import java.util.*;

class Solution {
    public int solution(int[] order) {
   		Stack<Integer> subBelt = new Stack<>();
        
        int currBox = 1;
        int count = 0;
        for (int target: order) {
            // 우선순위를 가지는 박스 번호 순서대로 
            while (currBox <= order.length && currBox < target) {
                subBelt.push(currBox); // 순서에 안 맞은 경우 컨테이너로 
                currBox++;
            }
            
            //System.out.println(subBelt);
            if (currBox == target) {
                currBox++;
                count++;
            } else if (!subBelt.isEmpty() && subBelt.peek() == target) {
                subBelt.pop();
                count++;
            } else {
                break;
            }
        }
        return count;
    }
}