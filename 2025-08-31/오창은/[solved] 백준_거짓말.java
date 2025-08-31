import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int N; // 사람 수
    static int M; // 파티 수
    static int truthCount; // 진실을 아는 사람 수
    static int[] truthArr; // 진실을 아는 사람

    static int[] parent;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] parts = br.readLine().split(" ");

        N =  Integer.parseInt(parts[0]);
        M =  Integer.parseInt(parts[1]);

        parts = br.readLine().split(" ");
        truthCount = Integer.parseInt(parts[0]);
        truthArr = new int[truthCount];
        for (int i=0; i<truthCount; i++) {
            truthArr[i] = Integer.parseInt(parts[i+1]);
        }

        List<List<Integer>> parties = new ArrayList<>();
        while (M-- > 0) {
            parts = br.readLine().split(" ");
            int n = Integer.parseInt(parts[0]);
            List<Integer> party = new ArrayList<>();
            for (int i=0; i<n; i++) {
                party.add(Integer.parseInt(parts[i + 1]));
            }
            parties.add(party);
        }
        parent = new int[N + 1];
        for (int i=1; i<=N; i++) {
            parent[i] = i;
        }

        for (List<Integer> party: parties) {
            for (int i=1; i<party.size(); i++) {
                union(party.get(0), party.get(i));
            }
        }

        Set<Integer> truthRoots = new HashSet<>();
        for (int t: truthArr) {
            truthRoots.add(find(t));
        }

        int answer = 0;
        for (List<Integer> party: parties) {
            boolean canLie = true;
            for (int p: party) {
                if (truthRoots.contains(find(p))) {
                    canLie = false;
                    break;
                }
            }
            if (canLie)
                answer++;
        }
        System.out.println(answer);
    }

    public static int find(int x) {
        if (parent[x] == x) return x;
        return parent[x] = find(parent[x]);
    }
    public static void union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);

        if (rootA > rootB) {
            parent[rootA] = rootB;
        } else {
            parent[rootB] = rootA;
        }
    }
}