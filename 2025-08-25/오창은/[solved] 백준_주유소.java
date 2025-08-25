import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


        int N = Integer.parseInt(br.readLine());

        long[] distances = new long[N-1];
        long[] costs = new long[N];

        String[] parts = br.readLine().split(" ");
        for (int i=0; i<N-1; i++) {
            distances[i] = Long.parseLong(parts[i]);
        }

        parts = br.readLine().split(" ");
        for (int i=0; i<N; i++) {
            costs[i] = Integer.parseLong(parts[i]);
        }

        long result = 0;
        long min = costs[0];
        for (int i=0; i<N-1; i++) {
            if (min > costs[i+1]) {
                result += min * distances[i];
                min = costs[i+1];
            } else {
                result += min * distances[i];
            }
        }
        // result += min * costs[N-2];
        System.out.println(result);
        /*
        for (int distance: distances) {
            System.out.print(distance + " ");
        }
         */
    }
}