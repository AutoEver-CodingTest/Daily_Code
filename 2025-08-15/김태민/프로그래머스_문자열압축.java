class Solution {
    public int solution(String s) {
        int answer = Integer.MAX_VALUE;
        if(s.length()==1) return 1;
        
        
        for (int i = 1;i <= s.length() / 2; i++) {
            String str = s.substring(0,i);
            int count = 1;
            String reStr = "";
            for (int j = i; j <= s.length() - i;j+= i) {
                if (str.equals(s.substring(j,j+i))) {
                    count++;
                } else {
                    if (count > 1) {
                        reStr += "" + count;
                    } 
                    reStr += str;
                    str = s.substring(j,j+i);
                    count = 1;
                }
            }
            if (count > 1) {
                reStr += "" + count;
            } 
            reStr += str;

            int div = s.length()%i;
            
            answer = Math.min(answer,reStr.length() + div);
        }
        return answer;
    }
}
