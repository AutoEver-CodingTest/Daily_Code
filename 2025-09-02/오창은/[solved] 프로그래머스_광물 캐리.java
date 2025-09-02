import java.util.*;

class Solution {
   // stress[곡괭이][광물]
    // 다이아곡괭이, 철곡괭이, 돌곡괭이 순서
    int[][] stress = {
        {1, 1, 1}, 
        {5, 1, 1},
        {25, 5, 1}
    };
    
    public int solution(int[] picks, String[] minerals) {
        int answer = 0;
       
        int n = (minerals.length + 5 - 1) / 5;   // 총 구간 수
        int totalPick = picks[0] + picks[1] + picks[2];
        n = Math.min(n, totalPick);             // 곡괭이 수 이상은 의미 없음

        int[] ranges = new int[n];              // 구간별 가중치
       
        // 구간 가중치 계산
        for (int i=0; i<minerals.length; i++) {
            int value = 0;
            String mineral = minerals[i];
            if (mineral.equals("diamond")) value = 25;    
            else if (mineral.equals("iron")) value = 5; 
            else value = 1; 
            if (i / 5 < n) ranges[i / 5] += value; // n개까지만 반영
        }
      
        // (구간 번호, 가중치) 저장 후 정렬
        List<int[]> list = new ArrayList<>();
        for (int i=0; i<n; i++) {
            list.add(new int[] { i, ranges[i] });
        }
        Collections.sort(list, (a, b) -> b[1] - a[1]);
        
        // 정렬된 구간 순서대로 곡괭이 배정
        for (int idx = 0; idx < n; idx++) {
            int section = list.get(idx)[0];
            int startIdx = section * 5;
            int endIdx = Math.min(startIdx + 5, minerals.length);

            // 남은 곡괭이 중 가장 좋은 것 선택
            int pickType;
            if (picks[0] > 0) { pickType = 0; picks[0]--; }
            else if (picks[1] > 0) { pickType = 1; picks[1]--; }
            else { pickType = 2; picks[2]--; }

            for (int j = startIdx; j < endIdx; j++) {
                int m;
                if (minerals[j].equals("diamond")) m = 0;
                else if (minerals[j].equals("iron")) m = 1;
                else m = 2;
                answer += stress[pickType][m];
            }
        }
        
        return answer;
    }
}