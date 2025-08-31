import java.util.*;

class Solution {
    public int[] solution(int n, long k) {
        List<Integer> numbers = new ArrayList<>();
        for (int i=1; i<=n; i++) {
            numbers.add(i);
        }
        
        int[] result = new int[n];
        long[] fact = new long[n+1];
        fact[0] = 1;
        for (int i=1; i<=n; i++) {
            fact[i] = fact[i-1] * i;
        }
        
        k--; 
        for (int i=0; i<n; i++) {
			long f = fact[n - 1 - i];
            int index = (int)(k / f);
            result[i] = numbers.remove(index);
            k %= f;
        }
        
        return result;
    }
}