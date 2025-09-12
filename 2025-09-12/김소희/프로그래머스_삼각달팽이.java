class Solution {
    public int[] solution(int n) {
        int[][] arr = new int[n][n];
        int size = n*(n+1)/2;
        int [] answer = new int[size];
        
        int y=-1,x=0;
        int num=1;
        for(int i=0; i<n; i++){
            for(int j=i; j<n; j++){
                if(i%3 ==0){ //아래
                    y++;
                }else if(i%3 ==1){//오른쪽
                    x++;
                }else{ //대각선
                    y--;
                    x--;
                }
                arr[y][x]=num++;
            } 
        }
        int index=0;
        for(int i=0; i<n; i++){
            for(int j=0; j<=i; j++){
                answer[index++]=arr[i][j];
            }
        }
        return answer;
    }
}
