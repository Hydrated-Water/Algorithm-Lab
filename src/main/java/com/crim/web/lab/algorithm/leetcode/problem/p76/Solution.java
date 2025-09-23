package com.crim.web.lab.algorithm.leetcode.problem.p76;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


class Solution {
    public String minWindow(String s, String t) {
        /// 对 t 进行计数
        Map<Character, Integer> tCount = new HashMap<>();
        for (int i = 0; i < t.length(); i++) {
            char c = t.charAt(i);
            tCount.merge(c, 1, Integer::sum);
        }
        /// 对 s 进行哈希
        Map<Character, List<Integer>> sMap = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (tCount.containsKey(c)) {
                List<Integer> list = sMap.get(c);
                if (list == null) {
                    list = new ArrayList<>();
                    sMap.put(c, list);
                }
                list.add(i);
            }
        }
        // 提高鲁棒性
        if (sMap.isEmpty() || tCount.size() != sMap.size()) return "";
        
        int u = Integer.MIN_VALUE;
        /// 预测索引集
        Map<Character, Integer> preMap = new HashMap<>();
        for (Map.Entry<Character, List<Integer>> entry : sMap.entrySet()) {
            char c = entry.getKey();
            List<Integer> list = entry.getValue();
            int k = tCount.get(c);
            int q = k - 1;
            if (q >= list.size()) return "";
            preMap.put(c, q);
            u = Math.max(u, list.get(q));
        }
        
        String result = s.substring(0, u + 1);
        for (int i = 1; i <= s.length(); i++) {
            char c = s.charAt(i - 1);
            Integer q = preMap.get(c);
            if (q != null) {
                q++;
                List<Integer> list = sMap.get(c);
                if (q >= list.size()) return result;
                preMap.put(c, q);
                u = Math.max(u, list.get(q));
            }
            if (u - i + 1 < result.length()) result = s.substring(i, u + 1);
        }
        
        return result;
    }
}
