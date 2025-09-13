#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;

int main() {
    freopen_s(new FILE*, "input.txt", "r", stdin);
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    long long N, M;
    cin >> N >> M;
    vector<long long> tree(N);

    for (long long& h : tree) cin >> h;

    long long left = 0;
    long long right = *max_element(tree.begin(), tree.end());
    long long answer = 0;

    while (left <= right) {
        long long mid = (left + right) / 2;
        long long sum = 0;

        for (long long h : tree) {
            if (h > mid) sum += (h - mid);
        }

        if (sum >= M) {  // 조건 만족 → 더 높게 자를 수 있음
            answer = mid;
            left = mid + 1;
        }
        else {          // 조건 불만족 → 더 낮게 잘라야 함
            right = mid - 1;
        }
    }

    cout << answer;
    return 0;
}
