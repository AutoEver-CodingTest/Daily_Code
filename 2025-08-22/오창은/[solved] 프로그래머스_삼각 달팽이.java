class Solution {
    public int[] solution(int n) {
        int total = n * (n + 1) / 2;
        int[][] map = new int[n][n];
        int[] answer = new int[total];

        int num = 1;
        int x = 0, y = 0;
       	int dir = 0;
        for (int len = n; len > 0; len--) {
            // 길이만큼 이동
            for (int i = 0; i < len; i++) {
                map[y][x] = num++;
              
                // 마지막 칸 전까지만 이동
                 if (i == len - 1) {
                    break;
                } 
                
                // 이동
                if (dir == 0) {
                    y++; // 아래
                } else if (dir == 1) {
                    x++; // 오른쪽
                } else {
                    x--; 
                    y--; // 대각선 위
                }
            }
            // 방향 전환 
       		dir = (dir + 1) % 3; 
           
            // 마지막 칸에서 방향 전환 후 1칸 이동
            if (len > 1) { // len이 1이면 이동할 필요 없음
                if (dir == 0) y++;
                else if (dir == 1) x++;
                else { x--; y--; }
            }
        }
        
        int idx = 0;
        for (int i=0; i<n; i++) {
            for (int j=0; j<=i; j++) {
                answer[idx++] = map[i][j];
            }
        }
   
        return answer;
    }
}