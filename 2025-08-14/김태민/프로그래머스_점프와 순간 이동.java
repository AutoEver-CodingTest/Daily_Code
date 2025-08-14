import java.util.*;

public class Solution {
    public int solution(int n) {
        int ans = 0;
        int count = 0;
        while(n > 0) {
            if (n % 2 == 1) {
                n--;
                count++;
            } else { 
                n /= 2;
            }    
        
        }

        
        return count;
    }
}
