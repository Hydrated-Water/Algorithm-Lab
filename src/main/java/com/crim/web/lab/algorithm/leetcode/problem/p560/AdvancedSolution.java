package com.crim.web.lab.algorithm.leetcode.problem.p560;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


class AdvancedSolution {
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
        /// 哈希表处理 sums
        HashMap<Integer, List<Integer>> map = new HashMap<>(sums.length * 2);
        for (int i = 0; i < sums.length; i++) {
            List<Integer> list = map.get(sums[i]);
            if (list == null) {
                list = new ArrayList<>(3);
                map.put(sums[i], list);
            }
            list.add(i);
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
            List<Integer> list = map.get(sums[i] - k);
            if (list != null) {
                for (int j = 0; j < list.size(); j++) {
                    if (list.get(j) <= i - 2) result++;
                    else break;
                }
            }
        }
        return result;
    }
}
