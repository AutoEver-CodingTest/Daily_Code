import java.util.*;
import java.io.*;
public class Main {
    static int[] move = new int[101]; // 사다리, 뱀 이동 정보
    static boolean[] visited = new boolean[101];
    static int result;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine()," ");
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        
        
        //100개의 번호칸 
        for(int i=1; i<=100; i++){
            move[i]=i;
        }
        //사다리/뱀 
        for (int i=0; i<N+M; i++){
            st = new StringTokenizer(br.readLine()," ");
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            move[x] =y;
        }
        System.out.println(bfs(1));
    }
    static int bfs(int a){//행열
        Queue<Integer> q = new LinkedList<>();
        q.offer(a);
        int cnt=0;
        visited[a]=true;
        
        while(!q.isEmpty()){
            int size = q.size();
            cnt++;
            
            for(int s=0; s<size; s++){
                int cur = q.poll();
                
                for(int i=1; i<=6; i++){
                    int temp = cur+ i;//주사위 만큼 이동
                    if(temp>100) continue;
                    
                    temp=move[temp];
                    if(temp==100) return cnt;
                    
                    if(!visited[temp]){
                        visited[temp]=true;
                        q.offer(temp);
                    }
                }
            }
            
        }
        return -1;
    }
}
