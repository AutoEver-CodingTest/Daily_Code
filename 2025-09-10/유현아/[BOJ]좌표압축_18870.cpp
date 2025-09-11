#include <iostream>
#include <map>
#include <queue>
#include <vector>

using namespace std;

int main() {

	int total;
	cin >> total;
	vector<int> inpt(total, 0);
	priority_queue<int, vector<int>, greater<int>> qint;
	map<int, int> m;

	for (int i = 0; i < total; i++) {
		cin >> inpt[i];
		qint.push(inpt[i]);
	}
	int isx = 0;
	while (!qint.empty()) {
		if (m.count(qint.top()) == 0) {
			m[qint.top()] = isx;
			qint.pop();
			isx++;
		}
		else {
			qint.pop();
		}
	}
	for (int i = 0; i < total; i++) {
		cout << m[inpt[i]] << " ";
	}



	return 0;
}