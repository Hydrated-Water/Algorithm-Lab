package com.crim.web.lab.algorithm.leetcode.simple.p2;

import java.util.HashMap;


public class Solution {
    public int lengthOfLongestSubstring(String s) {
        int maxResult = -1;
        // 字符 - 索引 键值对
        HashMap<Character, Integer> map = new HashMap<>();
        // 最近最短重复字符索引
        int a = -1;
        int b = 0;
        // 用于计算字符串第一个无重复字符子串
        boolean isFirst = true;
        // 遍历字符串
        for (int j = 0; j < s.length(); j++) {
            char c = s.charAt(j);
            // 找到最近的上一个相同字符索引
            Integer index = map.get(c);
            if (index != null) {
                int i = index;
                // 判断当前子串 i,j 是否存在其他重复字符
                if (i > a) {
                    // 记录当前最大无重复字符串长度
                    maxResult = Math.max(maxResult, j - i);
                    // 更新最近最短重复字符索引
                    a = i;
                    b = j;
                }
            }
            // 更新最近的上一个相同字符索引
            if (isFirst) {
                // 计算第一个无重复字符子串长度
                if (map.put(c, j) != null) {
                    maxResult = Math.max(maxResult, j);
                    isFirst = false;
                }
            }
            else map.put(c, j);
        }
        // 计算数组尾部的最长无重复子串长度
        // 能解决字符串中无重复字符、字符串长度为0的情况
        maxResult = Math.max(maxResult, s.length() - 1 - a);
        return maxResult;
    }
}
