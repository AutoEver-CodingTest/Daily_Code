class Solution {
    public String[] solution(String[] s) {
        int n = s.length;
        String[] answer = new String[n];
        
        for (int i=0; i<n; i++) {
            answer[i] = move110(s[i]);
        }
        
        return answer;
    }
    
    public String move110(String str) {
        StringBuilder sb = new StringBuilder();
        int count110 = 0;
        
        for (char ch: str.toCharArray()) {
            sb.append(ch);
            
            int len = sb.length();
            if (sb.length() >= 3) {
                if (sb.charAt(len-3) == '1' &&
                    sb.charAt(len-2) == '1' && 
                    sb.charAt(len-1) == '0') {
                    sb.delete(len-3, len);
                    
                    count110++;
                }
            }
        }
        
        String remaining = sb.toString();
        int lastZeroIndex = remaining.lastIndexOf('0') + 1;
        // System.out.println(lastZeroIndex);
        
        StringBuilder result = new StringBuilder();
        result.append(remaining.substring(0, lastZeroIndex));
        for (int i=0; i<count110; i++) {
        	result.append("110");    
        }
        result.append(remaining.substring(lastZeroIndex));
        
        return result.toString();
    }
}
