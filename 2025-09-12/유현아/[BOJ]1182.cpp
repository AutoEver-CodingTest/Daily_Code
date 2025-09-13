#include <iostream>
#include <vector>
using namespace std;

int N, S, cnt = 0;
vector<int> arr;

void dfs(int idx, int sum) {
    if (idx == N) {
        if (sum == S) cnt++;
        return;
    }

    // 현재 원소를 포함하는 경우
    dfs(idx + 1, sum + arr[idx]);
    // 현재 원소를 포함하지 않는 경우
    dfs(idx + 1, sum);
}

int main() {
    freopen_s(new FILE*, "input.txt", "r", stdin);
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    cin >> N >> S;
    arr.resize(N);
    for (int i = 0; i < N; i++) cin >> arr[i];

    dfs(0, 0);

    // 공집합은 제외해야 함 (S=0일 때 공집합이 카운트됨)
    if (S == 0) cnt--;

    cout << cnt;
    return 0;
}
