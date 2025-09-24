package com.crim.web.lab.algorithm.leetcode.problem.p53;

import java.util.Arrays;
import java.util.Random;


public class MaximumSubarray implements Runnable {
    @Override
    public void run() {
        Random random = new Random();
        int[] arr = new int[random.nextInt(20) + 1];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(20) - 10;
        }
        System.out.println(Arrays.toString(arr));
        System.out.println(new Solution().maxSubArray(arr));
    }
}
