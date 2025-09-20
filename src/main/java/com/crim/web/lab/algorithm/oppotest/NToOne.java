package com.crim.web.lab.algorithm.oppotest;

import java.util.HashSet;
import java.util.Scanner;


/**
 * 对于整数 n，当 n 为偶数时，使 n 除 2，否则使 n 乘 3 后加 1
 * <p/>
 * 输入一个数，判断它是否能经过一定次数的上述操作后变为 1，如果能，输出 Yes 和最少操作次数，否则输出 No
 * <p/>
 * n <= 10^18
 * <p/>
 * 输入示例
 * 100
 * 输出示例
 * Yes
 * 25
 * <p/>
 * @since 2025/0913 O某P某2025秋招笔试第一题
 * <p/>
 * 通过率 96.67%
 */
public class NToOne {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long n = scanner.nextLong();
        long limit = (Long.MAX_VALUE - 1) / 3;
        HashSet<Long> set = new HashSet<>();
        set.add(n);
        int count = 0;
        boolean flag = false;
        
        while (true) {
            if (n == 1) {
                flag = true;
                break;
            }
            
            if (n % 2 == 0) {
                n = n / 2;
                count++;
            }
            else {
                if (n > limit) break;
                n = n * 3 + 1;
                count++;
            }
            
            if (!set.add(n)) break;
        }
        
        if (flag) {
            System.out.println("Yes");
            System.out.println(count);
        }
        else System.out.println("No");
    }
}
