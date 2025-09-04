package com.crim.web.lab.algorithm.leetcode.simple.p1;


import java.util.Arrays;


class Solution {
    public int[] twoSum(int[] nums, int target) {
        // 排序
        int[] sortedNums = Arrays.copyOf(nums, nums.length);
        Arrays.sort(sortedNums);
        // 两个找到的元素值
        int a = Integer.MIN_VALUE;
        int b = Integer.MIN_VALUE;
        // 遍历sortedNums，使用二分查找找到target-sortedNums[i]所在的位置
        for (int i = 0; i < sortedNums.length; i++) {
            // 当 index < 0 或 index == i 时无效
            int index = Arrays.binarySearch(sortedNums, target - sortedNums[i]);
            if (index < 0 || index == i) continue;
            a = sortedNums[i];
            b = sortedNums[index];
            break;
        }
        // 查找a、b在原数组中的索引，需处理当a == b的情况
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
