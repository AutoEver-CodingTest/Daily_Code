import java.io.*;
import java.util.*;

public class Main {
  static int n;

  // 배열의 범위 초과 방지를 위해 +1 씩
  static int[][] house = new int[1001][3];
  static int[][] cache = new int[1002][4];

  // 최대값 지정
  static final int INF = 987654321;

  static int rgb(int houseIdx, int prevColor) {
    int ci = houseIdx + 1;
    int pc = prevColor + 1;

    // 기저 사례 : 만약에 이미 계산되어 있으면 그대로 반환
    if (cache[ci][pc] != -1) {
      return cache[ci][pc];
    }

    // 기저 사례 : 끝까지 방문한 경우에도
    if (houseIdx == n) {
      return cache[ci][pc] = 0;
    }

    // 캐쉬되지 않았다면 계산시작
    int ret = INF;

    for (int color = 0; color < 3; color++) {

      if (prevColor != color || houseIdx == -1) {

        ret = Math.min(ret, house[houseIdx + 1][color] + rgb(houseIdx + 1, color));
      }
    }

    // 할당과 동시에 리턴
    return cache[ci][pc] = ret;
  }

  public static void main(String[] args) throws Exception {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    // cache 배열 초기화
    for (int i = 0; i < cache.length; i++) {
      Arrays.fill(cache[i], -1);
    }

    n = Integer.parseInt(br.readLine().trim());

    for (int i = 0; i < n; i++) {
      StringTokenizer st = new StringTokenizer(br.readLine());
      house[i][0] = Integer.parseInt(st.nextToken());
      house[i][1] = Integer.parseInt(st.nextToken());
      house[i][2] = Integer.parseInt(st.nextToken());
    }

    System.out.println(rgb(-1, -1));
  }
}