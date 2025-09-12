package com.crim.web.lab.algorithm.leetcode.problem.p239;

import java.util.TreeMap;


class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        int[] result = new int[nums.length - k + 1];
        TreeMap<Integer, Integer> map = new TreeMap<>();
        for (int i = 0; i < nums.length; i++) {
            map.compute(nums[i], (key, count) -> count == null ? 1 : count + 1);
            if (i >= k) {
                Integer count = map.get(nums[i - k]);
                Integer ignore = count == 1 ? map.remove(nums[i - k]) : map.put(nums[i - k], count - 1);
            }
            if (i >= k - 1) result[i - k + 1] = map.lastKey();
        }
        return result;
    }
}
