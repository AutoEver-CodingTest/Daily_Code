#include <iostream>
#include <vector>
using namespace std;

const int N = 10;
const int INF = 987654321;

int board[N][N];
vector<int> stock(6, 5);
int bestAns = INF;

bool canPlace(int y, int x, int k) {
  if (y + k > N || x + k > N)
    return false;
  for (int i = 0; i < k; ++i)
    for (int j = 0; j < k; ++j)
      if (board[y + i][x + j] == 0)
        return false;
  return true;
}

void fillSquare(int y, int x, int k, int val) {
  for (int i = 0; i < k; ++i)
    for (int j = 0; j < k; ++j)
      board[y + i][x + j] = val;
}

bool findNextOne(int &y, int &x) {
  for (int i = 0; i < N; ++i)
    for (int j = 0; j < N; ++j)
      if (board[i][j] == 1) {
        y = i;
        x = j;
        return true;
      }
  return false;
}

void dfs(int used) {
  if (used >= bestAns)
    return;

  int y, x;
  if (!findNextOne(y, x)) {
    bestAns = min(bestAns, used);
    return;
  }

  for (int k = 5; k >= 1; --k) {
    if (stock[k] == 0)
      continue;
    if (!canPlace(y, x, k))
      continue;
    fillSquare(y, x, k, 0);
    stock[k]--;
    dfs(used + 1);
    stock[k]++;
    fillSquare(y, x, k, 1);
  }
}

int main() {
  ios::sync_with_stdio(false);
  cin.tie(nullptr);

  for (int i = 0; i < N; ++i)
    for (int j = 0; j < N; ++j)
      cin >> board[i][j];

  dfs(0);

  cout << (bestAns == INF ? -1 : bestAns) << '\n';
  return 0;
}