import java.util.*;
import java.io.*;
public class Main {  
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        int N = Integer.parseInt(br.readLine());
        
        int[] A = new int[N+1];
        
        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(br.readLine()," ");
        for(int i=1; i<=N; i++){
            A[i]=Integer.parseInt(st.nextToken());
        }
        int min=Integer.MAX_VALUE;
        int max=0;
        for(int i=1; i<=N; i++){
            min = Math.min(A[i],min);
            max =Math.max(max, A[i]-min);
            sb.append(max+" ");
        }
        System.out.println(sb);
    }
}
