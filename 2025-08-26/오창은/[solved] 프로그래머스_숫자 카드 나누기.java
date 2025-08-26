class Solution {
    public int solution(int[] arrayA, int[] arrayB) {
        int gcdA = gcdArray(arrayA);
        int gcdB = gcdArray(arrayB);
        
        int max = 0;
        if (check(gcdA, arrayB)) {
            max = gcdA;
        }
        if (check(gcdB, arrayA)) {
            max = Math.max(max, gcdB);
        }
        
        return max;
    }
    
    public int gcd(int a, int b) {
        while (b != 0) {
           	int temp = a % b;
            a = b; 
            b = temp;
        }
        return a;
    }
    
    public int gcdArray(int[] arr) {
        int result = arr[0];
        for (int i=1; i<arr.length; i++) {
            result = gcd(result, arr[i]);
        }
        return result;
    }
    
    public boolean check(int x, int[] arr) {
        for (int num: arr) {
            if (num % x == 0) {
                return false;
            }
        }
        return true;
    }
}