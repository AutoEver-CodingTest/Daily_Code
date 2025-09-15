import java.util.*;

class Solution {
    public int[] solution(String msg) {
        Map<String, Integer> dict = new HashMap<>();
        int nextIdx = 1;
        for (char c = 'A'; c <= 'Z'; c++) dict.put(String.valueOf(c), nextIdx++);

        List<Integer> out = new ArrayList<>();
        int i = 0;
        while (i < msg.length()) {
            int j = i + 1;
            // 가장 긴 사전에 있는 문자열 찾기
            while (j <= msg.length() && dict.containsKey(msg.substring(i, j))) j++;
            // j가 한 칸 초과되어 나왔으므로, 실제 매칭은 j-1까지
            String w = msg.substring(i, j - 1);
            out.add(dict.get(w));
            // 다음 사전 항목 추가 (w + 다음 글자) — 다음 글자가 존재할 때만
            if (j <= msg.length()) {
                String wc = msg.substring(i, j);
                if (!dict.containsKey(wc)) dict.put(wc, nextIdx++);
            }
            i = j - 1;
        }

        return out.stream().mapToInt(Integer::intValue).toArray();
    }
}
