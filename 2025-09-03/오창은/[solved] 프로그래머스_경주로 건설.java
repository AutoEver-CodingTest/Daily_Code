import java.util.*;

class Solution {
    class Node {
        int x, y, dir, cost;
        public Node (int x, int y, int dir, int cost) {
            this.x = x;
            this.y = y;
            this.dir = dir;
            this.cost = cost;
        }
    }
    
    int[] dx = { 0, 0, -1, 1 }; // 아래, 위, 왼쪽, 오른쪽
    int[] dy = { 1, -1, 0, 0 };
    
    public int solution(int[][] board) {
   		int n = board.length;
        int[][][] cost = new int[n][n][4];
    	for (int i=0; i<n; i++) {
            for (int j=0; j<n; j++) {
                Arrays.fill(cost[i][j], Integer.MAX_VALUE);
            }
        }
        
        Queue<Node> q = new LinkedList<>();
        q.offer(new Node(0, 0, 0, 0)); // 아래 방향 
        q.offer(new Node(0, 0, 3, 0)); // 오른쪽 방향 
    
   		cost[0][0][0] = 0;
        cost[0][0][3] = 0;
       
        int answer = Integer.MAX_VALUE;
        while (!q.isEmpty()) {
        	Node curr = q.poll();
            
            if (curr.x == n-1 && curr.y == n-1) {
               	answer = Math.min(answer, curr.cost);
                continue;
            }
            
            for (int dir=0; dir<4; dir++) {
                int nx = curr.x + dx[dir];
                int ny = curr.y + dy[dir];
                
                if (nx < 0 || ny < 0 || nx >= n || ny >= n) continue;
                if (board[ny][nx] == 1) continue;
                
                int newCost = curr.cost;
                if (curr.dir == dir) { // 들어온 방향이 나가는 방향과 같은 경우 
                    newCost += 100;
                } else {
                   	newCost += 600; 
                }
                
                if (newCost < cost[ny][nx][dir]) {
                    cost[ny][nx][dir] = newCost;
                    q.offer(new Node(nx, ny, dir, newCost));
                }
            }
        }
   
        return answer;
    }
}