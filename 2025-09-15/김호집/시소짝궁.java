import java.util.*;

class Solution {
    public long solution(int[] weights) {
        // 몸무게의 빈도를 센다
        Map<Integer, Long> cnt = new HashMap<>();
        for (int w : weights) cnt.merge(w, 1L, Long::sum);

        // 고유 몸무게를 오름차순으로 순회 (중복 카운트 방지)
        List<Integer> uniq = new ArrayList<>(cnt.keySet());
        Collections.sort(uniq);

        long ans = 0;

        for (int w : uniq) {
            long c = cnt.get(w);

            // 1) 같은 몸무게끼리 (1:1)
            if (c >= 2) ans += c * (c - 1) / 2;

            // 2) 서로 다른 몸무게 쌍 (작은 쪽이 w라고 가정하고만 센다)
