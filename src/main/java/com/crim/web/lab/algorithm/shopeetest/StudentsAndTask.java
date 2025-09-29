package com.crim.web.lab.algorithm.shopeetest;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;


/**
 * 运行时间: 2357ms
 * 内存占用: 37.5MB
 */
public class StudentsAndTask {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(new StudentsAndTask().minAvailableDuration(
                new int[][]{new int[]{10,50},new int[]{50,120},new int[]{140,210}},
                new int[][]{new int[]{0,15},new int[]{40,120}},12)));
    }
    public int[] minAvailableDuration(int[][] A, int[][] B, int duration) {
        /// 排序 <时间，权重>
        TreeMap<Integer, Integer> map = new TreeMap<>();
        for (int[] a : A) {
            map.merge(a[0], 1, Integer::sum);
            map.merge(a[1], -1, Integer::sum);
        }
        for (int[] b : B) {
            map.merge(b[0], 1, Integer::sum);
            map.merge(b[1], -1, Integer::sum);
        }
        /// 遍历
        int weight = 0;
        int start = 0;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            int newWeight = weight + entry.getValue();
            if (weight < 2 && newWeight >= 2) start = entry.getKey();
            else if (weight >= 2 && newWeight < 2) {
                int end = entry.getKey();
                if (end - start >= duration) return new int[]{start, start + duration};
            }
            weight = newWeight;
        }
        return new int[0];
    }
}
