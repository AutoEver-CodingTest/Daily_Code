import java.util.*;
class Solution {
    static String [] word = {"A","E","I","O","U"}; 
    static int count = 0;
    static List<String> list = new ArrayList<>();
    
    
    public int solution(String word) {
        int answer = 0;
        dfs("");
        
        return list.indexOf(word) +1;
    }
    
    public void dfs(String str) {
        if (str.length() > 5) return;
        if (!str.isEmpty()) {
            list.add(str);
        }
        for (String w : word) {
            dfs(str+w);
        }
    }
}
