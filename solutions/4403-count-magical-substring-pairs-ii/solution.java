class Solution {
    public long countMagicalPairs(String s) {
        String w = s;
        int len = w.length();

        int[] following = new int[len];
        int[] earlier = new int[len];
        int[] seenAt = new int[26];
        java.util.Arrays.fill(seenAt, -1);
        for (int idx = 0; idx < len; idx++) {
            earlier[idx] = seenAt[w.charAt(idx) - 'a'];
            seenAt[w.charAt(idx) - 'a'] = idx;
        }
        java.util.Arrays.fill(seenAt, len);
        for (int idx = len - 1; idx >= 0; idx--) {
            following[idx] = seenAt[w.charAt(idx) - 'a'];
            seenAt[w.charAt(idx) - 'a'] = idx;
        }

        int[][] suffixBuckets = new int[27][len];

        int[] lowBound = new int[28];
        java.util.Arrays.fill(lowBound, len);
        int kinds = 0;

        for (int cut = len - 1; cut >= 0; cut--) {
            if (cut + 1 < len) {
                int spot = cut + 1;
                int again = following[spot];
                int[] snapshot = lowBound.clone();

                if (again == len) {
                    kinds++;
                    for (int level = kinds; level >= 2; level--) {
                        lowBound[level] = snapshot[level - 1];
                    }
                    lowBound[1] = spot;
                } else {
                    lowBound[1] = spot;
                    for (int level = 2; level <= kinds; level++) {
                        int fromRight = snapshot[level] >= again
                                ? snapshot[level]
                                : (snapshot[level + 1] > again ? again : len);
                        int fromLeft = snapshot[level - 1] < again ? snapshot[level - 1] : len;
                        lowBound[level] = Math.min(fromRight, fromLeft);
                    }
                }
            }

            for (int level = 1; level <= 26; level++) {
                suffixBuckets[level][cut] = level <= kinds ? lowBound[level + 1] - lowBound[level] : 0;
            }
        }

        int[] highBound = new int[28];
        java.util.Arrays.fill(highBound, -1);
        int kindsNow = 0;
        long total = 0;

        for (int cut = 0; cut < len; cut++) {
            int edge = cut;
            int before = earlier[edge];
            int[] snapshot = highBound.clone();

            if (before == -1) {
                kindsNow++;
                for (int level = kindsNow; level >= 2; level--) {
                    highBound[level] = snapshot[level - 1];
                }
                highBound[1] = edge;
            } else {
                highBound[1] = edge;
                for (int level = 2; level <= kindsNow; level++) {
                    int fromOld;
                    if (snapshot[level] <= before) {
                        fromOld = snapshot[level];
                    } else if (snapshot[level + 1] < before) {
                        fromOld = before;
                    } else {
                        fromOld = -1;
                    }
                    int fromShift = snapshot[level - 1] > before ? snapshot[level - 1] : -1;
                    highBound[level] = Math.max(fromOld, fromShift);
                }
            }

            for (int level = 1; level <= 26; level++) {
                int leftWays = level <= kindsNow ? highBound[level] - highBound[level + 1] : 0;
                if (leftWays > 0) {
                    total += (long) leftWays * suffixBuckets[level][cut];
                }
            }
        }

        return total;
    }
}
