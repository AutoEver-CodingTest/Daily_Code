#include <iostream>
#include <algorithm>
#include <vector>

using namespace std;


int main() {
	freopen_s(new FILE*, "input.txt", "r", stdin);
	int N;
	cin >> N;
	vector<int> step(N + 1, 0);
	vector<int> dp(N + 1, 0);
	
	step[0] = dp[0] = 0;

	for (int i = 1; i <= N; i++) {
		cin >> step[i];
	}

	dp[1] = step[1];
	dp[2] = dp[1] + step[2];

	for (int i = 3; i <= N; i++) {
		dp[i] = max(step[i] + dp[i-2], step[i] + step[i-1] + dp[i-3]);
		dp[i] = max(dp[i], dp[i - 1]);
	}

	cout << *max_element(dp.begin(), dp.end());

	return 0;
}