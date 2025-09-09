class Solution {
    public int[][] solution(int n) {
        int[][] answer = new int[n][n];

        int top = 0;
        int bottom = n - 1;
        int left = 0;
        int right = n - 1;
        int val = 1;

        while (top <= bottom && left <= right) {
            // 상단 행: left -> right
            for (int j = left; j <= right; j ++) {
                answer[top][j] = val++;
            }
            top++;

            // 오른쪽 열: top -> bottom
            for (int i = top; i <= bottom; i++) {
                answer[i][right] = val++;
            }

            right--;

            // 하단 행: right -> left
            if (top <= bottom) {
                for (int j = right; j >= left; j--) {
                    answer[bottom][j] = val++;
                }
                bottom--;
            }

            // 왼쪽 열: bottom -> top
            if (left <= right) {
                for (int i = bottom; i >= top; i--) {
                    answer[i][left] = val++;
                }
                left++;
            }


        }
        return answer;
    }
}