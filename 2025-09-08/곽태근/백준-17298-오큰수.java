import java.io.*;
import java.util.*;

public class Main{

    public static void main(String[] args) throws IOException{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int n = Integer.parseInt(br.readLine());
        int[] temp = new int[n];
        int[] answer = new int[n];

        Stack<Integer> stack = new Stack<>();

        stack.push(0);

        StringTokenizer st = new StringTokenizer(br.readLine());

        for(int i = 0; i < n; i++){
            temp[i] = Integer.parseInt(st.nextToken());
        }

        for(int p = 1; p<n; p++){

            while(!stack.isEmpty() && temp[stack.peek()] < temp[p]){

                answer[stack.pop()] = temp[p];
                
            }
            stack.push(p);
        }

        while(!stack.isEmpty()){
            answer[stack.pop()] = -1;
        }

        for(int i = 0; i<n; i++){
            bw.write(answer[i] + " ");
        }
        bw.flush();
    }
}
