import java.util.*;

class Solution {
   	class Node {
        int x, y, num; 
        Node left, right;
        public Node (int x, int y, int num) {
            this.x = x;
            this.y = y;
            this.num = num;
        }
    }
    
    List<Integer> preorderList;
    List<Integer> postorderList;
    public int[][] solution(int[][] nodeinfo) {
      	int n = nodeinfo.length;
        Node[] nodes = new Node[n];
        
        for (int i=0; i<n; i++) {
            int x = nodeinfo[i][0];
            int y = nodeinfo[i][1];
            nodes[i] = new Node(x, y, i+1);
        }
        
        Arrays.sort(nodes, (a, b) -> {
          	if (a.y == b.y) return a.x - b.x; 
            return b.y - a.y;
        });
       
        Node root = nodes[0];
        for (int i=1; i<n; i++) {
            insertNode(root, nodes[i]);
        }
       
        preorderList = new ArrayList<>();
        postorderList = new ArrayList<>();
       	preorder(root);
       	postorder(root);
       
        int[][] answer = new int[2][n];
        for (int i=0; i<n; i++) {
            answer[0][i] = preorderList.get(i);
            answer[1][i] = postorderList.get(i);
        }
        
        return answer;
    }
   	private void preorder(Node node) {
        if (node == null) return; 
        preorderList.add(node.num);
        preorder(node.left);
        preorder(node.right);
    } 
   	private void postorder(Node node) {
        if (node == null) return; 
        postorder(node.left);
        postorder(node.right);
        postorderList.add(node.num);
    } 
    
    private void insertNode(Node parent, Node child) {
        if (child.x < parent.x) {
            if (parent.left == null) 
                parent.left = child;
            else 
                insertNode(parent.left, child);
        } else {
            if (parent.right == null)
                parent.right = child; 
            else 
                insertNode(parent.right, child);
        }
    }
}
