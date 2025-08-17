import java.util.*;

class Solution {
    static boolean[] visit; 
    static Set<Integer> set;
    public int solution(String numbers) {
        int answer = 0;
        set = new HashSet<>();
        visit = new boolean[numbers.length()];
        dfs("",numbers);
        for (int num: set) {
            if(isPrime(num)) {
                answer++;
            }
        }
        return answer;
    }
    
    public static void dfs(String s, String numbers) {
        for (int i = 0; i < numbers.length();i++) {
            if(!visit[i]) {
                visit[i] = true;
                set.add(Integer.parseInt(s + numbers.charAt(i)));
                dfs(s+numbers.charAt(i),numbers);
                visit[i] = false;
            }
        }
    }
    
    public static boolean isPrime(int n) {
        if (n < 2) {
            return false;
        }
		
        for (int i = 2; i <= (int) Math.sqrt(n); i++) {
            if (n % i == 0) {
                return false;
            }
        }
 
        return true;
    }
}
