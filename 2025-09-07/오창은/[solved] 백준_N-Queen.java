import java.io.*;

public class Main {
    static int N;
    static int[] board;
    static int count = 0;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        board = new int[N];

        backtrack(0);
        System.out.println(count);
    }

    public static void backtrack(int row) {
        if (row == N) {
            count++;
            return;
        }

        for (int col = 0; col < N; col++) {
            if (isSafe(row, col)) {
                board[row] = col;
                backtrack(row + 1);
            }
        }
    }

    public static boolean isSafe(int row, int col) {
        for (int prev = 0; prev < row; prev++) {
            if (board[prev] == col) return false;
            if (Math.abs(row - prev) == Math.abs(col - board[prev])) return false;
        }
        return true;
    }
}