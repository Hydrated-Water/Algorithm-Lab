package com.crim.web.lab.algorithm.leetcode.problem.p76;

import java.util.HashMap;
import java.util.Map;


class AdvancedSolution {
    public String minWindow(String s, String t) {
        /// t 字符计数
        Map<Character, Integer> tCount = new HashMap<>();
        for (int i = 0; i < t.length(); i++) {
            char c = t.charAt(i);
            tCount.merge(c, 1, Integer::sum);
        }
        /// 初始化当前子串字符计数
        Map<Character, Integer> count = new HashMap<>();
        for (Character c : tCount.keySet()) {
            count.put(c, 0);
        }
        /// 找到第一个子串
        int i = 0;
        int p = -1;
        int u = 0;
        while (u < tCount.size()) {
            p++;
            if (p >= s.length()) return "";
            char c = s.charAt(p);
            Integer k = count.get(c);
            if (k != null) {
                k++;
                count.put(c, k);
                if (k.equals(tCount.get(c))) u++;
            }
        }
        /// 找最小子串
        int start = i;
        int end = p;
        while (true) {
            char c = s.charAt(i);
            Integer k = count.get(c);
            if (k == null) {
                i++;
            }
            else if (k > tCount.get(c)) {
                count.put(c, k - 1);
                i++;
            }
            else {
                p++;
                if (p >= s.length()) return s.substring(start, end + 1);
                char cp = s.charAt(p);
                Integer kp = count.get(cp);
                if (kp != null) {
                    count.put(cp, kp + 1);
                }
            }
            
            if (p - i + 1 < end - start + 1) {
                start = i;
                end = p;
            }
        }
    }
}
