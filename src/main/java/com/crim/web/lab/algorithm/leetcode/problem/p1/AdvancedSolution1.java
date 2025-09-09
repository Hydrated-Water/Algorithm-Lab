package com.crim.web.lab.algorithm.leetcode.problem.p1;

import java.util.Arrays;


class AdvancedSolution1 {
    public int[] twoSum(int[] nums, int target) {
        // 排序
        int[] sortedNums = Arrays.copyOf(nums, nums.length);
        Arrays.sort(sortedNums);
        // 两个找到的元素值
        int a = Integer.MIN_VALUE;
        int b = Integer.MIN_VALUE;
        // 双指针
        {
            int i = 0;
            int j = sortedNums.length - 1;
            boolean isFound = false;
            while (i != j) {
                if (sortedNums[i] + sortedNums[j] == target) {
                    isFound = true;
                    break;
                }
                // 向前移动j指针以寻找可能的元素值
                if (sortedNums[j] > target - sortedNums[i])
                    j--;
                else i++;
            }
            // 保证代码鲁棒性
            if (!isFound) return null;
            a = sortedNums[i];
            b = sortedNums[j];
        }
        // 查找a、b在原数组中的索引，需处理当a == b的情况
        {
            boolean aFound = false;
            boolean bFound = false;
            int aIndex = Integer.MIN_VALUE;
            int bIndex = Integer.MIN_VALUE;
            for (int i = 0; i < nums.length; i++) {
                if (!aFound && nums[i] == a) {
                    aFound = true;
                    aIndex = i;
                }
                else if (!bFound && nums[i] == b) {
                    bFound = true;
                    bIndex = i;
                }
                else if (aFound && bFound) break;
            }
            return new int[]{aIndex, bIndex};
        }
    }
}
