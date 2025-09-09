#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

int main() {
	cin.tie(nullptr);
	ios::sync_with_stdio(false);
	freopen_s(new FILE*, "input.txt", "r", stdin);

	long long N, col =1, temp;
	cin >> N;
	vector<vector<long long>> trial(N, vector<long long>(N,0));


	for (int i = 0; i < N;i++) {
		for (int j = 0; j < col;j++) {
			cin >> temp;
			trial[i][j] = temp;
		}
		col++;
	}

	for (int i = N - 2; i >= 0; i--) {
		for (int j = 0; j <= i; j++) {
			trial[i][j] += max(trial[i + 1][j], trial[i + 1][j + 1]);
		}
	}

	cout << trial[0][0];



	return 0;
}