package com.crim.web.lab.algorithm.leetcode.problem.p53;

import java.util.Arrays;


class Solution {
    public int maxSubArray(int[] nums) {
        // 它的实际长度应小于nums.length
        int[] datas = new int[nums.length];
        int len = 0;
        for (int i = 0; i < nums.length; i++) {
            // 去除头部负数
            if (len == 0) {
                if (nums[i] <= 0) continue;
                else {
                    len++;
                    datas[0] = nums[i];
                }
            }
            else if (len % 2 == 1) {
                if (nums[i] >= 0) datas[len - 1] += nums[i];
                else {
                    len++;
                    datas[len - 1] = nums[i];
                }
            }
            else {
                if (nums[i] <= 0) datas[len - 1] += nums[i];
                else {
                    len++;
                    datas[len - 1] = nums[i];
                }
            }
        }
        
        // nums 只有负数或0的情况
        // 逻辑错误
//        if (len == 0) return Arrays.stream(nums).sum();
        if(len == 0) return Arrays.stream(nums).max().orElse(0);
        // 去除 datas 尾部负数
        if (datas[len - 1] <= 0) len--;
        
        // 遍历 datas
        int max = datas[0];
        int sum = datas[0];
        for (int i = 0; i <= len - 3; i += 2) {
            if (sum + datas[i + 1] < 0) {
                sum = datas[i + 2];
            }
            else {
                sum += datas[i + 1] + datas[i + 2];
            }
            max = Math.max(max, sum);
        }
        
        return max;
    }
}
