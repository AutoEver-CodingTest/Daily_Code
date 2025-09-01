#include <iostream>
#include <vector>

using namespace std;

int boardH, boardW;
int R, C;
char board[10][10];
char block[10][10];

int main(void) {
  ios::sync_with_stdio(0);
  cin.tie(0);

  int cc;
  cin >> cc;
  for (int c = 0; c < cc; c++) {
    cin >> boardH >> boardW >> R >> C;

    // 보드 초기화
    memset(board, '.', sizeof(board));

    for (int i = 0; i < boardH; i++) {
      for (int j = 0; j < boardW; j++) {
        cin >> board[i][j];
      }
    }

    // 블럭초기화
    memset(block, '.', sizeof(block));

    for (int i = 0; i < R; i++) {
      for (int j = 0; j < C; j++) {
        cin >> block[i][j];
      }
    }
  }

  return 0;
}