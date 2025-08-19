class Solution {
    public long solution(int w, int h) {
        long w1 = w;
        long h1 = h;
        return w1 * h1 - (w1+h1 - gcd(w1,h1));
                    
       
    }
    
    public long gcd(long w,long h) {
        if (w % h == 0) {
            return h;
        } else {
            return gcd(h,w%h);
        }
    }
}
