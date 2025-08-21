class Solution {
    public int[] solution(int n) {
        int[] answer = new int [n*(n+1)/2];
        int [][] array = new int[n][n];
        int num = 1;
        int x = -1;
        int y = 0;
        for (int i = 0;i < n;i++) {
            for (int j = i; j < n; j++) {
                if (i % 3 == 0) {
                    x++;
                } else if(i % 3 == 1) {
                    y++;
                } else {
                    x--;
                    y--;
                }
                array[x][y] = num++;
            }
        }
        int idx = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= i ; j++) {
                answer[idx++] = array[i][j];
            }
        }
        return answer;
    
}}
