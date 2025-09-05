import java.util.*;
import java.io.*;
public class Main { 
    static int[][] dp;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine()," ");
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        
        dp = new int[N+1][K+1];
        System.out.println(bc(N,K));
        
    }
    static int bc(int N, int K){
        if(dp[N][K]>0){
            return dp[N][K];
        }
        if(N == K || K == 0){
            return dp[N][K]=1;
        }
        return dp[N][K]=bc(N-1, K-1)+bc(N-1,K);
    }
}
