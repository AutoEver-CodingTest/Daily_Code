class Solution {
    int MOD = 20170805;
    
    public int solution(int m, int n, int[][] cityMap) {
        // down[i][j]: (i,j)에서 아래쪽으로 나가는 경로의 수
        // right[i][j]: (i,j)에서 오른쪽으로 나가는 경로의 수
        int[][] down = new int[m + 1][n + 1];
        int[][] right = new int[m + 1][n + 1];
        
        // 시작점에서는 아래쪽과 오른쪽 모두로 갈 수 있음
        down[1][1] = 1;
        right[1][1] = 1;
        
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (i == 1 && j == 1) continue;
                
                int sign = cityMap[i-1][j-1];
                
                if (sign == 1) { // 벽 - 통과 불가
                    down[i][j] = 0;
                    right[i][j] = 0;
                } else if (sign == 0) { // 일반 도로 - 자유롭게 회전 가능
                    // 위에서 오는 경로들과 왼쪽에서 오는 경로들의 합
                    int total = (down[i-1][j] + right[i][j-1]) % MOD;
                    down[i][j] = total;
                    right[i][j] = total;
                } else { // sign == 2, 일방통행 - 방향 전환 불가
                    // 위에서 아래로 오는 경로는 계속 아래로
                    down[i][j] = down[i-1][j];
                    // 왼쪽에서 오른쪽으로 오는 경로는 계속 오른쪽으로
                    right[i][j] = right[i][j-1];
                }
            }
        }
        
        // 목표점에 도달하는 모든 경로의 수
        // 마지막 칸에서는 더 이상 이동할 필요가 없으므로
        // 위에서 오는 경로 + 왼쪽에서 오는 경로
        return (down[m-1][n] + right[m][n-1]) % MOD;
    }
}