class Solution {
    public long solution(int r1, int r2) {
        long answer = 0;

        // x=1~r2까지 세기 (사분면 안쪽만)
        for (int x = 1; x <= r2; x++) {
            long maxY = (long)Math.floor(Math.sqrt((long)r2 * r2 - (long)x * x));
            long minY = 0;
            if (x < r1) {
                minY = (long)Math.ceil(Math.sqrt((long)r1 * r1 - (long)x * x));
            }
            answer += (maxY - minY + 1);
        }

        // 4사분면 대칭
        answer *= 4;

        return answer;
    }
}
