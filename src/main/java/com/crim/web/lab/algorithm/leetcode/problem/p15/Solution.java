package com.crim.web.lab.algorithm.leetcode.problem.p15;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;


class Solution {
    
    public List<List<Integer>> threeSum(int[] nums) {
        /// 返回值是三元组的列表，注意三元组不可重复
        List<List<Integer>> result = new ArrayList<>();
        /// 去重，此处不应使用如TreeSet这类虽然有排序功能但性能更差的集合容器
        HashSet<Integer> set = new HashSet<>(nums.length * 2);
        for (int num : nums) {
            set.add(num);
        }
        int[] uniqueNums = new int[set.size()];
        {
            int i = 0;
            for (Integer num : set) {
                uniqueNums[i] = num;
                i++;
            }
        }
        /// 构建去重后数组的哈希表，以元素值为键，以元素索引为值
        HashMap<Integer, Integer> map = new HashMap<>(uniqueNums.length * 2);
        for (int i = 0; i < uniqueNums.length; i++) {
            map.put(uniqueNums[i], i);
        }
        /// 对去重数组进行双重循环，注意 i < j < k
        for (int i = 0; i < uniqueNums.length; i++) {
            for (int j = i + 1; j < uniqueNums.length; j++) {
                Integer k = map.get(-(uniqueNums[i] + uniqueNums[j]));
                if (k != null && k > j) {
                    result.add(List.of(uniqueNums[i], uniqueNums[j], uniqueNums[k]));
                }
            }
        }
        
        
        System.out.println(set);// DEBUG
        System.out.println(map);
        System.out.println(Arrays.toString(uniqueNums));
        
        return result;
    }
}
