import java.io.*;

public class Main {
    static final int MAX = 30;
    static int[][] dp = new int[MAX + 1][MAX + 1];
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());

        for (int n=0; n <= MAX; n++) {
            dp[n][0] = 1;
            dp[n][n] = 1;

            for (int r=1; r < n; r++) {
                dp[n][r] = dp[n-1][r-1] + dp[n-1][r];
            }
        }

        while(T-- > 0) {
            String[] parts = br.readLine().split(" ");
            int N = Integer.parseInt(parts[0]);
            int M = Integer.parseInt(parts[1]);

            int result = dp[M][N];
            System.out.println(result);
        }
    }
}