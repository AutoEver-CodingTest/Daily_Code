import java.util.*;

class Solution {
    public int[] solution(int[] fees, String[] records) {
        int baseTime = fees[0], baseFee = fees[1], unitTime = fees[2], unitFee = fees[3];

        Map<String, Integer> in = new HashMap<>();      // 차량번호 -> 입차 시각(분)
        Map<String, Integer> total = new HashMap<>();   // 차량번호 -> 총 주차 시간(분)

        for (String rec : records) {
            String[] parts = rec.split(" ");
            int time = toMin(parts[0]);
            String car = parts[1];
            String type = parts[2];

            if (type.equals("IN")) {
                in.put(car, time);
            } else { // OUT
                int t = time - in.remove(car);
                total.put(car, total.getOrDefault(car, 0) + t);
            }
        }

        // 출차 안 한 차량은 23:59로 처리
        int end = toMin("23:59");
        for (Map.Entry<String, Integer> e : in.entrySet()) {
            String car = e.getKey();
            int t = end - e.getValue();
            total.put(car, total.getOrDefault(car, 0) + t);
        }

        // 차량번호 오름차순으로 요금 계산
        List<String> cars = new ArrayList<>(total.keySet());
        Collections.sort(cars);

        int[] answer = new int[cars.size()];
        for (int i = 0; i < cars.size(); i++) {
            int time = total.get(cars.get(i));
            answer[i] = calcFee(time, baseTime, baseFee, unitTime, unitFee);
        }
        return answer;
    }

    private static int toMin(String hhmm) {
        String[] sp = hhmm.split(":");
        return Integer.parseInt(sp[0]) * 60 + Integer.parseInt(sp[1]);
    }

    private static int calcFee(int time, int baseTime, int baseFee, int unitTime, int unitFee) {
        if (time <= baseTime) return baseFee;
        int extra = time - baseTime;
        int units = (extra + unitTime - 1) / unitTime; // 올림
        return baseFee + units * unitFee;
    }
}
