import java.util.*;
import java.io.*;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        String[] parts = br.readLine().split(" ");
        int[] A = new int[N]; 
        for (int i=0; i<N; i++) {
            A[i] = Integer.parseInt(parts[i]);
        }

        int[] LIS = new int[N];
        for (int i=0; i<N; i++) {
            LIS[i] = 1;
            for (int j=0; j<i; j++) {
                if (A[j] < A[i] && LIS[i] < LIS[j] + 1) {
                    LIS[i] = LIS[j] + 1;
                }
            }
        }

        int[] LDS = new int[N]; 
        for (int i=N-1; i>=0; i--) {
            LDS[i] = 1; 
            for (int j=N-1; j>i; j--) {
                if (A[j] < A[i] && LDS[i] < LDS[j] + 1) {
                    LDS[i] = LDS[i] + 1;
                }   
            }
        }

        int maxLength = 0; 
        for (int i=0; i<N; i++) {
            maxLength = Math.max(maxLength, LIS[i] + LDS[i] - 1);
        }

        System.out.print(maxLength);     
    }
}