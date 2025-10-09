package com.crim.web.lab.algorithm.leetcode.problem.p75;

class Solution {
    public void sortColors(int[] nums) {
        int p = -1;
        int q = nums.length;
        int i = 0;
        while (true) {
            if (nums[i] == 0) {
                p++;
                swap(nums, p, i);
                i++;
            }
            else if (nums[i] == 2) {
                q--;
                swap(nums, q, i);
            }
            else if (nums[i] == 1) {
                i++;
            }
            
            if (i >= q) break;
        }
    }
    
    void swap(int[] nums, int i, int j) {
        int swap = nums[j];
        nums[j] = nums[i];
        nums[i] = swap;
    }
}
