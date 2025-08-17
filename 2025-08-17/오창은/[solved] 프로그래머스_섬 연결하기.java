import java.util.*;

class Solution {
   
    public int find(int[] parent, int x) { 
   		if (parent[x] == x) return x;
        return parent[x] = find(parent, parent[x]);
    }
    public void union(int[] parent, int a, int b) {
        int rootA = find(parent, a);
        int rootB = find(parent, b);
        
        if (rootA < rootB) 
            parent[rootB] = rootA;
        else 
            parent[rootA] = rootB;
    }
    
    public int solution(int n, int[][] costs) {
        int answer = 0;
       
        Arrays.sort(costs, (a, b) -> a[2] - b[2]);
     
        int[] parent = new int[n];
        for (int i=0; i<parent.length; i++) {
            parent[i] = i;
        }
       
        int count = 0;
        for (int[] cost: costs) {
            int a = cost[0];
            int b = cost[1];
            int c = cost[2]; // 비용(가중치)
            
            if (find(parent, a) != find(parent, b)) {
                union(parent, a, b);
                answer += c;
                count++;
            }
            
            if (count == n-1) 
                break;
        }
        /*
        for (int i=0; i<costs.length; i++) {
            for (int j=0; j<costs[0].length; j++) {
                System.out.print(costs[i][j]);
            }
            System.out.println();
        }
        */
        
        return answer;
    }
}