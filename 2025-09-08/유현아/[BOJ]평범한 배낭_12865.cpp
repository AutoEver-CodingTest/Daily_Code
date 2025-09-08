#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

int main() {
	freopen_s(new FILE*, "input.txt", "r", stdin);
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	int N, K, a,b;

	cin >> N >> K;
	vector<vector<int>> input(N + 1, vector<int>(2, 0));
	vector<vector<int>> V(N + 1, vector<int>(K + 1, 0));
	input[0][0] = input[0][1] = 0;

	for (int i = 1; i <= N;i++) {
		cin >> input[i][1] >> input[i][0];  // weight:1, value:0
	}
	sort(input.begin(), input.end());

	for (int i = 0; i <= N;i++) {
		for (int j = 0; j <= K;j++) {
			if (i == 0 || j == 0){
				V[i][j] = 0;
			}
			else if (input[i][1] <= j) {
				V[i][j] = max(V[i - 1][j], input[i][0] + V[i - 1][j - input[i][1]]);
			}
			else {
				V[i][j] = V[i - 1][j];
			}
		}
	}


	cout << V[N][K];

	return 0;
}