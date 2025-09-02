import java.util.*;
import java.io.*;
public class Main {
    
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine());
        int[][] array = new int[t][2];
        for (int i = 0; i < t; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            array[i][0] = a * 100 + b;
            array[i][1] = c * 100 + d;

        }

        Arrays.sort(array,(a,b) -> a[0] - b[0]);

        int cur = 301;
        int end = 1130;
        
        int count = 0;
        int idx = 0;
        while(cur <= end) {
            int max = cur;
            boolean found = false;
            while(idx < t && cur >= array[idx][0]) {
                found =true;
                if (max < array[idx][1]) {
                    max = array[idx][1];
                }
                idx++;
            }
            if (!found) {
                System.out.println(0);
                return;
            }
            count++;
            cur = max;
        }
        System.out.println(count);
    }}
