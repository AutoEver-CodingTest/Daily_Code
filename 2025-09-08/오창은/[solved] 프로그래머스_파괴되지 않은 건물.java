class Solution {
   	int N, M; 
    int[][] diff;
    public int solution(int[][] board, int[][] skill) {
        N = board.length;
        M = board[0].length;
      
        diff = new int[N + 1][M + 1];
      	
        for (int[] s: skill) {
            int type = s[0];
            int r1 = s[1], c1 = s[2];
            int r2 = s[3], c2 = s[4];
            int degree = s[5];
            
            degree = type == 1 ? -degree : degree;
            
            diff[r1][c1] += degree;
            diff[r1][c2 + 1] -= degree;
            diff[r2 + 1][c1] -= degree;
            diff[r2 + 1][c2 + 1] += degree;
        }
                   
        // 열 누적합 
        for (int i=0; i<N; i++) {
            for (int j=1; j<M; j++) { 
				diff[i][j] += diff[i][j-1];	
            }
        }
       
        // 행 누적합 
        for (int j=0; j<M; j++) {
            for (int i=1; i<N; i++) { 
				diff[i][j] += diff[i-1][j];	
            }
        }

        for (int i=0; i<N; i++) {
            for (int j=0; j<M; j++) { 
				board[i][j] += diff[i][j];	
            }
        }
       
        int count = 0;
        for (int i=0; i<N; i++) {
            for (int j=0; j<M; j++) { 
				if (board[i][j] > 0) {
                    count++;
                }	
            }
        }
        /*
        for (int i=0; i<N; i++) {
            for (int j=0; j<M; j++) { 
                System.out.print(board[i][j] + " ");
			}
            System.out.println();
        }
        */
        
        return count;
    }
}