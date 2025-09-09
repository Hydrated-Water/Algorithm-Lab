package com.crim.web.lab.algorithm.leetcode.problem.p1;

import java.util.HashMap;


public class AdvancedSolution4 {
    public int[] twoSum(int[] nums, int target) {
        // 建立哈希表，使得键为元素值，值为数组索引
        HashMap<Integer, Integer> map = new HashMap<>(nums.length * 2);
        for (int i = 0; i < nums.length; i++) {
            Integer index = map.get(target - nums[i]);
            // 这里总能正确处理多个值相同的情况，因为i不可能等于index
            if (index != null) {
                return new int[]{i, index};
            }
            map.put(nums[i], i);
        }
        return null;
    }
}
