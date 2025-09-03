// n번째 피보나치 수를 1234567로 나눈 나머지 반환
class Solution {
    private static final int MOD = 1234567;

    public int solution(int n) {
        return fibMod(n);
    }

    private int fibMod(int n) {
        if (n == 0) return 0;   // F(0)
        if (n == 1) return 1;   // F(1)

        long a = 0; // F(0)
        long b = 1; // F(1)

        for (int i = 2; i <= n; i++) {
            long sum = (a + b) % MOD; // 합을 long으로 계산
            a = b;
            b = sum;
        }
        return (int)(b % MOD);
    }
}