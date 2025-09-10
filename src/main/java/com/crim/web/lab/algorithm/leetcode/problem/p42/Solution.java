package com.crim.web.lab.algorithm.leetcode.problem.p42;

class Solution {
    public int trap(int[] height) {
        /// 返回值
        int v = 0;
        /// 找到输入数组的最大值和最大值第一次和最后一次出现的索引
        int maxHeight = 0;
        int r = 0;
        int s = 0;
        for (int i = 0; i < height.length; i++) {
            if (height[i] > maxHeight) {
                maxHeight = height[i];
                r = i;
                s = i;
            }
            else if (height[i] == maxHeight) {
                s = i;
            }
        }
        /// 对于 height[r] 之前的局部数组
        {
            int p = 0;
            int q = p;
            while (true) {
                q++;
                if (q >= r) break;
                if (height[q] >= height[p]) {
                    p = q;
                    continue;
                }
                v += height[p] - height[q];
            }
        }
        /// 对于第一个和最后一个 maxHeight 之间的局部数组
        {
            int q = r;
            while (true) {
                q++;
                if (q >= s) break;
                v += maxHeight - height[q];
            }
        }
        /// 对于最后一个 maxHeight 之后的局部数组
        {
            int p = height.length - 1;
            int q = p;
            while (true) {
                q--;
                if (q <= s) break;
                if (height[q] >= height[p]) {
                    p = q;
                    continue;
                }
                v += height[p] - height[q];
            }
        }
        return v;
    }
}
