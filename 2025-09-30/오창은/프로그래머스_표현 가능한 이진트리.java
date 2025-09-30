class Solution {
    public int[] solution(long[] numbers) {
        int n = numbers.length;
        int[] answer = new int[n];
      
        for (int i=0; i<n; i++) {
            String bin = Long.toBinaryString(numbers[i]);
            
            int len = 1; 
            while (len < bin.length()) {
                len = len * 2 + 1; 
            }
            while (bin.length() < len) {
                bin = "0" + bin;
            }
            
            if (isValid(bin)) answer[i] = 1;
            else answer[i] = 0;
        }
        
        return answer;
    }
  
    private boolean isValid(String s) {
        if (s.length() == 1) return true; 
        
        int mid = s.length() / 2; 
        char root = s.charAt(mid);
        
        String left = s.substring(0, mid);
        String right = s.substring(mid + 1);
        
       	if (root == '0') {
            if (left.contains("1") || right.contains("1")) return false; 
        }
        return isValid(left) && isValid(right);
    }
    
}
