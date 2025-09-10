#include <iostream>
#include <string>
#include <deque>
#include <queue>
#include <vector>

using namespace std;

vector<deque<int>> dq(4, deque<int>(8, 0));

void clkwise(int N) {
	int last = dq[N].back();
	dq[N].pop_back();
	dq[N].push_front(last);
	return;
}

void cntclkwise(int N) {
	int first = dq[N].front();
	dq[N].pop_front();
	dq[N].push_back(first);
	return;
}


int main() {
	freopen_s(new FILE*, "input.txt", "r", stdin);
	ios::sync_with_stdio(false);
	cin.tie(nullptr);
	
	string line;
	queue<int> cwheel;
	vector<bool> visited(4, false);
	vector<int> wdir(4, 0);	// 1 cw, -1 ccw
	for (int i = 0; i < 4; i++) {
		cin >> line;
		for (int j = 0; j < 8; j++) {
			dq[i][j] = line[j]-'0';
		}
	}
	int total, num, direction;
	cin >> total;
	while (total--) {
		cin >> num >> direction;

		//누가 어떻게 돌지 정함
		num--;
		wdir[num] = direction;
		cwheel.push(num);
		while (!cwheel.empty()) {
			int current = cwheel.front();
			cwheel.pop();
			visited[current] = true;
			int temp = current + 1;
			if (temp >= 0 && temp <= 3) {
				if (!visited[temp] && dq[current][2] != dq[temp][6]) {
					wdir[temp] = wdir[current]*(-1);
					cwheel.push(temp);
				}
			}
			temp = current - 1;
			if (temp >= 0 && temp <= 3) {
				if (!visited[temp] && dq[temp][2] != dq[current][6]) {
					wdir[temp] = wdir[current] * (-1);
					cwheel.push(temp);
				}
			}
		}

		// 돌림
		for (int i = 0; i < 4; i++) {
			if (wdir[i] == 1) clkwise(i);
			else if (wdir[i] == -1) cntclkwise(i);
			else continue;
		}


		// 초기화
		wdir.assign(4, 0);
		visited.assign(4, false);
	}


	// N극은 0, S극은 1
	int score = 0;
	if (dq[0][0] == 1) score += 1;
	if (dq[1][0] == 1) score += 2;
	if (dq[2][0] == 1) score += 4;
	if (dq[3][0] == 1) score += 8;
	

	cout << score;

	return 0;
}