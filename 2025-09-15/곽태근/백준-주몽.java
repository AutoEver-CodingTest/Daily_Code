import java.io.*;
import java.util.*;

public class Main{

    public static void main(String[] args) throws IOException{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int N = Integer.parseInt(br.readLine());
        long M = Long.parseLong(br.readLine());

        int[] j = new int[N];

        StringTokenizer st = new StringTokenizer(br.readLine());

        for(int i = 0 ; i<N; i++){
            j[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(j);

        int start = 0;
        int end = N -1;
        int answer = 0;

        while(start < end){

            if(j[start] + j[end] < M){
                start++;
            }else if (j[start] + j[end] == M){
                answer++;
                start++;
                end--;
            }else{
                end --;
            }

        }

        bw.write(answer+"");
        bw.flush();

    }
}