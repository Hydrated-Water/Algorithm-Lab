package com.crim.web.lab.algorithm.nowcoder.jd;

import java.util.Scanner;


/**
 * 牛牛与切割机
 * <p/>
 * 对序列A1 ... An，切割为两个不想交的非空序列 A1 ... Ap 和 Ap+1 ... An，切割代价为两序列和的乘积，找到最小的代价
 * <p/>
 * 第一行输入n表示序列长度 2 <= n <= 10^6
 * 第二行输入n个整数元素值Ai表示序列， -10^3 <= Ai <= 10^3
 * 输出一个整数表示代价最小
 * <p/>
 * 输入例子：
 * 5
 * 1 2 3 4 5
 * 输出例子：
 * 14
 * <p/>
 * 输入例子：
 * 4
 * 2 1 3 4
 * 输出例子：
 * 16
 * <p/>
 * 结果：
 * 通过全部用例
 * 运行时间 1016ms
 * 占用内存 28096KB
 * 解题用时 约8分钟
 *
 * @since 2025/09/09 牛客 京东 2024年 秋招 算法第一题
 */
public class CowAndCutting {
    
    static int n;
    
    static int[] nums;
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        /// 总大小
        long sum = 0;
        /// 最小代价
        long cost = Long.MAX_VALUE;
        /// 输入处理
        n = scanner.nextInt();
        nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = scanner.nextInt();
            // 计算总大小
            sum += nums[i];
        }
        /// 寻找最小代价
        /// 两个序列的大小
        long sumHead = nums[0];
        long sumTail = sum - sumHead;
        cost = Math.min(cost, sumHead * sumTail);
        // 第一个元素必定属于第一个序列，最后一个元素必定属于第二个序列
        for (int i = 1; i < n - 1; i++) {
            sumHead += nums[i];
            sumTail -= nums[i];
            cost = Math.min(cost, sumHead * sumTail);
        }
        
        System.out.println(cost);
    }
}
