// [a.x, b.x] 구간에 x가 포함되는지 검사
bool between(const point a, const point b, double x) {
  return (a.x <= x && x <= b.x) || (b.x <= x && x <= a.x);
}

// 선분 (a, b) 위에서 주어진 x좌표에 대응하는 y좌표 계산
double at(const point a, const point b, double x) {
  double dy = b.y - a.y, dx = b.x - a.x;
  return a.y + dy * (x - a.x) / dx;
}

// 주어진 x좌표에서 두 다각형의 위쪽 경계와 아래쪽 경계 차이를 계산
double vertical(double x) {
  double minUp = 1e20, maxLow = -1e20;

  // 위쪽 hull 검사
  for (int i = 0; i < upper.size(); i++) {
    if (between(upper[i].first, upper[i].second, x)) {
      minUp = min(minUp, at(upper[i].first, upper[i].second, x));
    }
  }

  // 아래쪽 hull 검사
  for (int i = 0; i < lower.size(); i++) {
    if (between(lower[i].first, lower[i].second, x)) {
      maxLow = max(maxLow, at(lower[i].first, lower[i].second, x));
    }
  }

  return minUp - maxLow;
}

// 두 볼록다각형의 교집합의 최대 높이를 찾는 solve 함수
double solve() {
  // 교집합 x구간 결정
  double lo = max(minX(hull1), minX(hull2));
  double hi = min(maxX(hull1), maxX(hull2));

  if (hi <= lo)
    return 0;

  // 삼등분 탐색 (ternary search)
  for (int iter = 0; iter < 100; iter++) {
    double aab = (2 * lo + hi) / 3.0;
    double abb = (lo + 2 * hi) / 3.0;

    if (vertical(aab) < vertical(abb)) {
      lo = aab;
    } else {
      hi = abb;
    }
  }

  return max(0.0, vertical(hi));
}