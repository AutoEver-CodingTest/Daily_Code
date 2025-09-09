import java.util.*;

class Solution {
   	int M, N; 
    int size;
    public boolean solution(int[][] key, int[][] lock) {      	
        M = key.length; 
      	N = lock.length; 
      	size = N + (M - 1) * 2;
        int[][] newLock = new int[size][size];
        
        for (int i=M-1; i<M+N-1; i++) {
        	for (int j=M-1; j<M+N-1; j++) {
           		newLock[i][j] = lock[i-M+1][j-M+1];
            }
        }
        /*
        for (int i=0; i<size; i++) {
        	for (int j=0; j<size; j++) {
           		System.out.print(newLock[i][j] + " "); 
            }
            System.out.println(); 
        }
        */
        for (int k=0; k<4; k++) {
            key = rotate(key);
            
            for (int x=0; x<M-1+N; x++) {
                for (int y=0; y<M-1+N; y++) {
                    if (check(newLock, key, x, y)) {
                        return true;
                    }
                }
            }
            /*
            for (int i=0; i<N; i++) {
                for (int j=0; j<N; j++) {
                    System.out.print(key[i][j] + " "); 
                }
                System.out.println(); 
            }
            */
            // System.out.println(); 
        }       
        return false;
    }
    
    public int[][] rotate(int[][] arr) {
        int[][] result = new int[M][M];
        for (int row=0; row<M; row++) {
            for (int col=0; col<M; col++) {
                result[col][M-row-1] = arr[row][col];
            }
        }
        return result;
    }
    
    public boolean check(int[][] newLock, int[][] key, int x, int y) {
        int[][] temp = new int[size][size]; 
        
        for (int i=0; i<size; i++) {
            temp[i] = Arrays.copyOf(newLock[i], size);
        }

        for (int i=0; i<M; i++) {
            for (int j=0; j<M; j++) {
                temp[x + i][y + j] += key[i][j];
            }
        }
        
        /*
        for (int i=0; i<size; i++) {
            for (int j=0; j<size; j++) {
                System.out.print(temp[i][j] + " "); 
            }
            System.out.println(); 
        }
        System.out.println(); 
        */
        
        for (int i=M-1; i<M+N-1; i++) {
            for (int j=M-1; j<M+N-1; j++) {
                if (temp[i][j] != 1)
                    return false; 
            }
        }
        return true; 
    }
}