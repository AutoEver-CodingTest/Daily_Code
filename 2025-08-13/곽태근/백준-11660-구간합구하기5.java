
import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        int[][] origin = new int[N+1][N+1];
        int[][] newarr = new int[N+1][N+1];

        // 기존 배열 생성
        for(int i = 1; i<N+1; i++){
            st = new StringTokenizer(br.readLine());
            for(int p = 1; p<N+1; p++){

                origin[i][p] = Integer.parseInt(st.nextToken());
            }
        }

        // 합 배열 생성
        for(int pp=1; pp<N+1; pp++){
            for(int mm=1; mm<N+1; mm++){
                newarr[pp][mm] = newarr[pp-1][mm] + newarr[pp][mm-1] - newarr[pp-1][mm-1] + origin[pp][mm];
            }
        }

        // 결과값 반환
        for(int ii=0; ii<M; ii++){
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
