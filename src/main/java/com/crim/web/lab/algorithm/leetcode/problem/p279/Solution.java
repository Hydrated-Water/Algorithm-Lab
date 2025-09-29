package com.crim.web.lab.algorithm.leetcode.problem.p279;

import java.util.Arrays;


class Solution {
    public int numSquares(int n) {
        int[] f = new int[n + 1];
        /// 使 f(k^2) = 1
        for (int k = 1; ; k++) {
            int u = k * k;
            if (u > n) break;
            f[u] = 1;
        }
        if (f[n] > 0) return f[n];
        /// 动态规划
        for (int i = 1; i <= n; i++) {
            if (f[i] != 0) continue;
            int min = Integer.MAX_VALUE;
            for (int p = 1; p <= i / 2; p++) {
                min = Math.min(min, f[p] + f[i - p]);
            }
            f[i] = min;
        }
        return f[n];
    }
}
