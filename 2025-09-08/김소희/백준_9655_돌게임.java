import java.util.*;
import java.io.*;
public class Main {   
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int M = Integer.parseInt(br.readLine());
        
        boolean[] dp = new boolean[M+1];
        dp[1]=true;
        if(M>=2) dp[2]=false;
        if(M>=3) dp[3]=true;
        
        for(int i=4; i<=M; i++){
            dp[i]= !dp[i-1] || !dp[i-3];
        }
        System.out.println(dp[M]? "SK" : "CY");
    }
}
