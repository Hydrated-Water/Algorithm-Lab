package com.crim.web.lab.algorithm.leetcode.problem.p239;

class AdvancedSolution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        int[] result = new int[nums.length - k + 1];
        int m = Integer.MIN_VALUE;
        int c = 0;
        /// 初始扫描
        for (int i = 0; i < k; i++) {
            if (nums[i] > m) {
                m = nums[i];
                c = 1;
            }
            else if (nums[i] == m) {
                c++;
            }
        }
        result[0] = m;
        /// 后续遍历
        for (int i = k; i < nums.length; i++) {
            if (nums[i - k] == m) c--;
            if (nums[i] > m) {
                m = nums[i];
                c = 1;
            }
            else if (nums[i] == m) {
                c++;
            }
            if (c == 0) {
                m = Integer.MIN_VALUE;
                for (int j = i - k + 1; j <= i; j++) {
                    if (nums[j] > m) {
                        m = nums[j];
                        c = 1;
                    }
                    else if (nums[j] == m) {
                        c++;
                    }
                }
            }
            result[i - k + 1] = m;
        }
        return result;
    }
}
