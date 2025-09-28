package com.crim.web.lab.algorithm.leetcode.problem.p198;

class Solution {
    public int rob(int[] nums) {
        if (nums.length == 0) return 0;
        else if (nums.length == 1) return nums[0];
        else if (nums.length == 2) return Math.max(nums[0], nums[1]);
        else if (nums.length == 3) return Math.max(nums[0] + nums[2], nums[1]);
        int[] f = new int[nums.length];
        f[0] = nums[0];
        f[1] = nums[1];
        f[2] = nums[0] + nums[2];
        for (int i = 3; i < f.length; i++) {
            f[i] = Math.max(f[i - 2] + nums[i], f[i - 3] + nums[i]);
        }
        return Math.max(f[f.length - 1], f[f.length - 2]);
    }
}
