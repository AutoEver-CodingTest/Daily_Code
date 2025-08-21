class Solution {
    public int solution(int storey) {
        int answer = 0;
        
        while (storey > 0) {
           	int num10 = storey / 10; 
            int num0 = storey % 10;
            if (num0 > 5) {
                num10 += 1;
				answer += 10 - num0;
            } else if (num0 < 5) {
                answer += num0;
            } else {
                if (num10 % 10 >= 5) {
                    num10 += 1;
                }
                answer += num0; // 숫자 5
            }
           	storey = num10;
        }
        
        return answer;
    }
}