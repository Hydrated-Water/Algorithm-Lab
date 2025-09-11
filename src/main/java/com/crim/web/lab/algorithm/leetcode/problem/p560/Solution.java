package com.crim.web.lab.algorithm.leetcode.problem.p560;

class Solution {
    public int subarraySum(int[] nums, int k) {
        int result = 0;
        /// 从长度为 1 开始直到长度为 nums.length 遍历 nums
        int headSum = 0;
        for (int len = 1; len <= nums.length; len++) {
            headSum += nums[len - 1];
            if (headSum == k) result++;
            int sum = headSum;
            for (int i = 1; i <= nums.length - len; i++) {
                sum -= nums[i - 1];
                sum += nums[i + len - 1];
                if (sum == k) result++;
            }
        }
        return result;
    }
}
