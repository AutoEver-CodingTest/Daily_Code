import java.util.*;
class Solution {
    public int[] solution(String[] genres, int[] plays) {
        ArrayList<Integer> answer = new ArrayList<>();
        
        Map<String,Integer> map = new HashMap<>();
        Map<String, Map<Integer,Integer>> music = new HashMap<>(); 
        
        for (int i = 0 ; i < plays.length ;i++) {
            Map<Integer,Integer> newMap = new HashMap<>();
            map.put(genres[i], map.getOrDefault(genres[i],0) + plays[i]);
            newMap.put(i,plays[i]);
            if (music.containsKey(genres[i])) {
                 music.get(genres[i]).put(i, plays[i]);
                
            } else {
               music.put(genres[i],newMap);
            }
            
        }
        
        List<String> keySet = new ArrayList<>(map.keySet());
        Collections.sort(keySet,(a,b) -> map.get(b) - map.get(a));
        
        for (String s : keySet) {
            Map<Integer,Integer> map2 = music.get(s);
            List<Integer> list = new ArrayList<>(map2.keySet());
            Collections.sort(list, (a,b) -> map2.get(b) - map2.get(a));
            answer.add(list.get(0));
            if (list.size() > 1) {
                answer.add(list.get(1));
            }
            
        }
        
        
        
        
        
        return answer.stream().mapToInt(i -> i).toArray();
    }
}
