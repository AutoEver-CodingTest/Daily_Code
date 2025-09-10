import java.util.*;
import java.io.*;
public class Main {   
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        PriorityQueue<Integer> pq = new PriorityQueue<>(
            (o1,o2)->{
                int absA = Math.abs(o1);
                int absB = Math.abs(o2);
                if(absA == absB) return Integer.compare(o1,o2);
                return Integer.compare(absA, absB);
            }
        );
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<N; i++){
            int num = Integer.parseInt(br.readLine());
            if(num == 0){
                if(pq.isEmpty()){
                    sb.append(0+"\n");
                }else{
                    sb.append(pq.poll()+"\n");
                }
            }else{
                pq.offer(num);
            }
        }
        System.out.println(sb);
    }
}

