import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static char[][] canvas;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        int height = N;
        int weight = 2 * N - 1;
        canvas = new char[height][weight];

        for (int i=0; i<height; i++) {
            Arrays.fill(canvas[i], ' ');
        }

        draw(0, N-1, N);

        StringBuilder sb = new StringBuilder();
        for (int i=0; i<height; i++) {
            sb.append(canvas[i]).append('\n');
        }
        System.out.print(sb.toString());
    }

    public static void draw(int row, int col, int size) {
        if (size == 3) {
            canvas[row][col] = '*';
            canvas[row+1][col-1] = '*';
            canvas[row+1][col+1] = '*';

            for (int i=-2; i<=2; i++) {
                canvas[row+2][col+i] = '*';
            }
            return;
        }

        int half = size / 2;
        draw(row, col, half);
        draw(row + half, col - half, half);
        draw(row + half, col + half, half);
    }
}