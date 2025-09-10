package com.crim.web.lab.algorithm.leetcode.problem.p15;

public class ThreeSum implements Runnable {
    
    @Override
    public void run() {
        System.out.println(new Solution().threeSum(new int[]{-1, 0, 1, 2, -1, -4}));
    }
}
