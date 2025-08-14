import java.io.*;
import java.util.*;

public class Main{

    public static void main(String[] args) throws IOException{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int N = Integer.parseInt(br.readLine());

        int start_idx = 1;
        int end_idx = 1;
        int count = 1;
        int sum = 1;

        while(end_idx != N){

            if(sum == N){
                count++;
                end_idx++;
                sum = sum + end_idx;
            }else if(sum>N){
                sum = sum - start_idx;
                start_idx++;
            }else{
                end_idx++;
                sum = sum + end_idx;
            }
        }

        bw.write(count+"");
        bw.flush();
    }
}
