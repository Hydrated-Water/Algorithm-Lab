package com.crim.web.lab.algorithm.leetcode.problem.p560;

import java.util.HashMap;


class AdvancedSolution2 {
    public int subarraySum(int[] nums, int k) {
        int result = 0;
        /// sums[i] 即为 nums 前 i+1 个元素的和
        int[] sums = new int[nums.length];
        {
            int sum = 0;
            for (int i = 0; i < nums.length; i++) {
                sum += nums[i];
                sums[i] = sum;
            }
        }
        /// 哈希表处理 sums 到 counts，
        /// counts[i]的值即为sums的0到i-2子数组中值为sums[i]-k的元素的数量
        int[] counts = new int[sums.length];
        {
            HashMap<Integer, Integer> map = new HashMap<>(counts.length * 2);
            for (int i = 0; i < sums.length - 2; i++) {
                Integer sumCount = map.get(sums[i]);
                sumCount = sumCount == null ? 1 : sumCount + 1;
                map.put(sums[i], sumCount);
                
                Integer targetCount = map.get(sums[i + 2] - k);
                counts[i + 2] = targetCount == null ? 0 : targetCount;
            }
        }
        /// 查看前 2 个元素
        if (nums.length >= 1) {
            if (nums[0] == k) result++;
        }
        if (nums.length >= 2) {
            if (nums[1] == k) result++;
            if (sums[1] == k) result++;
        }
        /// 查看之后的元素
        for (int i = 2; i < nums.length; i++) {
            if (nums[i] == k) result++;
            if (sums[i] == k) result++;
            result += counts[i];
        }
        return result;
    }
}
