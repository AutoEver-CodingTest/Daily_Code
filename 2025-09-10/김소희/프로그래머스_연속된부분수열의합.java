class Solution {
    public int[] solution(int[] sequence, int k) {
        int n = sequence.length;
        int left = 0, right = 0;
        int sum = 0;

        int bestLeft = 0, bestRight = 0;
        int minLen = Integer.MAX_VALUE;

        while (true) {
            if (sum >= k) {
                if (sum == k) {
                    int len = right - left;
                    if (len < minLen) {
                        minLen = len;
                        bestLeft = left;
                        bestRight = right - 1; // right는 한 칸 더 가 있으므로 -1
                    }
                }
                sum -= sequence[left];
                left++;
            } else {
                if (right == n) break;
                sum += sequence[right];
                right++;
            }
        }

        return new int[]{bestLeft, bestRight};
    }
}
