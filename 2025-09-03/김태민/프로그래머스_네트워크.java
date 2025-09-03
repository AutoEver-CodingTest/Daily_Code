class Solution {
    
    public static int[][] array;
    public static boolean [] visit;
    
    public int solution(int n, int[][] computers) {
        int answer = 0;
        
        array = new int[n][n];
        visit = new boolean[n];
        
        for (int i = 0; i < n; i++) {
            for (int j = 0;j < n;j++) {
                if (computers[i][j] == 1) {
                    array[i][j] = 1;
                    array[j][i] = 1;
                }
            }
        }
        
        for (int i = 0;i < n;i++) {
            if (!visit[i]) {
                dfs(i,n);
                answer++;
            }
        }
        
        return answer;
        
        
        
    }
    
    public void dfs(int start,int n) {
        visit[start] = true;
        for (int i = 0; i < n;i++) {
            if (!visit[i] && array[start][i] == 1) {
                dfs(i,n);
            }
        }
        
        
    }
    
}
