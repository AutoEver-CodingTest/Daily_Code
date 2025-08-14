#include <string>
#include <vector>
#include <algorithm>
#include <iostream>

using namespace std;

// 그리디의 대표문제인 회의실 잡기 그리디 알고리즘과 유형이 비슷함
// 회의실은 끝나는 시간이 제일 빠른 순으로 잡게 된다면 최대 개수가 된다는 것을 알 수 있었음
// 개구간 a < x < b
// 이것은 닫히는 구간이 빠른 순으로만 요격을 해주면 최소값을 얻을 수 있다는 '사실'이 존재함
// 증명 : min(b) 부분에서 다른 것으로 바뀐다고해도 상관이 없는가?
// if : b가 더 작은 비행기(alpha)로 대체 된다고 한다면?
// 그럼에도 a < x < alpha 이기 때문에 해당 공간은 a < x < b도 격추된다. 즉, 손해를 보지 않는다는게 증명됨
// 나머지 원래 맞았던 구간도 무조건 b 이상의 시간에서 끝나기 때문에 격추될 수 밖에 없다. (a > b 는 존재하지 않기 때문에 , 최소 길이 1)
int solution(vector<vector<int>> targets) {
    int answer = 0;
    sort(targets.begin(),targets.end(),[](const vector<int>& a, const vector<int>& b){
        return a[1] == b[1] ? a[0] < b[0] : a[1] < b[1];
    });
    
    // 범위를 넘으면 새로운 미사일을 준비해서 쏜다
    int now = 0;
    for(vector<int> fly: targets){
        if(fly[0] >= now){ // 닫힌 구간 이기 때문에 >=로 넣어야된된다.
            answer++;
            now = fly[1];
        }
    }
    return answer;
}