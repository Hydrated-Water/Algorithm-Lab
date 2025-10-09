package com.crim.web.lab.algorithm.leetcode.problem.p152;

class Solution {
    
    static int result;
    
    public int maxProduct(int[] nums) {
        result = Integer.MIN_VALUE;
        int negCount = 0;
        int i = 0;
        int start = 0;
        int product = 1;
        while (i < nums.length) {
            if (nums[i] == 0) {
                result = Math.max(result, 0);
                process(nums, start, i - 1, negCount, product);
                i++;
                negCount = 0;
                start = i;
                product = 1;
                continue;
            }
            product *= nums[i];
            if (nums[i] < 0) negCount++;
            i++;
        }
        process(nums, start, i - 1, negCount, product);
        return result;
    }
    
    void process(int[] nums, int start, int end, int negCount, int product) {
        if (end - start < 0) return;
        if (negCount % 2 == 0) {
            result = Math.max(product, result);
            return;
        }
        if (negCount == 1) {
            if (end - start == 0) {
                result = Math.max(result, product);
                return;
            }
            int p = 1;
            for (int i = start; i <= end; i++) {
                if (nums[i] < 0) break;
                p *= nums[i];
            }
            result = Math.max(result, p);
            p = 1;
            for (int i = end; i >= start; i--) {
                if (nums[i] < 0) break;
                p *= nums[i];
            }
            result = Math.max(result, p);
            return;
        }
        int p = 1;
        for (int i = start; i <= end; i++) {
            p *= nums[i];
            if (nums[i] < 0) break;
        }
        result = Math.max(result, product / p);
        p = 1;
        for (int i = end; i >= start; i--) {
            p *= nums[i];
            if (nums[i] < 0) break;
        }
        result = Math.max(result, product / p);
    }
}
