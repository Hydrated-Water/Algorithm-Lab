package com.crim.web.lab.algorithm.leetcode.problem.p1;

import java.util.HashMap;


class AdvancedSolution3 {
    public int[] twoSum(int[] nums, int target) {
        // 建立哈希表，使得键为元素值，值为数组索引（假设多个值相同的情况很少见，不在此处处理）
        HashMap<Integer, Integer> map = new HashMap<>(nums.length * 2);
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], i);
        }
        // 遍历数组，查找对应的target-nums[i]，在此处处理可能的nums[i]等于target-nums[i]的情况
        for (int i = 0; i < nums.length; i++) {
            Integer index = map.get(target - nums[i]);
            // 当两值target-nums[i]与nums[i]相等时，如果nums中确实有多个相同的元素值，
            // 那么在map.put(nums[i],i)操作中位置靠后后者将覆盖前者，而此处i是从数组头开始遍历，
            // 那么这里总能正确处理多个值相同的情况
            if (index != null && index != i) {
                return new int[]{i, index};
            }
        }
        return null;
    }
}
