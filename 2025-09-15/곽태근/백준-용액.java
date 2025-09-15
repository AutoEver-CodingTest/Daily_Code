import java.io.*;
import java.util.*;
import java.lang.*;

public class Main{

    public static void main(String[] args) throws IOException{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int N = Integer.parseInt(br.readLine());

        StringTokenizer st = new StringTokenizer(br.readLine());

        long[] arr = new long[N];

        for(int i = 0; i<N; i++){
            arr[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(arr);

        int start = 0;
        int end = N-1;

        long close_start = 0;
        long close_end = 0;

        long total = Long.MAX_VALUE;

        while(start < end){

            if ( Math.abs(arr[start] + arr[end]) < total ){

                total = Math.abs(arr[start] + arr[end]);
                close_start = arr[start];
                close_end = arr[end];
                if (total == 0) break;
            }

            if ( (arr[start] + arr[end]) < 0 ){
                start++;
            }else if ((arr[start] + arr[end]) > 0) {
                end--;
            }
            else {
                break;
            }
        }

        bw.write(close_start + " " + close_end);
        bw.flush();

    }

}