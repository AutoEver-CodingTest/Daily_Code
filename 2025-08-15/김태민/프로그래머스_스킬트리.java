class Solution {
    public int solution(String skill, String[] skill_trees) {
        int answer = 0;
        int seq = 0;
        for (int i = 0; i < skill_trees.length;i++) {
            boolean result = true;
            for(int j = 0;j < skill_trees[i].length();j++) {
                if(skill.contains(String.valueOf(skill_trees[i].charAt(j)))) {
                    if (skill_trees[i].charAt(j) == skill.charAt(seq)) {
                        seq++;
                    } else {
                        result = false;
                        break;
                    }
                }
            }
            if (result) {
                answer++;
            }
            seq = 0;
            
        }
        
        return answer;
    }
}
