import java.util.*;

class Solution {
    public String solution(int n, int t, int m, String[] timetable) {
        String answer = "";

        Arrays.sort(timetable);

        int time9 = 9 * 60; // 9시 
        int curr = 0; // 현재까지 버스에 탑승한 사람 인덱스 
        Queue<Integer> max = new LinkedList<>();
        for (int i=0; i<n; i++) {
            int currTime = time9 + t * i;
            if (i == n - 1) { // 마지막 버스
                int capacity = 0;
                int lastBoarded = -1; // 마지막 탑승자 시간 기록
                for (int j=curr; j<timetable.length; j++) {
                    int time = setTime(timetable[j]);
                    if (time <= currTime && capacity < m) {
                        lastBoarded = time;
                        curr++;
                        capacity++;
                    } else break;
                }
                if (capacity == m) { // 정원 꽉 찼음
                    answer = setTimeToString(lastBoarded - 1);
                } else { // 정원 덜 찼음
                    answer = setTimeToString(currTime);
                }
            } else {
                // 현재 9시 일 때, 
                // timetable에서 9시보다 작은 시간들 개수만큼 curr을 옮겨야 해. 
                // 근데 curr을 옮길 때 최대 m만큼만 옮겨야 하고, m보다 작은 만큼은 옮겨도 돼.
                int capacity = 0;
                for (int j=curr; j<timetable.length; j++) {
                    int time = setTime(timetable[j]);
                    if (time <= currTime) {
                        curr++;
                        capacity++;
                        if (capacity == m) {
                            break;
                        }
                    } 
                }
            }
        } 

       /* 
        for (String time: timetable) {
            int tNum = setTime(time);
            System.out.println(tNum);
        } 
       */ 
        return answer;
    }
    private int setTime(String time) {
        String[] parts = time.split(":");
        int hour = Integer.parseInt(parts[0]);
        int minute = Integer.parseInt(parts[1]);
        return hour * 60 + minute;
    }
    private String setTimeToString(int time) {
        int hour = time / 60;
        int minute = time % 60;
        return String.format("%02d:%02d", hour, minute); 
    }
}
