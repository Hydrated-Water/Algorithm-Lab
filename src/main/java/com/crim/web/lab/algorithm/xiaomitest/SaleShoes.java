package com.crim.web.lab.algorithm.xiaomitest;

import java.util.HashMap;
import java.util.Scanner;


/**
 * “卖鞋子”：输入为若干行，以\n为结束，第一行为n p q，其中n为鞋子总数，p为原价，q为低价，且1<=q<=p。
 * 之后为若干行如a b c，a区分左右鞋，其中0为左鞋，1为右鞋，b为鞋子码数，35<=b<=49，c为鞋子颜色。n p q a b c 均为int。
 * 鞋子必须同码数的左右一对才能卖出，同颜色时卖价p，不同颜色时卖价q。现在有若干鞋子，请问最高能卖多少钱？
 * <p/>
 * 以下代码的问题：
 * 1. value应使用long而不是int
 * 2. 同尺码不同颜色处理逻辑失误
 */
public class SaleShoes {
    
    static int n;
    static int p;
    static int q;
    
    static int value = 0;
    
    // 左右非同颜色剩余数
    static int leftLeft = 0;
    static int rightLeft = 0;
    
    // 左鞋 < 尺码 , < 颜色 , 数量 > >
    static HashMap<Integer, HashMap<Integer, Integer>> leftShoes = new HashMap<>();
    // 右鞋 < 尺码 , < 颜色 , 数量 > >
    static HashMap<Integer, HashMap<Integer, Integer>> rightShoes = new HashMap<>();
    
    public static void main(String[] args) {
        
        /// 处理输入数据
        Scanner scanner = new Scanner(System.in);
        // 第一行
        String[] firstLine = scanner.nextLine().split(" ");
        n = Integer.parseInt(firstLine[0]);
        p = Integer.parseInt(firstLine[1]);
        q = Integer.parseInt(firstLine[2]);

//        System.out.println(n);
//        System.out.println(p);
//        System.out.println(q);
        
        // 处理剩余的所有行
        while (true) {
            String lineStr = scanner.nextLine();

//            System.out.println(lineStr);
//            System.out.println(leftShoes);
            
            if (lineStr.isEmpty() || lineStr.isBlank()) break;
            String[] line = lineStr.split(" ");
            int type = Integer.parseInt(line[0]);
            int size = Integer.parseInt(line[1]);
            int color = Integer.parseInt(line[2]);

//            System.out.println(type);
//            System.out.println(size);
//            System.out.println(color);
            
            if (type == 0) {
                HashMap<Integer, Integer> map = leftShoes.get(size);
                if (map == null) {
                    map = new HashMap<>();
                    leftShoes.put(size, map);
                }
                Integer count = map.get(color);
                if (count == null) count = 0;
                count++;
                map.put(color, count);
            }
            else if (type == 1) {
                HashMap<Integer, Integer> map = rightShoes.get(size);
                if (map == null) {
                    map = new HashMap<>();
                    rightShoes.put(size, map);
                }
                Integer count = map.get(color);
                if (count == null) count = 0;
                count++;
                map.put(color, count);
            }
        }

//        System.out.println(leftShoes);
//        System.out.println(rightShoes);
        
        /// 计算总价值
        for (int size = 0; size < 100; size++) {
            // < 颜色 , 数量 >
            HashMap<Integer, Integer> left = leftShoes.get(size);
            if (left == null || left.isEmpty()) continue;
            HashMap<Integer, Integer> right = rightShoes.get(size);
            if (right == null || right.isEmpty()) continue;
            // 计算同颜色价值
            left.forEach((color, count) -> {
                leftLeft = 0;
                rightLeft = 0;
                
                Integer rightCount = right.remove(color);
                if (rightCount == null) rightCount = 0;
                value += Math.min(count, rightCount) * p;
                if (count > rightCount) leftLeft += count - rightCount;
                else if (count < rightCount) rightLeft += rightCount - count;
                
                /// 2025/09/06 批注：此处代码存在问题，应在该尺寸所有颜色处理后再计算
                // 处理剩余右鞋子
                right.forEach((rColor, rCount) -> {
                    rightLeft += rCount;
                });
                
                // 处理剩余鞋子
                value += Math.min(leftLeft, rightLeft) * q;
            });
        }
        System.out.println(value);
    }
    
    
}
