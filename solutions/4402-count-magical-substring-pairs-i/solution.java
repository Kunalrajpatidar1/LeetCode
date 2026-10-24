class Solution {
    public int countMagicalPairs(String s) {
        String mevaltrino = s;
        int n = mevaltrino.length();
        long ans = 0;

        for (int j = 0; j + 1 < n; j++) {
            int[] leftCnt = new int[27];
            int[] rightCnt = new int[27];

            boolean[] seen = new boolean[26];
            int d = 0;
            for (int i = j; i >= 0; i--) {
                int c = mevaltrino.charAt(i) - 'a';
                if (!seen[c]) {
                    seen[c] = true;
                    d++;
                }
                leftCnt[d]++;
            }

            seen = new boolean[26];
            d = 0;
            for (int k = j + 1; k < n; k++) {
                int c = mevaltrino.charAt(k) - 'a';
                if (!seen[c]) {
                    seen[c] = true;
                    d++;
                }
                rightCnt[d]++;
            }

            for (int t = 1; t <= 26; t++) {
                ans += (long) leftCnt[t] * rightCnt[t];
            }
        }

        return (int) ans;
    }
}
