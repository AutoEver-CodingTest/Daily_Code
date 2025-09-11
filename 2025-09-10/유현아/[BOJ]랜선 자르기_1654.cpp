#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

int main() {
	freopen_s(new FILE*, "input.txt", "r", stdin);
	cin.tie(nullptr);
	ios::sync_with_stdio(false);
	int K, N;
	cin >> K >> N;
	vector<int> lan(K);

	for (int i = 0; i < K; i++) {
		cin >> lan[i];
	}
	sort(lan.begin(), lan.end());

	long long left = 0, right = lan[K - 1], mid, quo = 0;

	while (left <= right) {
		mid = (left + right) / 2;
		if (mid == 0) mid = 1;
		quo = 0;
		for (int i = 0; i < K; i++) {
			quo += lan[i] / mid;
		}

		if (quo >= N) {
			left = mid + 1;
		}
		else {
			right = mid - 1;
		}
	}
	cout << right;



	return 0;
}