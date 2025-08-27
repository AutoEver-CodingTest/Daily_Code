class Solution {
    public int solution(int n, int[][] results) {
        boolean[][] graph = new boolean[n+1][n+1];
        
        for (int[] result: results) {
            int win = result[0];
            int lose = result[1];
        	
            graph[win][lose] = true;
        }
        
        for (int k=1; k<=n; k++) { // 경유 선수 
            for (int i=1; i<=n; i++) { // 출발 선수 
                for (int j=1; j<=n; j++) { // 도착 선수
                    if (graph[i][k] && graph[k][j]) {
                        graph[i][j] = true;
                    }
                }
            }
        }
        
        int answer = 0; 
        for (int i=1; i<=n; i++) {
            int count = 0;
            for (int j=1; j<=n; j++) {
                if (i == j) continue; 
                if (graph[i][j] || graph[j][i]) {
                    count++;
                }
            }
            if (count == n-1) {
                answer++;
            }
        }
        
        return answer;
    }
}