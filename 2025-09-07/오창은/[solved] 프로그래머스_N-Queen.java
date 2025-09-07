class Solution {
    int[] board;
    int count = 0;
    public int solution(int n) {
        board = new int[n];
        backtrack(0, n);
        
        return count;
    }
    public void backtrack(int row, int n) {
        if (row == n) {
            count++;
            return;
        }
        
        for (int col = 0; col < n; col++) {
            if (isSafe(row, col)) {
                board[row] = col;
                backtrack(row + 1, n);
            }
        }
    }
    
    public boolean isSafe(int row, int col) {
     	for (int prev = 0; prev < row; prev++) {
            if (board[prev] == col) return false;
            if (Math.abs(row - prev) == Math.abs(col - board[prev])) return false;
        } 
        
        return true;
    }
}