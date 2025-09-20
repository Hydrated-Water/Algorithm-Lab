package com.crim.web.lab.algorithm.xgdtest;

import java.util.Arrays;
import java.util.Scanner;


public class CandyAndChildren {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        String[] scoresStr = line.substring(1, line.length() - 1).split(",");
        System.out.println(Arrays.toString(scoresStr));
        int[] scores = new int[scoresStr.length];
        for (int i = 0; i < scores.length; i++) {
            scores[i] = Integer.parseInt(scoresStr[i]);
        }
        
        int result = 1;
        int last = 1;
        boolean asc = true;
        for (int i = 1; i < scores.length; i++) {
            if (i == 1 && scores[1] < scores[0]) asc = false;
            if (asc && scores[i] > scores[i - 1]) {
                last++;
                result += last;
                continue;
            }
            
        }
        
        
        System.out.println(result);
    }
}
