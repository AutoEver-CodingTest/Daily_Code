#include <iostream>
#include <deque>
#include <vector>
using namespace std;

int main() {
    freopen_s(new FILE*, "input.txt", "r", stdin);
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int N, K;
    cin >> N >> K;

    const int MAX = 100000;
    vector<int> dist(MAX + 1, -1); // -1�̸� ���� �湮 ����
    deque<int> dq;

    dist[N] = 0;
    dq.push_back(N);

    while (!dq.empty()) {
        int cur = dq.front();
        dq.pop_front();

        if (cur == K) {
            cout << dist[cur];
            return 0;
        }

        // 1. �����̵� (�ð� 0)
        if (cur * 2 <= MAX && dist[cur * 2] == -1) {
            dist[cur * 2] = dist[cur];
            dq.push_front(cur * 2); // 0�ʴϱ� ������
        }

        // 2. -1 �̵� (�ð� 1)
        if (cur - 1 >= 0 && dist[cur - 1] == -1) {
            dist[cur - 1] = dist[cur] + 1;
            dq.push_back(cur - 1);
        }

        // 3. +1 �̵� (�ð� 1)
        if (cur + 1 <= MAX && dist[cur + 1] == -1) {
            dist[cur + 1] = dist[cur] + 1;
            dq.push_back(cur + 1);
        }
    }
}
