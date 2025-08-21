import java.util.*;
class Solution {
    
    class Node {
        String name;
        int time;
        int playtime;
        public Node(String name, int time, int playtime) {
            this.name = name;
            this.time = time;
            this.playtime = playtime;
        }
    }
    
    public String[] solution(String[][] plans) {
        String[] answer = new String[plans.length];
        PriorityQueue<Node> pq = new PriorityQueue<>((a,b) -> a.time - b.time);
        Stack<Node> stack = new Stack<>();
        for (String[] str : plans) {
            String[] s = str[1].split(":");
            int time = Integer.parseInt(s[0]) * 60 + Integer.parseInt(s[1]);
            int playtime = Integer.parseInt(str[2]);
            pq.offer(new Node(str[0],time,playtime));
        }
        
        Node n = pq.poll();
        int now = n.time;
        int idx = 0;
        
        
        while(true) {
            if (!pq.isEmpty() && now + n.playtime > pq.peek().time) {
                stack.push(new Node(n.name, n.time,
                                    now + n.playtime - pq.peek().time));
                now = pq.peek().time;
                n = pq.poll();
            } else {
                answer[idx++] = n.name;
                now += n.playtime;
                if(!pq.isEmpty() && now == pq.peek().time) {
                    n = pq.poll();
                } else if(!stack.isEmpty())  {
                    n = stack.pop();
                } else if(!pq.isEmpty()) {
                    n = pq.poll();
                    now = n.time;
                } else {
                    break;
                }
            }
            
        }
        return answer;
    }
}
