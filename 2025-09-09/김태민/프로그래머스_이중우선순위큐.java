import java.util.*;
class Solution {
    public int[] solution(String[] operations) {
        int[] answer = {};
        
        PriorityQueue<Integer> max = new PriorityQueue<>((a,b) -> b - a);
        PriorityQueue<Integer> min = new PriorityQueue<>();
        
        Map<Integer,Integer> map = new HashMap<>();
        
        for (int i = 0 ;i < operations.length ; i++) {
            String[] str = operations[i].split(" ");
            if (str[0].equals("I")) {
                int n = Integer.parseInt(str[1]);
                map.put(n, map.getOrDefault(n,0) + 1);
                max.offer(n);
                min.offer(n);
            } else {
                if (str[1].equals("-1")) {
                    while(!min.isEmpty()) {
                        int a = min.peek();
                        if (map.getOrDefault(a,0) != 0) {
                            map.put(a,map.get(a) - 1);
                            min.poll();
                            break;
                        }
                        min.poll();
                    }
                    
                } else {
                    while(!max.isEmpty()) {
                       int a = max.peek();
                        if (map.getOrDefault(a,0) != 0) {
                            map.put(a,map.get(a) - 1);
                            max.poll();
                            break;
                        }    
                        max.poll();
                    }
                                  
                }   
            }
        }
        
        int result1 = 0;
        int result2 = 0;
        while(!max.isEmpty()) {
            int a  = max.poll();
            if(map.get(a) != 0) {
               result1 = a;
                break;
            } 
        }
        
        while(!min.isEmpty()) {
            int a  = min.poll();
            if(map.get(a) != 0) {
               result2 = a;
                break;
            } 
        }
        
        return new int[]{result1,result2};
    }
}
