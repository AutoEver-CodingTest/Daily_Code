#include <iostream>
#include <vector>
#include <queue>
#include <algorithm>
using namespace std;

struct study {
    int start, end;
    bool operator<(const study& other) const {
        return start < other.start;
    }
};

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int N;
    cin >> N;
    vector<study> lectures(N);
    for (int i = 0; i < N; i++) {
        cin >> lectures[i].start >> lectures[i].end;
    }

    sort(lectures.begin(), lectures.end()); // ���� �ð� ���� ����

    priority_queue<int, vector<int>, greater<int>> pq; // ������ �ð� �ּ� ��

    for (auto& lec : lectures) {
        if (!pq.empty() && pq.top() <= lec.start) {
            pq.pop(); // ���� ���ǽ� ���� ����
        }
        pq.push(lec.end); // �� ���ǽ� �߰� �Ǵ� ���� ���ǽ� ����
    }

    cout << pq.size(); // �ּ� ���ǽ� ��
    return 0;
}
