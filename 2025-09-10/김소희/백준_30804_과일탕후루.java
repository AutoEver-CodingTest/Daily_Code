import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());      
        int[] fruit = new int[N];
        for(int i=0; i<N; i++) {
            fruit[i] = Integer.parseInt(st.nextToken());
        }

        Map<Integer, Integer> map = new HashMap<>();
        int left = 0;
        int result = 0;

        for(int right=0; right<N; right++) {
            map.put(fruit[right], map.getOrDefault(fruit[right], 0) + 1);      
            // 과일 종류가 3 종류일 때, 2 종류가 될 때까지 map에서 제거
            while(map.size() > 2) {
                // 앞에서부터 순차적으로 과일을 제거
                map.put(fruit[left], map.get(fruit[left])-1);
                if(map.get(fruit[left]) == 0) {
                    map.remove(fruit[left]);
                }
                left++;
            }
            result = Math.max(result, right-left+1);
        }

        System.out.println(result);
    }
}
