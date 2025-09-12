import java.util.*;
class Solution {
    public int solution(int[] arrayA, int[] arrayB) {
        
        int gcdA = arrayA[0];
        int gcdB = arrayB[0];
        
        for (int i = 1; i < arrayA.length;i++) {
            gcdA = gcd(gcdA,arrayA[i]);
        }
        
        for (int i = 1; i < arrayB.length;i++) {
            gcdB = gcd(gcdB,arrayB[i]);
        }
        int answer = 0;
        if (check(arrayA,gcdB)) {
            answer = Math.max(answer,gcdB);
        }
        
        if (check(arrayB,gcdA)) {
            answer = Math.max(answer,gcdA);
        }
        
        
        
        
        return answer;
    }
    
    public int gcd(int a, int b) {
        if (b == 0) {
            return a;
        }
        
        return gcd(b, a % b);
    }
    
    public boolean check(int[] array,int n) {
        for (int a : array) {
            if (a % n == 0) return false; 
        }
        return true;
    }
}
