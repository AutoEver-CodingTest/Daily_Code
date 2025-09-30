import java.util.*;
import java.io.*;
public class Main {   
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        double result = (T / 2.0) * (T / 2.0); // 실수 나눗셈
        long answer = Math.round(result); // 소수 첫째 자리에서 반올림

        System.out.println(answer);
    }
}
