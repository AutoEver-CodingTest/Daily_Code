import java.util.*;
class Solution {
    static int[] max; 
    static HashMap<String,Integer> menu;
    static String str;
    public String[] solution(String[] orders, int[] course) {
        ArrayList<String> answer = new ArrayList<>();
        max = new int[course.length];
        menu = new HashMap<>();
        
        for(int i=0; i<orders.length; i++){
            char[] a = orders[i].toCharArray();
            Arrays.sort(a);
            str = String.copyValueOf(a);
            
            for(int j=0; j<course.length; j++){
                combo(0,0,"",j, course[j]);
            }
        }
        
        for(String s: menu.keySet()){
            for(int i=0; i<course.length; i++){
                if(course[i]==s.length() && max[i] != 1 && max[i]==menu.get(s)) answer.add(s);
            }
        }
        
        String[] result = new String[answer.size()];
        for(int i=0; i<answer.size(); i++){
            result[i]=answer.get(i);
        }
        Arrays.sort(result);
        return result;
    }
    static void combo(int cur, int cnt, String s, int j, int N){
        if(cnt == N){
            menu.put(s, menu.containsKey(s) ? menu.get(s)+1 :1);
            max[j] = Math.max(max[j], menu.get(s));
            return;
        }
        for(int i=cur; i<str.length(); i++){
            combo(i+1, cnt+1, s+str.charAt(i), j,N);
        }
    }
}
