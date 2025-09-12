#include <iostream>
#include <vector>

using namespace std;

vector<bool> col, diag1, diag2;
int N, cnt = 0;

void dfs(int row) {
	if (row >= N) {
		cnt++;
		return;
	}


	for (int c = 0; c < N; c++) {
		if (col[c] || diag1[row+c] || diag2[row-c+N-1]) continue;
	
		col[c] = diag1[row + c] = diag2[row - c + N - 1] = true;

		dfs(row + 1);
		col[c] = diag1[row + c] = diag2[row - c + N - 1] = false;
	
	}
	return;
}


int main() {
	freopen_s(new FILE*, "input.txt", "r", stdin);
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	cin >> N;
	col.assign(N, false);
	diag1.assign(2*N, false);
	diag2.assign(2*N, false);

	dfs(0);

	cout << cnt;

	return 0;
}