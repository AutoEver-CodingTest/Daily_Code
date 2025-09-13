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

    // ���� ���Ҹ� �����ϴ� ���
    dfs(idx + 1, sum + arr[idx]);
    // ���� ���Ҹ� �������� �ʴ� ���
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

    // �������� �����ؾ� �� (S=0�� �� �������� ī��Ʈ��)
    if (S == 0) cnt--;

    cout << cnt;
    return 0;
}
