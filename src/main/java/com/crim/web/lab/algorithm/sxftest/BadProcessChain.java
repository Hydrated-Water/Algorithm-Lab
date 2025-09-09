package com.crim.web.lab.algorithm.sxftest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;


/**
 * 进程链
 * <p/>
 * 输入示例
 * 5          // 进程数量
 * 1 1 10     // PID 进程风险score 创建时间
 * 2 1 20
 * 3 1 30
 * 4 1 40
 * 5 3 111
 * 4          // 进程关系数量
 * 1 2        // 父进程PID 子进程PID
 * 2 3
 * 3 4
 * 1 5
 * 100 4      // 阈值T，所有进程应在T内（包含T）完成 阈值S，所有进程的总风险应大于等于S
 * <p/>
 * 输出示例 依次输出从父进程到子进程的链，随后输出总风险
 * 1 2 3 4 4
 * <p/>
 * 注意：父进程不必是根进程
 * <p/>
 * 以下代码的问题：（通过率50%）
 * 1. （可能的）当进程总score达S就停止，但要求可能仍需要继续输出父进程直到超出阈值T
 *
 * @since 2025/09/06 某信服笔试算法第一题
 */
public class BadProcessChain {
    
    // <pid, 进程>
    static HashMap<Integer, P> pidMap = new HashMap<>();
    
    // 阈值t，总时间必须小于等于t
    static int t;
    
    // 阈值s，总风险必须大于等于s
    static int s;
    
    // 如果没有匹配的进程链则输出NULL
    static boolean flag = true;
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        /// 读取并存储所有进程
        int pCount = scanner.nextInt();
        for (int i = 0; i < pCount; i++) {
            int pid = scanner.nextInt();
            int score = scanner.nextInt();
            int time = scanner.nextInt();
            pidMap.put(pid, new P(pid, score, time));
        }
        /// 读取并解析所有进程关系
        int relationCount = scanner.nextInt();
        for (int i = 0; i < relationCount; i++) {
            int parentPid = scanner.nextInt();
            int childPid = scanner.nextInt();
            P parent = pidMap.get(parentPid);
            pidMap.get(childPid).parent = parent;
            parent.hasChildren = true;
        }
        /// 输入s t
        t = scanner.nextInt();
        s = scanner.nextInt();
        
        
//        System.out.println(pidMap);
        /// 扫描所有的叶子进程，并统计time和score
        /// 如果总计time超出了t，那么扫描下一个叶子
        /// 如果总计time<=t的情况下score大于s，则输出
        pidMap.forEach((pid, p) -> {
            if (!p.hasChildren) {
                int timeCount = 0;
                int scoreCount = 0;
                P current = p;
                ArrayList<Integer> outPids = new ArrayList<>();
                while (timeCount <= t && current != null) {
                    outPids.add(current.pid);
                    timeCount += current.time;
                    scoreCount += current.score;
                    current = current.parent;
                    if (scoreCount >= s && timeCount <= t) {
                        // 输出
                        flag = false;
                        for (int i = outPids.size() - 1; i >= 0; i--) {
                            System.out.print(outPids.get(i) + " ");
                        }
                        System.out.println(scoreCount);
                        break;
                    }
                }
            }
        });
        if(flag) System.out.println("NULL");
    }
    
    static class P {
        public final int pid;
        public final int score;
        public final int time;
        
        // 父进程
        public P parent;
        // 是否存在子进程
        public boolean hasChildren = false;
        
        P(int pid, int score, int time) {
            this.pid = pid;
            this.score = score;
            this.time = time;
        }
        
        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("P{");
            sb.append("pid=").append(pid);
            sb.append(", score=").append(score);
            sb.append(", time=").append(time);
            sb.append(", parent=").append(parent);
            sb.append(", hasChildren=").append(hasChildren);
            sb.append('}');
            return sb.toString();
        }
        
        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            P p = (P) o;
            return pid == p.pid && score == p.score && time == p.time;
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(pid, score, time);
        }
    }
}
