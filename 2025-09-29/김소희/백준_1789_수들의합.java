import java.util.*;
import java.io.*;
public class Main {   
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        long S = Long.parseLong(br.readLine());
        
        long sum=0;
        int cnt=0;
        for(int i=1; ; i++){
            if(sum>S) break;
            sum += i;
            cnt++;
        }
        System.out.print(cnt-1);
    }
}
