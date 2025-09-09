package com.crim.web.lab.algorithm.leetcode.problem.p49;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        // 使用哈希表存储和查找，以每个字母出现次数为键，以存储被分组的字符串列表为值
        HashMap<List<Integer>, List<String>> map = new HashMap<>();
        for (String str : strs) {
            List<Integer> letterCount = this.getLetterCount(str);
            List<String> container = map.get(letterCount);
            if (container == null) {
                container = new ArrayList<>();
                map.put(letterCount, container);
            }
            container.add(str);
        }
        return map.values().stream().toList();
    }
    
    // 计算字符串中每个小写字母的出现次数
    private List<Integer> getLetterCount(String s) {
        int[] letterCount = new int[26];
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            letterCount[c - 'a']++;
        }
        List<Integer> list = new ArrayList<>(letterCount.length);
        for (int count : letterCount) {
            list.add(count);
        }
        return list;
    }
}
