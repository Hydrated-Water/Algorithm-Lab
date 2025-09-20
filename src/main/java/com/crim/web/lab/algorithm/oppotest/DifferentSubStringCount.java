package com.crim.web.lab.algorithm.oppotest;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Scanner;


/**
 * 输入一个整数n和长度为n的字符串s，判断s中共有几个字符集完全不同的子串。
 * <p/>
 * 解释：子串 abca 和 子串 bcaa 的字符集均是 {a,a,b,c}
 * 输入示例
 * abc
 * 输出示例
 * 6
 * <p/>
 * 输入示例
 * aabbz
 * 输出示例
 * 13
 * <p/>
 * @since 2025/0913 O某P某2025秋招笔试第二题
 * <p/>
 * 通过率100% 用时291ms 内存占用18MB
 */
public class DifferentSubStringCount {
    
    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        String str = scanner.nextLine();
        int count = 1;
        // 最大子串（字符串本身）无需再计算
        for (int len = 1; len <= n - 1; len++) {
            StringSpecial special = new StringSpecial();
            HashSet<StringSpecial> set = new HashSet<>();
            for (int i = 0; i < len; i++) {
                special.charCount[str.charAt(i) - 'a']++;
            }
            set.add(new StringSpecial(special.charCount));
            for (int i = 1; i <= n - len; i++) {
                special.charCount[str.charAt(i - 1) - 'a']--;
                special.charCount[str.charAt(i + len - 1) - 'a']++;
                if (!set.contains(special)) {
                    set.add(new StringSpecial(special.charCount));
                }
            }
            count+=set.size();
        }
        
        System.out.println(count);
    }
    
    static class StringSpecial implements Cloneable {
        int[] charCount;
        
        public StringSpecial() {
            super();
            this.charCount = new int[26];
        }
        
        public StringSpecial(int[] charCount) {
            this.charCount = Arrays.copyOf(charCount, 26);
        }
        
        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            StringSpecial that = (StringSpecial) o;
            return Objects.deepEquals(charCount, that.charCount);
        }
        
        @Override
        public int hashCode() {
            return Arrays.hashCode(charCount);
        }
    }
}
