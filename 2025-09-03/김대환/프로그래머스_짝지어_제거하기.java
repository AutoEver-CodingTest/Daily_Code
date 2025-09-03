// 스택으로 풀이
// 왼쪽부터 문자를 하나씩 읽는다.
// 스택이 비어있지 않고 top == 현재 문자이면, 두 문자가 짝이 되어 제거(pop) 된다.
// 그렇지 않으면 현재 문자를 push한다.
// 전부 처리한 뒤 스택이 비어 있으면 1, 아니면 0을 반환한다.

class Solution {
    public int solution(String s) {
        char[] stack = new char[s.length()];
        int top = -1; // 비어있음

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (top >= 0 && stack[top] == c) {
                top--;  // pop: 인접 동일 문자 쌍 제거, 포인터만 이동
            } else {
                stack[++top] = c; // push
            }
        }
        return (top == -1) ? 1 : 0;
    }
}