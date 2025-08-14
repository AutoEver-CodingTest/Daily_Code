import java.util.*;
class Solution {
    public int solution(int N, int[][] road, int K) {
        int answer = 0;
        List<int[]> [] node = new ArrayList[N+1];
        for (int i = 1;i <= N;i++) {
            node[i] = new ArrayList<>();
        }
        
        for (int[] r : road) {
            int a = r[0];
            int b = r[1];
            int w = r[2];
            
            node[a].add(new int[]{b,w});
            node[b].add(new int[]{a,w});
        }
        
        int [] dist = new int[N+1];
        Arrays.fill(dist,Integer.MAX_VALUE);
        dist[1] = 0;
        
        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> a[0] - b[0]);
        pq.offer(new int[]{0,1});
        
        while(!pq.isEmpty()) {
            int [] cur = pq.poll();
            if (cur[0] > dist[cur[1]]) {
                continue;
            }
            
            for (int [] nxt : node[cur[1]]) {
                int nxtD = nxt[1] + cur[0];
                if (nxtD < dist[nxt[0]]) {
                    dist[nxt[0]] = nxtD;
                    pq.offer(new int[]{nxtD,nxt[0]});
                }
            }
        }
        
        for (int i = 1;i <= N;i++) {
            if (dist[i] <= K) {
                answer++;
            }
        }

        return answer;
    }
}
