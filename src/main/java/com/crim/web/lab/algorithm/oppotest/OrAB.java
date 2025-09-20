package com.crim.web.lab.algorithm.oppotest;

import java.util.HashSet;
import java.util.Scanner;


/**
 * 输入 t 组数据，每组数据输入 n 表示 数组 a1 ... an 和数组 b1 ... bn 的长度，然后输入输入 a、b。
 * 对于任意前缀 a1 ... ai （ 1<=i<=n）存在一个子序列，其OR值恰好等于 bi。
 * 如果这组数据满足上述条件，输出Yes，否则输出No
 * <p/>
 * n <= 10^6
 * ai <= 2^12
 * bi <= 2^12
 * <p/>
 * 输入示例
 * 2\n // t
 * 3\n // n
 * 1 2 3\n  // a1 ... a3
 * 1 3 3\n  // b1 ... b3
 * 2\n
 * 1 2\n
 * 2 1\n
 * 输出示例
 * Yes\n
 * No\n
 * <p/>
 * @since 2025/0913 O某P某2025秋招笔试第三题
 * 大失败
 * <p/>
 * 以下代码存在问题，其搞混了“子数组”和“子序列”的定义
 */
public class OrAB {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int t = scanner.nextInt();
        for (int i = 0; i < t; i++) {
            int n = scanner.nextInt();
            int[] a = new int[n];
            int[] b = new int[n];
            for (int j = 0; j < n; j++) {
                a[j] = scanner.nextInt();
            }
            for (int j = 0; j < n; j++) {
                b[j] = scanner.nextInt();
            }
            System.out.println(check(a, b) ? "Yes" : "No");
        }
    }
    
    static boolean check(int[] a, int[] b) {
        HashSet<Integer> set = new HashSet<>();
        for (int i = 0; i < b.length; i++) {
            int or = 0;
            for (int j = i; j >= 0; j--) {
                or = or | a[j];
                set.add(or);
            }
            if (!set.contains(b[i])) {
                return false;
            }
        }
        return true;
    }
}
