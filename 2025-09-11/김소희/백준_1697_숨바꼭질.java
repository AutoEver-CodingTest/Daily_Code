import java.util.*;
import java.io.*;

public class Main{
    public static void main(String[] args)throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine()," ");
        int X = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        if(X==K){
           System.out.println(0);
           return;
        } 
        
        Queue<Integer> q = new LinkedList<>();
        boolean[] visited = new boolean[100001];
        
        int cnt=0;
        q.offer(X);
        visited[X]=true;
        while(true){
            int size = q.size();
            cnt++;
            for(int i=0; i<size; i++){
               int m = q.poll();
               if(m+1 ==K || m-1 ==K || m*2 ==K){
                   System.out.println(cnt);
                   return;
               }
               if(m+1<=100000 && !visited[m+1]){
                   q.offer(m+1);
                   visited[m+1]=true;
               }
               if(m-1>=0 && !visited[m-1]){
                   q.offer(m-1);
                   visited[m-1]=true;
               }
               if(m*2<=100000 && !visited[m*2]){
                   q.offer(m*2); 
                   visited[m*2]=true;
               } 
            } 
        }
    }
}
