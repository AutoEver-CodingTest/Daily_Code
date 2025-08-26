import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());
        while (T-- > 0) {
            int N = Integer.parseInt(br.readLine());
            int[][] sticker = new int[2][N];
            int[][] dp = new int[3][N];

            for (int i = 0; i < 2; i++) {
                String[] parts = br.readLine().split(" ");
                for (int j = 0; j < N; j++) {
                    sticker[i][j] = Integer.parseInt(parts[j]);
                }
            }

            // dp 2행은 아무 것도 선택하지 않았을 때의 최대값
            dp[0][0] = sticker[0][0];
            dp[1][0] = sticker[1][0];
            dp[2][0] = 0;
            for (int i=1; i<N; i++) {
                dp[0][i] = Math.max(dp[1][i-1], dp[2][i-1]) + sticker[0][i];
                dp[1][i] = Math.max(dp[0][i-1], dp[2][i-1]) + sticker[1][i];
                dp[2][i] = Math.max(Math.max(dp[0][i-1], dp[1][i-1]),dp[2][i-1]);
            }

            int dp0 = dp[0][N-1];
            int dp1 = dp[1][N-1];
            int dp2 = dp[2][N-1];

            int res = Math.max(Math.max(dp0, dp1), dp2);
            sb.append(res + "\n");
        }
        /*
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
         */
        System.out.print(sb.toString());
    }
}