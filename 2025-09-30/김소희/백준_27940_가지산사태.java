import java.util.*;
import java.io.*;
public class Main {
    static int[] farm;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine()," ");
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        long K = Integer.parseInt(st.nextToken());

        long firstFloor =0;
        boolean check = true;

        for(int i=0; i<M; i++){
            st = new StringTokenizer(br.readLine()," ");
            int ti = Integer.parseInt(st.nextToken());
            int ri = Integer.parseInt(st.nextToken());

            if(check){ //1층이 반드시 먼저 무너짐
                firstFloor += ri;
                if(firstFloor>K){
                    System.out.print(i+1+" "+1);
                    check=false;
                }
            }
        }
        if (check) {
            System.out.print(-1);
        }
    }
}
