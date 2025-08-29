class Solution {
    public int solution(int storey) {
        int answer = 0;
        
        while(storey > 0) {
            int n = storey % 10;
            storey /= 10;
            if (n > 5) {
                answer += 10 - n;
                storey++;
            } else if (n < 5) {
                answer += n;
            } else if (storey % 10 >= 5) {
                answer += 10 - n;
                storey++;
            } else {
                answer += n;
            }
        }
        return answer;
    }
}
