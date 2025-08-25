import java.util.*;

class Solution {
    String[] arr = { "aya", "ye", "woo", "ma" };
  	boolean[] visited = new boolean[4]; 
  	List<String> selected = new ArrayList<>(); 
    
    public int solution(String[] babbling) {
        int answer = 0;
       
        // 4 + 6 + 6 + 4
        // 20 
        
        dfs("", 0);
        for (String str: selected) {
            for (String input: babbling) {
                if (str.equals(input)) 
                    answer++;
            }
        }
        return answer;
    }
    public void dfs(String curr, int depth) {
      	if (depth > 0) {
            selected.add(curr);
            // return;
        } 
        
        // System.out.println(curr);
        for (int i=0; i<arr.length; i++) {
            if(!visited[i]) {
               	visited[i] = true;	
                dfs(curr + arr[i], depth + 1); 
               	visited[i] = false;	
            }
        }
    }
}