package com.crim.web.lab.algorithm.leetcode.problem.p300;

public class Solution {
    public int lengthOfLIS(int[] nums) {
        int[] f = new int[nums.length];
        int result = 1;
        for (int i = 0; i < nums.length; i++) {
            int max = 1;
            for (int p = 0; p < i; p++) {
                if (nums[p] < nums[i])
                    max = Math.max(max, f[p] + 1);
            }
            f[i] = max;
            result = Math.max(result, max);
        }
        return result;
    }
}
