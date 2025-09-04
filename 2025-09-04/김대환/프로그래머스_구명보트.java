import java.util.*;

class Solution {
    public int solution(int[] people, int limit) {
        Arrays.sort(people);
        int left = 0, right = people.length - 1;
        int boats = 0;

        while (left <= right) {
            // 가장 무거운 사람은 무조건 태운다.
            if (people[left] + people[right] <= limit) {
                // 가벼운 사람과 함께 탈 수 있으면 같이 태운다.
                left++;
                right--;
            } else {
                // 못타면 무거운 사람 혼자 탑승
                right--;
            }
            boats++;
        }
        return boats;
    }
}