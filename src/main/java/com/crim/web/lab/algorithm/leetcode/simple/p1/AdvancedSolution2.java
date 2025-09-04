package com.crim.web.lab.algorithm.leetcode.simple.p1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


class AdvancedSolution2 {
    public int[] twoSum(int[] nums, int target) {
        // 建立哈希表，使得键为元素值，值为数组索引（注意可能存在多个相同值）
        HashMap<Integer, List<Integer>> map = new HashMap<>(nums.length * 2);
        for (int i = 0; i < nums.length; i++) {
            List<Integer> indexList = map.get(nums[i]);
            if (indexList == null) {
                indexList = new ArrayList<>();
                indexList.add(i);
                map.put(nums[i], indexList);
            }
            else {
                indexList.add(i);
            }
        }
        // 遍历数组，查找对应的target-nums[i]
        for (int i = 0; i < nums.length; i++) {
            List<Integer> indexList = map.get(target - nums[i]);
            if (indexList == null) continue;
            if (nums[i] != target - nums[i]) {
                return new int[]{i, indexList.get(0)};
            }
            else {
                if (indexList.size() < 2) continue;
                if (indexList.get(0) != i)
                    return new int[]{i, indexList.get(0)};
                else return new int[]{i, indexList.get(1)};
            }
        }
        return null;
    }
}
