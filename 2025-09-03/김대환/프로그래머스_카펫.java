import java.util.*;

class Solution {
    public int[] solution(int brown, int yellow) {
        // 정답을 담을 객체
        int[] answer = new int[2];
        int width;
        int height;

        // 약수의 조합 찾기
        List<List<Integer>> pairs = new ArrayList<>();

        for (int i = yellow; i >= 1; i--) {
            if (yellow % i == 0) {
                int a = i;
                int b = yellow/i;

                if (a < b) break;
                pairs.add(Arrays.asList(a, b));
            }
        }

        for (List<Integer> ele : pairs) {
            int c = ele.get(0);
            int d = ele.get(1);
            int result = 2 * c + 2 * d + 4;
            if (result == brown) {
                answer[0] = c + 2;
                answer[1] = d + 2;
            }
        }
        return answer;
    }
}