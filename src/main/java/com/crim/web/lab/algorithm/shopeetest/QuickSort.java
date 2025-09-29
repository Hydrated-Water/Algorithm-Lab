package com.crim.web.lab.algorithm.shopeetest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


/**
 * 运行时间: 429ms
 * 内存占用: 28.6MB
 */
public class QuickSort {
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            list.add(c - '0');
        }
        int[] arr = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            arr[i] = list.get(i);
        }
        quickSort(arr, 0, arr.length - 1);
        for (int j : arr) {
            System.out.print(j);
        }
        System.out.println();
    }
    
    public static void quickSort(int[] arr, int start, int end) {
        if (end - start <= 0) return;
        int i = start - 1;
        int pivot = arr[end];
        for (int k = start; k <= end; k++) {
            if (arr[k] <= pivot) {
                i++;
                // 交换
                int swap = arr[i];
                arr[i] = arr[k];
                arr[k] = swap;
            }
        }
        quickSort(arr, start, i - 1);
        quickSort(arr, i + 1, end);
    }
}
