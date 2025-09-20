package com.crim.web.lab.algorithm.qyftest;

import java.util.Arrays;
import java.util.Scanner;


public class WorkAndTwoGroup {
    public static void main(String[] args) {
        // System.out.println(1 << 2); // 4
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] works = new int[n];
        for (int i = 0; i < n; i++) {
            works[i] = scanner.nextInt();
        }
        
        /*Arrays.sort(works);
        int a = 0;
        int b = 0;
        for (int i = works.length - 1; i >= 0; i--) {
            if (a >= b) b += works[i];
            else a += works[i];
        }
        if (a >= b) System.out.println(a + " " + b);
        else System.out.println(b + " " + a);*/
        /// 动态规划不可用？
        int[] workDiffer = new int[1 << (n - 1)];
        for (int i = 0; i < works.length; i++) {
            if (i == 0) {
                workDiffer[0] = works[0];
                continue;
            }
            System.arraycopy(workDiffer, 0, workDiffer, (1 << (i - 1)), (1 << (i - 1)));
            for (int j = 0; j < (1 << (i - 1)); j++) {
                workDiffer[j] -= works[i];
            }
            for (int j = (1 << (i - 1)); j < (1 << i); j++) {
                workDiffer[j] += works[i];
            }
        }
        /// 找最小值
        int min = Integer.MAX_VALUE;
        int minIndex = 0;
        for (int i = 0; i < workDiffer.length; i++) {
            if (workDiffer[i] < min) {
                min = workDiffer[i];
                minIndex = i;
            }
        }
        int a = works[0];
        int b = 0;
        for (int i = 1; i < works.length; i++) {
            if (minIndex % 2 == 0) b += works[i];
            else a += works[i];
            minIndex = minIndex >> 1;
        }
        System.out.println(a+" "+b);
    }
}
