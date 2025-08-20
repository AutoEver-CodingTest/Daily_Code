import java.io.*;
import java.util.*;

public class Main{

    public static void main(String[] args) throws IOException{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();

        int N = Integer.parseInt(br.readLine());

        PriorityQueue<Integer> pq = new PriorityQueue<>((o1, o2) -> {

            int first_abs = Math.abs(o1);
            int second_abs = Math.abs(o2);

            if(first_abs == second_abs){
                return o1 > o2 ? 1 : -1;
            }else{
                return first_abs - second_abs;
            }
        });

        for(int i = 0; i<N; i++){

            int temp = Integer.parseInt(br.readLine());
            
            if (temp > 0 || temp < 0){
                pq.add(temp);
            }else if(temp == 0){
                if(!pq.isEmpty()){
                    sb.append(pq.poll()+"\n");
                }else{
                    sb.append("0\n");
                }
            }
        }

        System.out.println(sb.toString());

    }
}
