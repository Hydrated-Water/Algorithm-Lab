package com.crim.web.lab.algorithm.leetcode.problem.p438;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


class Solution {
    public List<Integer> findAnagrams(String s, String p) {
        /// 结果
        List<Integer> result = new ArrayList<>();
        /// 对字符串p进行计数
        int[] n = new int[26];
        for (int i = 0; i < p.length(); i++) {
            char c = p.charAt(i);
            n[c - 'a']++;
        }
        /// 鲁棒性保证
        if (p.length() > s.length() || p.isEmpty()) return result;
        /// 对字符串s进行倒序遍历计算末尾p.length个字符的计数
        int[] ni = new int[26];
        for (int i = s.length() - 1; i >= s.length() - p.length(); i--) {
            char c = s.charAt(i);
            ni[c - 'a']++;
        }
        /// 匹配
        if (Arrays.equals(n, ni)) {
            result.add(s.length() - p.length());
        }
        /// 计数并匹配
        for (int i = s.length() - p.length() - 1; i >= 0; i--) {
            // 修改计数数组
            ni[s.charAt(i) - 'a']++;
            ni[s.charAt(i + p.length()) - 'a']--;
            // 匹配
            if (Arrays.equals(n, ni)) {
                result.add(i);
            }
        }
        return result;
    }
}
