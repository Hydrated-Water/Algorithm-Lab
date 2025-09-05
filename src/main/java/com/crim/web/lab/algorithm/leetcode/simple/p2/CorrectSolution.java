package com.crim.web.lab.algorithm.leetcode.simple.p2;

import java.util.HashMap;


class CorrectSolution {
    public int lengthOfLongestSubstring(String s) {
        int maxResult = 0;
        // 当前无重复子串的长度
        int m = 0;
        // 每个字符对应的最近的上一个重复字符的索引
        HashMap<Character, Integer> map = new HashMap<>();
        // 遍历字符串
        for (int j = 0; j < s.length(); j++) {
            // 查询上一个重复字符索引
            char c = s.charAt(j);
            Integer index = map.get(c);
            // 查询上一个重复字符索引是否在当前无重复子串内
            if (index != null && index >= j - m) {
                m = j - index;
            }
            else {
                // 无重复子串长度增加
                m++;
            }
            maxResult = Math.max(maxResult, m);
            map.put(c, j);
        }
        return maxResult;
    }
}
