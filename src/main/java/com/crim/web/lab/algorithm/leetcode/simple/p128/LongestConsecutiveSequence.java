package com.crim.web.lab.algorithm.leetcode.simple.p128;

import java.io.InputStreamReader;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Scanner;


public class LongestConsecutiveSequence implements Runnable {
    @Override
    public void run() {
        LocalDateTime start = LocalDateTime.now();
        HashSet<Integer> integers = new HashSet<>(200_0000);
        for (int i = 0; i < 100_0000; i++) {
            integers.add(i);
        }
        LocalDateTime addEnd = LocalDateTime.now();
        System.out.println(Duration.between(start,addEnd));
        int j=0;
        while (true) {
            if(integers.isEmpty()) break;
//            Iterator<Integer> iterator = integers.iterator();
//            if (!iterator.hasNext()) break;
//            int i = iterator.next();
//            integers.remove(i);
            integers.remove(j);
            if(Math.random()<0.01) System.out.println(integers.size());
            j++;
        }
        LocalDateTime removeEnd = LocalDateTime.now();
        System.out.println(Duration.between(start,removeEnd));
        
        System.out.println(System.currentTimeMillis());
        Scanner scanner = new Scanner(
                Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("nums.txt"))
        );
        String str = scanner.nextLine();
        System.out.println(str.length());
        String[] arr = str.split(",");
        int[] nums = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            nums[i] = Integer.parseInt(arr[i]);
        }
        System.out.println(new Solution().longestConsecutive(nums));
        System.out.println(System.currentTimeMillis());
    }
}