import java.util.*;
import java.io.*;
public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int num = Integer.parseInt(br.readLine());
        
        int[][] dp = new int[30][30];
        for(int i=0; i<30; i++){
            dp[i][i]=1;
            dp[i][0]=1;
        }
        for(int i=2; i<30; i++){
            for(int j=1; j<30; j++){
               dp[i][j]= dp[i-1][j-1]+dp[i-1][j];
            } 
        }
        
        for(int i=0; i<num; i++){
           st = new StringTokenizer(br.readLine()," ");
           int n = Integer.parseInt(st.nextToken());
           int k = Integer.parseInt(st.nextToken());
           
           System.out.println(dp[k][n]);
        }
    }
}
