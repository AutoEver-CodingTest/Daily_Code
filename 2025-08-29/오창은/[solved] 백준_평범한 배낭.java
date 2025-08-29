import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] parts = br.readLine().split(" ");

        int N = Integer.parseInt(parts[0]);
        int K = Integer.parseInt(parts[1]);
        
        int[] dp = new int[K+1];
        while (N-- > 0){
            parts = br.readLine().split(" ");
            int weight = Integer.parseInt(parts[0]);
            int value = Integer.parseInt(parts[1]);

            for (int i=K; i>=weight; i--) {
                dp[i] = Math.max(dp[i], dp[i-weight] + value);
            }
        }
        System.out.println(dp[K]);
    }
}