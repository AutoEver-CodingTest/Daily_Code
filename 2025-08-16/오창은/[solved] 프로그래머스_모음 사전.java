import java.util.*;

class Solution {
    String[] words = { "A", "E", "I", "O", "U" };
    int maxSize = words.length;
    List<String> dictionary = new ArrayList<>();
    
    public int solution(String word) {
        backtrack("", 0);
        
        int answer = dictionary.indexOf(word) + 1;
        return answer;
    }
    
    public void backtrack(String curr, int depth) {
        if (depth > 0) {
            dictionary.add(curr);
            // System.out.println(curr);
        }
        
        if (depth == maxSize) {
            return;
        }
  
        for (String word: words) {
        	backtrack(curr + word, depth + 1);
        }
    }
}