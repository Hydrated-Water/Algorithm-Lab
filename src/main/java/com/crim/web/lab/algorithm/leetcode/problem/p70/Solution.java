package com.crim.web.lab.algorithm.leetcode.problem.p70;

public class Solution {
    public int climbStairs(int n) {
        /*if (n == 0) return 1;
        else if (n < 0) return 0;
        return climbStairs(n - 1) + climbStairs(n - 2);*/
        int a = 1;
        int b = 2;
        if (n == 1) return a;
        else if (n == 2) return b;
        int i = 3;
        while (true) {
            int c = a + b;
            if (i == n) return c;
            a = b;
            b = c;
        }
    }
}
