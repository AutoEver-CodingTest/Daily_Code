import java.util.*;

class Solution {
    public int solution(String skill, String[] skill_trees) {
        int answer = 0;
        for (String tree : skill_trees) {
            StringBuilder sb = new StringBuilder();
            for (char c : tree.toCharArray()) {
                if (skill.indexOf(c) != -1) sb.append(c);
            }
            // 필터링된 문자열이 skill의 접두사면 가능
            if (skill.startsWith(sb.toString())) answer++;
        }
        return answer;
    }
}
