import java.util.*;

class Solution {
    public int solution(int k, int[] tangerine) {
        // 각 요소가 몇개가 있는지 count
        Map<Integer, Integer> freqMap = new HashMap<>();

        for (int num : tangerine) {
            freqMap.merge(num, 1, Integer::sum);
        }

        Set<Integer> unique = freqMap.keySet();
        List<Integer> counts = new ArrayList<>(freqMap.values());

        counts.sort(Comparator.reverseOrder());

        int sum = 0;
        int answer = 0;

        for (int c: counts) {
            sum += c;
            answer++;
            if (sum >= k) break;
        }
        return answer;
    }
}