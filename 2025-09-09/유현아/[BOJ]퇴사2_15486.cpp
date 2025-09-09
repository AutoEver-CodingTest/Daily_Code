#include <iostream>
#include <vector>
#include <algorithm>


using namespace std;

struct timeSlot
{
	int time, pay;
};

int main() {
	freopen_s(new FILE*, "input.txt", "r", stdin);

	int N, t,p;
	cin >> N;
	
	vector<timeSlot> list(N+1);
	//vector<int> dp(N+1,0);

	vector<int> dp(N + 2, 0); // N+2 크기 확보

	list[0]={ 0,0 };
	for (int i = 1; i <= N; i++) {
		cin >> t >> p;
		if (t <= N - i + 1) {
			list[i].time = t;
			list[i].pay = p;
		}
		else {
			list[i].time = 0;
			list[i].pay = 0;
		}
	}
	dp[0] = 0;
	dp[N] = list[N].pay;


	for (int i = N; i >= 1; i--) {
		if (i + list[i].time <= N + 1) // 상담 가능
			dp[i] = max(dp[i + 1], list[i].pay + dp[i + list[i].time]);
		else // 상담 불가능
			dp[i] = dp[i + 1];
	}



	cout << dp[1];
	return 0;
}