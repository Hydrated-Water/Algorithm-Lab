package com.crim.web.lab.algorithm.nowcoder.jd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;


/**
 * 牛牛的朋友送了她一棵节点数为n的糖果树（1号点为根节点），树上的每个糖果都有一个颜色。
 * 牛牛吃糖果有一个习惯，她每次都会吃掉一整个子树的糖果，她喜欢与众不同，
 * 所以每次吃之前她都会把出现次数最多的颜色的糖果扔掉（可能有多个出现次数最多的颜色，此时要全部扔掉），
 * 她可以选择任意一棵子树吃掉，但她只能吃一次，因此他想知道她一次能吃到的所有糖果的颜色的异或和最大是多少
 * （如果吃到了多个颜色相同的糖果，也需要做多次异或），你能帮帮她吗？
 * <p/>
 * 时间限制：C/C++ 1秒，其他语言2秒
 * 空间限制：C/C++ 256M，其他语言512M
 * <p/>
 * 输入描述：
 * 第一行一个整数1<=n<=1000。表示树的节点数量。
 * 第二行个整数1<=Ai<=1000，表示节点i的颜色。
 * 接下来n-1行，每行两个整数,表示节点和节点之间有一条边。
 * 输出描述：
 * 输出仅有一行，一个整数表示她一次能吃到的糖果的颜色的异或和最大值。
 * <p/>
 * 示例1
 * 输入例子：
 * 4
 * 1 2 3 4
 * 1 2
 * 2 3
 * 2 4
 * 输出例子：
 * 0
 * 例子说明：
 * 四个节点颜色各不相同。吃掉任意子树都会在吃之前把所有糖果扔掉，因此结果为0。
 * 示例2
 * 输入例子：
 * 4
 * 1 1 2 2
 * 1 2
 * 2 3
 * 2 4
 * 输出例子：
 * 1
 * 例子说明：
 * 吃掉以节点3或节点4为根的子树都只有一个节点，结果都为0，以1为根节点时颜色为1和颜色为2的节点数量都为2，因此结果也为0。
 * 吃掉以2为根的子树时节点3和节点4颜色都为2被删除，结果为节点2的颜色1。
 * <p/>
 * 结果：
 * 用时约一小时
 * 在测试用例的提示下修改代码，通过全部用例
 * 运行时间 249ms
 * 占用内存 23332KB
 * <p/>
 * 以下代码存在一个问题：它无法正确处理输入，因为它默认节点数较小者为父节点。使用 修正 1 标识的代码进行修正
 *
 * @since 2025/09/09 牛客 京东 2024年 秋招 算法第三题
 */
public class CowAndCandyTree {
    
    static HashMap<Integer, Node> numNodeMap = new HashMap<>();
    
    // 修正 1
    static HashMap<Integer, List<Integer>> relationMap = new HashMap<>();
    static HashMap<Integer, List<Integer>> relationMapReverse = new HashMap<>();
    
    static int max = 0;
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        /// 处理输入节点及颜色
        int nodeCount = scanner.nextInt();
        for (int i = 0; i < nodeCount; i++) {
            int color = scanner.nextInt();
            int num = i + 1;
            numNodeMap.put(num, new Node(num, color));
        }
        /// 处理输入节点关系
        // 修正 1
        for (int i = 0; i < nodeCount - 1; i++) {
            int a = scanner.nextInt();
            int b = scanner.nextInt();
            List<Integer> relation = relationMap.get(a);
            if (relation == null) {
                relation = new ArrayList<>();
                relationMap.put(a, relation);
            }
            relation.add(b);
            List<Integer> relationReverse = relationMapReverse.get(b);
            if (relationReverse == null) {
                relationReverse = new ArrayList<>();
                relationMapReverse.put(b, relationReverse);
            }
            relationReverse.add(a);
        }
        // 递归建树
        createTree(1);
        // 原代码
        /*for (int i = 0; i < nodeCount - 1; i++) {
            int a = scanner.nextInt();
            int b = scanner.nextInt();
            // 问题代码，默认假设了父节点的节点数小于子节点
            Node parent = numNodeMap.get(Math.min(a, b));
            Node child = numNodeMap.get(Math.max(a, b));
            child.parent = parent;
            parent.children.add(child);
        }*/
        System.out.println(numNodeMap.get(1));
        /// 递归计算
        treeAnalyze(numNodeMap.get(1));
        System.out.println(max);
    }
    
    // 修正 1
    static void createTree(int nodeNum) {
        Node parent = numNodeMap.get(nodeNum);
        List<Integer> relation = relationMap.get(nodeNum);
        List<Integer> relationReverse = relationMapReverse.get(nodeNum);
        if (relation != null)
            for (Integer childNum : relation) {
                if (parent.parent == null || childNum != parent.parent.num) {
                    Node child = numNodeMap.get(childNum);
                    child.parent = parent;
                    parent.children.add(child);
                    // 递归
                    createTree(childNum);
                }
            }
        if (relationReverse != null)
            for (Integer childNum : relationReverse) {
                if (parent.parent == null || childNum != parent.parent.num) {
                    Node child = numNodeMap.get(childNum);
                    child.parent = parent;
                    parent.children.add(child);
                    // 递归
                    createTree(childNum);
                }
            }
    }
    
    static void treeAnalyze(Node tree) {
        
        // 计算本节点
        {
            tree.treeColorXOR ^= tree.color;
            Integer colorCount = tree.colorCountMap.get(tree.color);
            colorCount = colorCount == null ? 1 : colorCount + 1;
            tree.colorCountMap.put(tree.color, colorCount);
            if (colorCount == tree.maxCountColorsValue) tree.maxCountColors.add(tree.color);
            else if (colorCount > tree.maxCountColorsValue) {
                tree.maxCountColorsValue = colorCount;
                tree.maxCountColors.clear();
                tree.maxCountColors.add(tree.color);
            }
        }
        
        // 递归计算每一个子树
        for (Node child : tree.children) {
            treeAnalyze(child);
            
            // 合并一个子树
            tree.treeColorXOR ^= child.treeColorXOR;
            for (Map.Entry<Integer, Integer> entry : child.colorCountMap.entrySet()) {
                int color = entry.getKey();
                int count = entry.getValue();
                Integer colorCount = tree.colorCountMap.get(color);
                colorCount = colorCount == null ? count : colorCount + count;
                tree.colorCountMap.put(color, colorCount);
                if (colorCount == tree.maxCountColorsValue) tree.maxCountColors.add(color);
                else if (colorCount > tree.maxCountColorsValue) {
                    tree.maxCountColorsValue = colorCount;
                    tree.maxCountColors.clear();
                    tree.maxCountColors.add(color);
                }
            }
        }
        
        // 统计子树去除统计最多颜色后的异或和
        // 这里运用了 a XOR b XOR b = a 的原理
        tree.revisionColorXOR = tree.treeColorXOR;
        if (tree.maxCountColorsValue % 2 != 0)
            for (Integer color : tree.maxCountColors) {
                tree.revisionColorXOR ^= color;
            }
        max = Math.max(max, tree.revisionColorXOR);
    }
    
    
    /**
     * 表示一个节点
     */
    static class Node {
        
        Node parent = null;
        
        List<Node> children = new ArrayList<>();
        
        final int num;
        
        final int color;
        
        // 子树颜色异或和
        int treeColorXOR = 0;
        
        // 子树去除统计最多颜色后的异或和
        int revisionColorXOR = 0;
        
        // 子树所有颜色统计
        Map<Integer, Integer> colorCountMap = new HashMap<>();
        
        // 子树统计最多颜色
        Set<Integer> maxCountColors = new HashSet<>();
        
        // 子树统计最多颜色的统计值
        int maxCountColorsValue = 0;
        
        Node(int num, int color) {
            this.num = num;
            this.color = color;
        }
        
        @Override
        public String toString() {
            return num + "/" + color + ":" + children.toString();
        }
    }
}
