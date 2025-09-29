package com.crim.web.lab.algorithm.leetcode.problem.p322;

import java.util.Arrays;


class Solution {
    public int coinChange(int[] coins, int amount) {
        Arrays.sort(coins);
        /// 动态规划数组
        int[] f = new int[amount + 1];
        for (int c : coins)
            if (c <= amount) f[c] = 1;
        for (int p = 1; p <= amount; p++) {
            if (f[p] > 0) continue;
            int min = Integer.MAX_VALUE;
            for (int i = 0; i < coins.length; i++) {
                if (p <= coins[i]) break;
                if (f[p - coins[i]] == -1) continue;
                min = Math.min(min, f[p - coins[i]] + 1);
            }
            if (min == Integer.MAX_VALUE) f[p] = -1;
            else f[p] = min;
        }
        return f[amount];
    }
}
