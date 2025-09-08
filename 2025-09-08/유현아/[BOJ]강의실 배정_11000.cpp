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

    sort(lectures.begin(), lectures.end()); // 시작 시간 기준 정렬

    priority_queue<int, vector<int>, greater<int>> pq; // 끝나는 시간 최소 힙

    for (auto& lec : lectures) {
        if (!pq.empty() && pq.top() <= lec.start) {
            pq.pop(); // 기존 강의실 재사용 가능
        }
        pq.push(lec.end); // 새 강의실 추가 또는 기존 강의실 갱신
    }

    cout << pq.size(); // 최소 강의실 수
    return 0;
}
