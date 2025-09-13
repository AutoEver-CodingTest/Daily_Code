#include <iostream>
#include <vector>
#include <queue>

using namespace std;
vector<vector<int>> dices;

int findingM(int dindex, int underIndex) {
	priority_queue<int> atleast2;

	if (underIndex == 0 || underIndex == 5) {
		for (int j = 0; j < 6; j++) {
			if (j != 0 && j != 5)
				atleast2.push(dices[dindex][j]);
		}
	}
	else if (underIndex == 1 || underIndex == 3) {
		for (int j = 0; j < 6; j++) {
			if (j != 1 && j != 3)
				atleast2.push(dices[dindex][j]);
		}

	}
	else if (underIndex == 2 || underIndex == 4) {
		for (int j = 0; j < 6; j++) {
			if (j != 2 && j != 4)
				atleast2.push(dices[dindex][j]);
		}
	}



	return atleast2.top();
}



int main() {
	freopen_s(new FILE*, "input.txt", "r", stdin);
	ios::sync_with_stdio(false);
	cin.tie(nullptr);
	int cnt;
	cin >> cnt;
	dices.assign(cnt, vector<int>(6,0));

	for (int i = 0; i < cnt; i++) {
		for (int j = 0; j < 6; j++) {
			cin >> dices[i][j];
		}
	}


	
	priority_queue<int> asf;

	for (int i = 0; i < 6; i++) {
		priority_queue<int> atleast;

		if (i == 0 || i == 5) {
			for (int j = 0; j < 6; j++) {
				if(j != 0 && j != 5 )
					atleast.push(dices[0][j]);
			}
		}
		else if (i == 1 || i == 3) {
			for (int j = 0; j < 6; j++) {
				if (j != 1 && j != 3)
					atleast.push(dices[0][j]);
			}
		
		}
		else if (i == 2 || i == 4) {
			for (int j = 0; j < 6; j++) {
				if (j != 2 && j != 4)
					atleast.push(dices[0][j]);
			}
		}

		int mmmax = atleast.top(), temp=0;
		int nextUnder = dices[0][i];	// 1번의 맨 위 숫자, 현재의 top, 다음의 under

		for (int l = 0; l < 4; l++)
		{
			atleast.pop();
		}
		


		for (int k = 1; k < cnt; k++) {
			for (int j = 0; j < 6; j++) {
				if (dices[k][j] == nextUnder) {
					temp = findingM(k, j);

					if (k == cnt-1) continue;
					if (j == 0) nextUnder = dices[k][5];
					else if (j == 1) nextUnder = dices[k][3];
					else if (j == 2) nextUnder = dices[k][4];
					else if (j == 3) nextUnder = dices[k][1];
					else if (j == 4) nextUnder = dices[k][2];
					else if (j == 5) nextUnder = dices[k][0];

					break;
				}
			}
			mmmax += temp;
		}

		asf.push(mmmax);
	}


	cout << asf.top();

	return 0;
}