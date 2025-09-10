import java.util.*;

class Solution {
    public String solution(int n, int k, String[] cmd) {
  		int[] prev = new int[n];
        int[] next = new int[n];
        
        for (int i=0; i<n; i++) {
            prev[i] = i - 1;
            next[i] = i + 1;
        }
        /*
        for (int num: next) {
            System.out.print(num + " ");
        }
        System.out.println();
        
        for (int num: prev) {
            System.out.print(num + " ");
        }
        System.out.println();
      	*/ 
        next[n-1] = -1;
       
        Stack<Integer> removed = new Stack<>();
        for (String input: cmd) {
       		char op = input.charAt(0); 
            
            if (op == 'D' || op == 'U') {
           		int distance = Integer.parseInt(input.substring(2)); 
            	for (int i=0; i<distance; i++) {
                    k = (op == 'U') ? prev[k] : next[k];
                }    
            	// System.out.println(k);
            } else if (op == 'C') {
                removed.add(k);
                if (prev[k] != -1) next[prev[k]] = next[k];
                if (next[k] != -1) prev[next[k]] = prev[k];
             
                if (next[k] != -1) {
                    k = next[k];
                } else {
                    k = prev[k];
                }
            } else if (op == 'Z') {
                int restore = removed.pop();
                if (prev[restore] != -1) next[prev[restore]] = restore; 
                if (next[restore] != -1) prev[next[restore]] = restore;
            }
        }
      
        StringBuilder sb = new StringBuilder("O".repeat(n));
       	while (!removed.isEmpty()) {
			int remove = removed.pop();
            sb.setCharAt(remove, 'X');
        } 
        
        return sb.toString();
        
    }
}
