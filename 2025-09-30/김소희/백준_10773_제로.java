import java.util.*;
import java.io.*;
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        LinkedList<Integer> list = new LinkedList<>();
        for(int i=0; i<N; i++){
            int num = Integer.parseInt(br.readLine());
            if(num==0){
                list.removeLast(); continue;
            }
            list.add(num);
        }
        int sum=0;
        for(int i :list){
            sum+= i;
        }
        System.out.print(sum);
    }
}
