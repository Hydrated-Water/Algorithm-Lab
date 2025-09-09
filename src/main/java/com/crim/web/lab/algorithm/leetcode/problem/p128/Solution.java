package com.crim.web.lab.algorithm.leetcode.problem.p128;

import java.util.HashSet;


class Solution {
    public int longestConsecutive(int[] nums) {
        int result = 0;
        HashSet<Integer> set = new HashSet<>(nums.length * 2);
        for (int i : nums) {
            set.add(i);
        }
        for (int c = 0; c < nums.length; c++) {
            int i = nums[c];
            if (!set.remove(i)) continue;
            
            int len = 1;
            int n = i;
            while (true) {
                if (n == Integer.MAX_VALUE) break;
                n++;
                if (set.remove(n)) len++;
                else break;
            }
            n = i;
            while (true) {
                if (n == Integer.MIN_VALUE) break;
                n--;
                if (set.remove(n)) len++;
                else break;
            }
            result = Math.max(result, len);
        }
        return result;
    }
}
