import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static int[] arr;
    static int[] sum;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        arr = new int[n];
        sum = new int[n + 1];
        String[] parts = br.readLine().split(" ");
        for (int i=0; i<n; i++) {
            arr[i] = Integer.parseInt(parts[i]);
        }

        for (int i=1; i<=n; i++) {
            sum[i] = sum[i-1] + arr[i-1];
        }

        int max = Integer.MIN_VALUE;
        int minPrefix = 0;
        for (int i = 1; i <= n; i++) {
            max = Math.max(max, sum[i] - minPrefix);
            minPrefix = Math.min(minPrefix, sum[i]);
        }

        System.out.println(max);

        /*
        for (int num: sum) {
            System.out.print(num + " ");
        }
         */
        System.out.println();
    }
}