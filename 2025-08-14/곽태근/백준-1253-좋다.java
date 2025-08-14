
import java.io.*;
import java.util.*;

public class Main{

    public static void main(String[] args) throws IOException{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int N = Integer.parseInt(br.readLine());
        int answer = 0;
        long[] arr = new long[N];

        StringTokenizer st = new StringTokenizer(br.readLine());

        for(int i = 0; i<N; i++){
            arr[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(arr);

        for(int k = 0; k<N; k++){

            long find = arr[k];
            int start = 0;
            int end = N-1;

            while(start < end){

                if (arr[start] + arr[end] == find){

                    if(start != k && end != k){
                        answer++;
                        break;
                    }else if( start == k){
                        start++;
                    }else if( end == k){
                        end--;
                    }
                } else if(arr[start] + arr[end] > find){
                    end--;
                }else if (arr[start] + arr[end] < find){
                    start++;
                }
            }
        }

        bw.write(answer+"");
        bw.flush();
    }

}
