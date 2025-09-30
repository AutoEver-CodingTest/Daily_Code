import java.util.*;
import java.io.*;
public class Main {   
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine()," ");
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());
        
        int[] arr = {a,b,c};
        Arrays.sort(arr);
        
        int x = arr[0]; int y=arr[1]; int z = arr[2];
        int perimeter;
        
        if(x+y>z){//삼각형 가능
            perimeter=x+y+z;
        }else{
            perimeter=(x+y)*2-1;
        }
        System.out.println(perimeter);
    }
}
