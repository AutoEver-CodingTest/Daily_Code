import java.util.*;

class Solution {
    boolean isPrime(long num) {
        if (num < 2) return false;
        for (long i = 2; i * i <= num; i++) {
            if (num % i == 0) return false;
        }
        return true;
    }

    public int solution(int n, int k) {
        String numInBaseK = Integer.toString(n, k); // k진수 변환

        List<String> list = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        for (char ch : numInBaseK.toCharArray()) {
            if (ch == '0') {
                if (sb.length() > 0) {
                    String res = sb.toString();
                    long value = Long.parseLong(res); // ← long으로 변환
                    if (isPrime(value)) {
                        list.add(res);
                    }
                    sb = new StringBuilder();
                }
            } else {
                sb.append(ch);
            }
        }

        // 마지막 처리
        if (sb.length() > 0) {
            String res = sb.toString();
            long value = Long.parseLong(res);
            if (isPrime(value)) {
                list.add(res);
            }
        }

        return list.size();
    }
}