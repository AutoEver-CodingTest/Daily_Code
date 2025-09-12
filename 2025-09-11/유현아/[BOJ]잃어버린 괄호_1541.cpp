#include <iostream>
#include <string>
#include <algorithm>

using namespace std;

int main() {
	freopen_s(new FILE*, "input.txt", "r", stdin);

	string line, tep = "";
	bool minu = false;	// 마이너스 값 시작이면

	int total = 0;

	cin >> line;
	for (int i = 0; i < line.size(); i++) {
		if (line[i] == '+') {
			if (minu) total -= stoi(tep);
			else {		
				total += stoi(tep);
			}
			tep = "";
		}
		else if (line[i] == '-') {
			if (minu) total -= stoi(tep);
			else total += stoi(tep);
			minu = true;
			tep = "";
		}
		else {
			tep = tep + line[i];
		}

		if (i == line.size() - 1) {
			if (minu) total -= stoi(tep);
			else {
				total += stoi(tep);
			}
		}
	}
	
	cout << total;

	return 0;
}