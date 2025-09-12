#include <iostream>
#include <vector>
#include <queue>

using namespace std;

struct Node {
	int x=0;
	int y=0;
	int day =0;
	bool visited = false;

	Node() {}

	Node(int x, int y, int day)
		: x(x), y(y), day(day) {}

	Node(int x, int y, int day, bool visited)
		: x(x), y(y), day(day), visited(visited) {}

};

void ppp(vector<vector<Node>> map) {
	cout << "=====================" << endl;
	for (int i = 0; i < 4; i++) {
		for (int j = 0; j < 6; j++) {
			cout << map[i][j].day << " ";
		}
		cout << '\n';
	}
}


int main() {
	freopen_s(new FILE*, "input.txt", "r", stdin);
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	int row, col;
	cin >> col >> row;
	vector<vector<Node>> map(row, vector<Node>(col));
	queue<Node> q;

	int dx[] = { -1, 0, 1, 0 }; 
	int dy[] = { 0, 1, 0, -1 };

	bool cho = false;

	for (int i = 0; i < row; i++) {
		for (int j = 0; j < col; j++) {
			int state;
			cin >> state;
			if (state == 0) cho = true;
			else if(state == 1) q.push({ i,j,1 });
			map[i][j] = {i, j, state, state==-1? true:false};
		}
	}

	if (cho == false) {
		cout << 0;
		return 0;
	}



	while (!q.empty()) 
	{
		Node currentNode = q.front();
		q.pop();
		for (int k = 0; k < 4; k++) {
			int nx = currentNode.x + dx[k];
			int ny = currentNode.y + dy[k];
					
			if (nx < 0 || nx >= row || ny < 0 || ny >= col) continue;
			if (currentNode.visited) continue;

			int nday = currentNode.day + 1;
			if (nday >= map[nx][ny].day && map[nx][ny].day != 0) continue;
			map[nx][ny].day = nday;
			q.push({ nx, ny, nday });
		}
		map[currentNode.x][currentNode.y].visited = true;

		//ppp(map);
	}

	bool notall = false;
	int max = 0;
	for (int i = 0; i < row; i++) {
		for (int j = 0; j < col; j++) {
			if (map[i][j].day == 0) {
				cout << -1;
				notall = true;
				break;
			}
			if (max <= map[i][j].day) max = map[i][j].day;
		}
		if (notall == true) return 0;
	}

	cout << max-1;


	return 0;
}