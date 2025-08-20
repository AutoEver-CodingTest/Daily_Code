import java.util.*;

class Solution {
   	Set<Integer> set = new HashSet<>();	
    
    public boolean isPrime(int n) {
        if (n < 2) return false; 
        if (n == 2) return true; 
        if (n % 2 == 0) return false; 
        
        for (int i=3; i<=Math.sqrt(n); i++) {
            if (n % i == 0) return false;
        }
        return true;
    } 
    
    public int solution(String numbers) {
    	boolean[] visited = new boolean[numbers.length()];
        dfs(numbers, "", visited);
  
        int answer = 0;
        for (int num: set) {
            if (isPrime(num)) {
               	answer++; 
            }
        }
       	// boolean isTrue = isPrime(2);
        // System.out.println(isTrue);
        return answer;
    }
    
    public void dfs(String numbers, String curr, boolean[] visited) {
       	if (!curr.equals("")) {
          	set.add(Integer.parseInt(curr));
        } 
        
        for (int i=0; i<numbers.length(); i++) {
            if (!visited[i]) {
                visited[i] = true;
            	dfs(numbers, curr + numbers.charAt(i), visited);
                visited[i] = false;
            }
        }
    }
}