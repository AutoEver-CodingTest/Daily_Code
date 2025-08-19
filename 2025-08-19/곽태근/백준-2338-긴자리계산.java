// https://www.acmicpc.net/problem/2338

import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        BigInteger a = new BigInteger(br.readLine());
        BigInteger b = new BigInteger(br.readLine());

        bw.write(a.add(b)+"");
        bw.newLine();
        bw.flush();
        bw.write(a.subtract(b)+"");
        bw.newLine();
        bw.flush();
        bw.write(a.multiply(b)+"");
        bw.newLine();
        bw.flush();
    }
}
