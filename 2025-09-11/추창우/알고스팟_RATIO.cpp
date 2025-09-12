
#include <iostream>
using namespace std;

// 승률 계산 함수
int ratio(long long games, long long won) { return (won * 100) / games; }

// 추가로 필요한 게임 수를 이분 탐색으로 구하는 함수
int neededGames(long long games, long long won) {
  // 이미 승률이 변하지 않는 경우
  if (ratio(games, won) == ratio(games + 1, won + 1))
    return -1;

  long long lo = 0, hi = 987654321;

  while (lo + 1 < hi) {
    long long mid = (lo + hi) / 2;
    if (ratio(games, won) == ratio(games + mid, won + mid))
      lo = mid;
    else
      hi = mid;
  }

  return hi;
}

int main() {
  long long games, won;
  cin >> games >> won;
  cout << neededGames(games, won) << endl;
  return 0;
}