package com.crim.web.lab.algorithm.qyftest;

import java.util.Arrays;
import java.util.Scanner;


/**
 * 100% 62ms 13MB
 */
public class KeyAndBoxes {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String key = scanner.nextLine();
        String[] boxes = scanner.nextLine().split(" ");
        
        /// 对密钥进行字母统计
        int[] keyChars = new int[26];
        for (int i = 0; i < key.length(); i++) {
            keyChars[key.charAt(i) - 'a']++;
        }
        
        /// 匹配所有box
        int result = -1;
        for (int i = 0; i < boxes.length; i++) {
            int[] sChars = new int[26];
            String s = boxes[i];
            // 统计字母
            for (int j = 0; j < s.length(); j++) {
                char c = s.charAt(j);
                if (c >= 'a' && c <= 'z') {
                    sChars[c-'a']++;
                }
                else if (c >= 'A' && c <= 'Z') {
                    sChars[c-'A']++;
                }
            }
            // 匹配
            if(Arrays.equals(keyChars,sChars)){
                result = i+1;
                break;
            }
        }
        
        System.out.println(result);
    }
}
