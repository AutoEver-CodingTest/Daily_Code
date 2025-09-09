import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[] A = new int[N];
        String[] parts = br.readLine().split(" ");
        for (int i=0; i<N; i++) {
            A[i] = Integer.parseInt(parts[i]);
        }
        int M = Integer.parseInt(br.readLine());
        int[] B = new int[M];
        parts = br.readLine().split(" ");
        for (int i=0; i<M; i++) {
            B[i] = Integer.parseInt(parts[i]);
        }

        List<Integer> answer = new ArrayList<>();

        int i = 0, j = 0;
        while (i < N && j < M) {
            int max = -1;
            for (int x=i; x<N; x++) {
                for (int y=j; y<M; y++) {
                    if (A[x] == B[y]) {
                        max = Math.max(max, A[x]);
                    }
                }
            }

            if (max == -1)
                break;

            answer.add(max);

            while (i < N && A[i] != max) i++;
            while (j < M && B[j] != max) j++;

            i++;
            j++;
        }

        System.out.println(answer.size());
        for (int res: answer) {
            System.out.print(res + " ");
        }
    }
}