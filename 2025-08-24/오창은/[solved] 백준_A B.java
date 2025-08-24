import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] parts = br.readLine().split(" ");

        int A = Integer.parseInt(parts[0]);
        int B = Integer.parseInt(parts[1]);

        int count = 1;
        while (A < B) {
            if (B % 10 == 1) {
                B /= 10;
            } else if (B % 2 == 0) {
                B /= 2;
            } else {
                break;
            }
            count ++;

            //System.out.println(B);
        }

        int res = (A == B) ? count : -1;
        System.out.println(res);
    }
}