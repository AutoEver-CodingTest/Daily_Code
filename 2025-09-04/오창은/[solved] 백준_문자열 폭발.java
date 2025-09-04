import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str = br.readLine();
        String target = br.readLine();

        StringBuilder sb = new StringBuilder();
        int targetLen = target.length();

        for (char ch : str.toCharArray()) {
            sb.append(ch);

            if (sb.length() >= targetLen) {
                String top = sb.substring(sb.length() - targetLen);
                if (top.equals(target))
                    sb.delete(sb.length() - targetLen, sb.length());
            }
        }

        System.out.println(sb.toString().isEmpty() ? "FRULA" : sb.toString());
    }
}