import java.util.*;

class Solution {
    public String solution(String[] cards1, String[] cards2, String[] goal) {
        int i = 0, j = 0;

        for (String g : goal) {
            if (i < cards1.length && g.equals(cards1[i])) {
                i++;
            } else if (j < cards2.length && g.equals(cards2[j])) {
                j++;
            } else {
                return "No";
            }
        }
        return "Yes";
    }
}
