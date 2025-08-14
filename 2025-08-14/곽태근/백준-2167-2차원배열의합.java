import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        int[][] oldarr = new int[N+1][M+1];
        int[][] newarr = new int[N+1][M+1];

        for(int i = 1; i<N+1; i++){
            st = new StringTokenizer(br.readLine());
            for(int p = 1; p<M+1; p++) {
                oldarr[i][p] = Integer.parseInt(st.nextToken());
            }
        }

        for(int m = 1; m<N+1; m++){
            for(int l = 1; l<M+1; l++){
                newarr[m][l] = newarr[m-1][l] + newarr[m][l-1] - newarr[m-1][l-1] + oldarr[m][l];
            }
        }

        int K = Integer.parseInt(br.readLine());

        for(int u = 0; u<K; u++){
            st = new StringTokenizer(br.readLine());

            int x1 = Integer.parseInt(st.nextToken());
            int y1 = Integer.parseInt(st.nextToken());
            int x2 = Integer.parseInt(st.nextToken());
            int y2 = Integer.parseInt(st.nextToken());

            int result = newarr[x2][y2] - newarr[x1-1][y2] - newarr[x2][y1-1] + newarr[x1-1][y1-1];

            bw.write(result+"");
            bw.newLine();
            bw.flush();
        }
    }
}
