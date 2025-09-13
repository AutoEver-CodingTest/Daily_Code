import java.util.*;
class Solution {
    
    static class Node {
        String str;
        int count;
        Node(String str, int count) {
            this.str = str;
            this.count = count;
        }
    }
    
    static boolean [] visit;
    public int solution(String begin, String target, String[] words) {
        int answer = 0;
        visit = new boolean[words.length];
        
        
        return bfs(begin,target,words);
    }
    
    static public int bfs(String start, String end, String[] words) {
        
        Queue<Node> q = new LinkedList<>();
        
        
        for (int i = 0; i < words.length;i++) {
            if (check(start,words[i])) {
                q.offer(new Node(words[i], 1));
                visit[i] = true;
            }
        }
        
        while(!q.isEmpty()) {
            Node node = q.poll();
            if (node.str.equals(end)) {
                return node.count;
            }
            for (int i = 0; i < words.length;i++) {
                if (check(node.str,words[i]) && !visit[i]) {
                    q.offer(new Node(words[i], node.count + 1));
                    visit[i] = true;
                }
            }
        }
        return 0;
    }
    
    static public boolean check(String a, String b) {
        int count = 0;
        for (int i = 0 ; i < a.length(); i++) {
            if (a.charAt(i) != b.charAt(i)) {
                count++;
            }
        }
        if (count == 1) {
            return true;
        } else {
            return false;
        }
    }
}
