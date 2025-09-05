import java.util.*;

class Solution {
    public int[] solution(String msg) {        
        // A부터 Z까지 map에 넣기 
        Map<String, Integer> dictionary = new HashMap<>();
        for (int i=0; i<26; i++) {
            dictionary.put(Character.toString(i + 'A'), i + 1);
        }
        
        int newWordIndex = 27;
        int targetIndex = 0;
        StringBuilder sb = new StringBuilder(msg);
        List<String> result = new ArrayList<>();
        while (targetIndex < sb.length()) {
            int rangeIndex = targetIndex + 1;
            
            // 이번 문자까지 포함되어있으면 다음 문자까지 확인 
            while (rangeIndex <= sb.length() && dictionary.containsKey(sb.substring(targetIndex, rangeIndex))) {
                rangeIndex++;
            }
            
            String found = sb.substring(targetIndex, rangeIndex - 1);
            result.add(found);
            // System.out.println(found);
            
            if (rangeIndex <= sb.length()) {
                String newWord = sb.substring(targetIndex, rangeIndex);
                dictionary.put(newWord, newWordIndex);
                newWordIndex++;
            }
            
            targetIndex = rangeIndex -1;
        }
        
        // System.out.println(dictionary);
        int[] answer = new int[result.size()];
        for (int i=0; i<result.size(); i++) {
            answer[i] = dictionary.get(result.get(i));
        }
        return answer;
    }
}