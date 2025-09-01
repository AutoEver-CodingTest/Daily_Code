#include <algorithm>
#include <iostream>
#include <string>
#include <unordered_map>
#include <vector>

using namespace std;

int N, M;
vector<string> names;
unordered_map<string, int> nameToIdx;

// 친구가 먹을 수 있는 음식 목록
vector<vector<int>> canEat;
// 음식을 먹을 수 있는 친구 목록
vector<vector<int>> eaters;

// 가지치기를 위한 전역에서 제일 좋은 응답
// 여기를 넘어가는 분기점들은 모두 제거한다
int best;

// 구현1 아직 아무것도 못 먹는 첫 친구를 찾는다
int pickFirstUncovered(const vector<int> &edibleCount) {
  for (int i = 0; i < N; i++) {
    if (edibleCount[i] == 0)
      return i;
  }
  return N;
}

// 구현 2 백트래킹으로 최소 음식 수를 찾는다
// first 친구가 먹을 수 있는 음식들만 시도한다
void search(vector<int> &edibleCount, int chosen) {
  if (chosen >= best)
    return;

  int first = pickFirstUncovered(edibleCount);
  if (first == N) {
    if (chosen < best)
      best = chosen;
    return;
  }

  // 분기 순서를 조금이라도 줄이기 위해 gain이 큰 음식부터 시도
  vector<pair<int, int>> cand;
  for (int f : canEat[first]) {
    int gain = 0;
    for (int p : eaters[f])
      if (edibleCount[p] == 0)
        gain++;
    if (gain > 0)
      cand.push_back({gain, f});
  }
  sort(cand.begin(), cand.end(),
       [](const pair<int, int> &a, const pair<int, int> &b) {
         return a.first > b.first;
       });

  for (auto &it : cand) {
    int f = it.second;
    for (int p : eaters[f])
      edibleCount[p]++;

    search(edibleCount, chosen + 1);

    for (int p : eaters[f])
      edibleCount[p]--;
  }
}

int main() {
  ios::sync_with_stdio(false);
  cin.tie(nullptr);

  int T;
  if (!(cin >> T))
    return 0;
  while (T--) {
    cin >> N >> M;

    names.assign(N, "");
    nameToIdx.clear();
    for (int i = 0; i < N; i++) {
      cin >> names[i];
      nameToIdx[names[i]] = i;
    }

    eaters.assign(M, {});
    for (int f = 0; f < M; f++) {
      int k;
      cin >> k;
      eaters[f].reserve(k);
      for (int i = 0; i < k; i++) {
        string s;
        cin >> s;
        eaters[f].push_back(nameToIdx[s]);
      }
    }

    canEat.assign(N, {});
    for (int f = 0; f < M; f++) {
      for (int p : eaters[f])
        canEat[p].push_back(f);
    }

    vector<int> edibleCount(N, 0);
    best = 987654321;

    search(edibleCount, 0);

    cout << best << "\n";
  }
  return 0;
}