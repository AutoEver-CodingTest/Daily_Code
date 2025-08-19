
import java.io.*;
import java.util.*;

public class Main{

    public static void main(String[] args) throws IOException{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        long[] sumarr = new long[N];
        long[] count = new long[M];

        long answer = 0;

        st = new StringTokenizer(br.readLine());

        sumarr[0] = Long.parseLong(st.nextToken());

        for(int i = 1; i<N; i++){
            sumarr[i] = Long.parseLong(st.nextToken()) + sumarr[i-1];
        }

        for(int p = 0; p<N; p++){
            int temp = (int)(sumarr[p]%M);

            if(temp == 0) answer++;

            count[temp]++;
        }

        for(int m = 0; m<M; m++){

            if(count[m]>1){
                answer = answer + (count[m] * (count[m] - 1) / 2);
            }
        }

        bw.write(answer+"");
        bw.flush();
    }
}
