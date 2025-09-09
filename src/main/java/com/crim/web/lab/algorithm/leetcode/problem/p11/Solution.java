package com.crim.web.lab.algorithm.leetcode.problem.p11;

class Solution {
    public int maxArea(int[] height) {
        if (height.length < 2) return 0;
        int i = 0;
        int j = height.length - 1;
        int p = height[i] <= height[j] ? i : j;
        int max = calArea(i, j, height);
        while (i < j) {
            if (height[i] <= height[j]) {
                if (height[p] <= height[i]) {
                    p++;
                    if (p >= j) break;
                    continue;
                }
                int area_ip = calArea(i, p, height);
                int area_pj = calArea(p, j, height);
                int currentMax = Math.max(area_ip, area_pj);
                max = Math.max(max, currentMax);
                i = p;
                p = height[i] <= height[j] ? i : j;
            }
            else {
                if (height[p] <= height[j]) {
                    p--;
                    if (p <= i) break;
                    continue;
                }
                int area_ip = calArea(i, p, height);
                int area_pj = calArea(p, j, height);
                int currentMax = Math.max(area_ip, area_pj);
                max = Math.max(max, currentMax);
                j = p;
                p = height[i] <= height[j] ? i : j;
            }
        }
        return max;
    }
    
    private int calArea(int a, int b, int[] height) {
        return Math.min(height[a], height[b]) * (b - a);
    }
}
