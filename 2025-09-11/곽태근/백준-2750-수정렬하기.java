import java.io.*;
import java.util.*;

public class Main{

    public static void main(String[] args) throws IOException{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int N = Integer.parseInt(br.readLine());

        int[] arr = new int[N];

        for(int i = 0 ; i<N; i++){
            int temp = Integer.parseInt(br.readLine());
            arr[i] = temp;
        }

        for(int i = 0; i<N; i++){
            for(int j = 1; j<N; j++){

                if (arr[j-1] > arr[j]){
                int temp1 = arr[j-1];

                arr[j-1] = arr[j];
                arr[j] = temp1;
                }
            }
            
        }

        for(int i = 0; i<N; i++){
            bw.write(arr[i]+"\n");
        }

        bw.flush();
    }
}
