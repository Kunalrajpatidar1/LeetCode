class Solution {
public:
    int peakValleyDifference(vector<int>& nums) {
        int peaks = 0;
        int valleys = 0;

        for (int i = 1; i + 1 < (int)nums.size(); ++i) {
            if (nums[i] > nums[i - 1] && nums[i] > nums[i + 1]) {
                peaks += nums[i];
            } else if (nums[i] < nums[i - 1] && nums[i] < nums[i + 1]) {
                valleys += nums[i];
            }
        }

        return peaks - valleys;
    }
};
