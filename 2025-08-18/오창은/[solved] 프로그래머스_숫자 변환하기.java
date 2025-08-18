import java.util.*;

class Solution {
    public int solution(int x, int y, int n) {
      
        boolean[] visited = new boolean[y+1]; // y 이상인 값들은 신경안씀
   		Queue<Integer> queue = new LinkedList<>(); // 현재 계산된 값, 연산 횟수 
        
        queue.offer(x);
       	visited[x] = true;
        
        int level = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i=0; i<size; i++) {
                int curr = queue.poll();
            
                if (curr == y) return level;
                
                int[] nextValues = { curr + n, curr * 2, curr * 3 };
                for (int value: nextValues) {
                    if (value <= y && !visited[value]) {
                        queue.offer(value);
                        visited[value] = true;
                    }
                }
            }
            level++;
        }
        
        return -1;
    }
}