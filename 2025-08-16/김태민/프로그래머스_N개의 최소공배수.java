class Solution {
    public int solution(int[] arr) {
        
        if (arr.length == 1) {
            return arr[0];
        }
        
        int result = getGCD(arr[0],arr[1]);
        int answer = (arr[0] * arr[1]) / result;
        
        for (int i = 2; i < arr.length;i++) {
            result = getGCD(answer,arr[i]);
            answer = answer * arr[i] / result;
        }
        
        
        
        return answer;
    }
    
    public static int getGCD(int num1, int num2) {
        if (num1 % num2 == 0) {
            return num2;
        }
        return getGCD(num2, num1%num2);
    }
}
