import java.util.*;
import java.io.*;
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine()," ");
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        int[] tree = new int[N];
        st = new StringTokenizer(br.readLine()," ");
        int max = 0;
        for(int i=0; i<N; i++){
            tree[i]=Integer.parseInt(st.nextToken());
            max=Math.max(max,tree[i]);
        }
        int left = 0; 
        int right = max;
        int result = 0;
        
        while(left<=right){
            long sum=0;
            int mid = (left+right)/2;
            for(int h : tree){
                if(h>mid) sum += h-mid;
            }
            
            if(sum>=M){
              result = mid;
              left = mid+1;
            }else{
                right = mid-1;
            }
        }
        System.out.print(result);
    }
}
