class Solution {
    public long countSubarrays(int[] nums, int minK, int maxK) {
        long valid = 0;
        int lastBad = -1;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] < minK || nums[i] > maxK) {
                lastBad = i;
            }
            valid += i - lastBad;
        }

        return valid;
    }
}
