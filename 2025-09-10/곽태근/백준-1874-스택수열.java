
import java.io.*;
import java.util.*;
import java.lang.*;

public class Main{

    public static void main(String[] args) throws IOException{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringBuilder sb = new StringBuilder();

        int n = Integer.parseInt(br.readLine());

        int[] temp = new int[n];

        for(int i = 0; i<n; i++){
            temp[i] = Integer.parseInt(br.readLine());
        }

        Stack<Integer> stack = new Stack<>();

        int num =1;
        boolean result = true;

        for (int p =0; p<temp.length; p++){
            int now = temp[p];

            if(now>=num){
                while(now>=num){
                    stack.push(num++);
                    sb.append("+\n");
                }
                stack.pop();
                sb.append("-\n");
            }
            else{
                int nn = stack.pop();
                if (nn > now){
                    System.out.println("NO");
                    result = false;
                    break;
                }
                else{
                    sb.append("-\n");
                }
            }
        }

        if(result){
            bw.write(sb.toString());
            bw.flush();
        }
    }
}
