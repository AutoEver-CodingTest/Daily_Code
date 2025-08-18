
import java.io.*;
import java.util.*;
import java.lang.*;

public class Main{

    static int checkArr[];
    static int nowArr[];
    static int checkSecret;

    public static void main(String[] args) throws IOException{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());

        int S = Integer.parseInt(st.nextToken());
        int P = Integer.parseInt(st.nextToken());
        int result = 0;
        checkSecret = 0;

        char[] sarr = new char[S];
        checkArr = new int[4];
        nowArr = new int[4];

        String mm = br.readLine();

        for (int i = 0 ; i<S; i++){
            sarr[i] = mm.charAt(i);
        }

        st = new StringTokenizer(br.readLine());

        for(int p = 0; p<4; p++){

            checkArr[p] = Integer.parseInt(st.nextToken());
            if(checkArr[p] == 0){
                checkSecret++;
            }
        }

        for(int m = 0; m<P; m++){
            Add(sarr[m]);
        }

        if (checkSecret == 4){
            result++;
        }

        for(int mmm = P; mmm<S; mmm++ ){
            int j = mmm - P;
            Add(sarr[mmm]);
            Remove(sarr[j]);
            if (checkSecret == 4){
                result++;
            }
        }

        bw.write(result+"");
        bw.flush();

    }

    private static void Add(char c){

        switch (c) {
            case 'A':
                nowArr[0]++;
                if (nowArr[0] == checkArr[0]) {
                    checkSecret++;
                }
                break;
            case 'C':
                nowArr[1]++;
                if (nowArr[1] == checkArr[1]){
                    checkSecret++;
                }
                break;
            case 'G':
                nowArr[2]++;
                if (nowArr[2] == checkArr[2]){
                    checkSecret++;
                }
                break;
            case 'T':
                nowArr[3]++;
                if (nowArr[3] == checkArr[3]){
                    checkSecret++;
                }
                break;
        }
    }

    private static void Remove(char c){

        switch (c) {
            case 'A':
                if (nowArr[0] == checkArr[0]){
                    checkSecret--;
                }
                nowArr[0]--;
                break;
            case 'C':
                if (nowArr[1] == checkArr[1]){
                    checkSecret--;
                }
                nowArr[1]--;
                break;
            case 'G':
                if (nowArr[2] == checkArr[2]){
                    checkSecret--;
                }
                nowArr[2]--;
                break;
            case 'T':
                if (nowArr[3] == checkArr[3]){
                    checkSecret--;
                }
                nowArr[3]--;
                break;
        }
    }
}
