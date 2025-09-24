package com.crim.web.lab.algorithm.leetcode.problem.p56;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;


class Solution {
    public int[][] merge(int[][] intervals) {
        // 排序并赋予权重
        TreeMap<Integer, Integer> map = new TreeMap<>();
        for (int[] ivl : intervals) {
            map.merge(ivl[0], 1, Integer::sum);
            map.merge(ivl[1], -1, Integer::sum);
        }
        // 遍历
        ArrayList<int[]> result = new ArrayList<>(intervals.length);
        int weight = 0;
        int start = -1;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            int key = entry.getKey();
            int value = entry.getValue();
            if (weight == 0) {
                // 单独的 start == end 区间
                if (value == 0) {
                    result.add(new int[]{key, key});
                }
                // 开始一个新区间
                else {
                    weight += value;
                    start = key;
                }
            }
            else {
                weight += value;
                // 结束一个区间
                if (weight == 0) {
                    result.add(new int[]{start, key});
                }
            }
        }
        return result.toArray(new int[0][0]);
    }
}
