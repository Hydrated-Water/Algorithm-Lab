package com.crim.web.lab.algorithm.qyftest;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;


/**
 * 100% 134ms 20MB
 */
public class BroadcastNetwork {
    
    static int n;
    
    // 长宽 n+1 0行0列不使用
    static int[][] net;
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        n = scanner.nextInt();
        net = new int[n + 1][n + 1];
        int length = scanner.nextInt();
        for (int i = 0; i < length; i++) {
            int u = scanner.nextInt();
            int v = scanner.nextInt();
            net[u][v] = 1;
            net[v][u] = 1;
        }
        int start = scanner.nextInt();
        
        //debug
        System.out.println(n);
        Arrays.stream(net).forEach(ints -> System.out.println(Arrays.toString(ints)));
        System.out.println();
        
        int far = 0;
        HashSet<Integer> currentNodes = new HashSet<>();
        HashSet<Integer> broadcastNodes = new HashSet<>();
        currentNodes.add(start);
        broadcastNodes.add(start);
        while (broadcastNodes.size() < n) {
            
            //debug
            System.out.println(currentNodes);
            System.out.println(broadcastNodes);
            
            HashSet<Integer> nextNodes = new HashSet<>();
            for (int u : currentNodes) {
                for (int v = 1; v <= n; v++) {
                    if (net[u][v] == 0) continue;
                    if (broadcastNodes.add(v))
                        nextNodes.add(v);
                }
            }
            currentNodes = nextNodes;
            if (currentNodes.isEmpty()) break;
            far++;
            
            //debug
            System.out.println(currentNodes);
            System.out.println();
        }
        
        System.out.println(far * 2);
    }
}
