package com.crim.web.lab.algorithm.leetcode.problem.p416;

import java.util.Arrays;


class Solution {
    public boolean canPartition(int[] nums) {
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        if (sum % 2 == 1) return false;
        
        int target = sum / 2;
        boolean[] dp = new boolean[target + 1];
        dp[0] = true;
        
        for (int num : nums) {
            for (int k = target - num; k >= 0; k--) {
                if (dp[k]) dp[k + num] = true;
            }
            if (dp[target]) return true;
        }
        
        return false;
    }
}
