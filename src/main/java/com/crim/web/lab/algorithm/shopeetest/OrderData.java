package com.crim.web.lab.algorithm.shopeetest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


/**
 * 运行时间: 243ms
 * 内存占用: 36.7MB
 */
public class OrderData {
    public String[] GetTopPurchases(String[] logs, String userID) {
        int uid = Integer.parseInt(userID);
        // 商品ID: 购买次数
        HashMap<Integer, Integer> map = new HashMap<>();
        for (String log : logs) {
            String[] logData = log.split(",");
            int user = Integer.parseInt(logData[0]);
            if (user == uid) {
                int pid = Integer.parseInt(logData[1]);
                map.merge(pid, 1, Integer::sum);
            }
        }
        // 排序并输出
        int[] sortPID = new int[3];
        int[] sortCount = new int[3];
        int minIndex = -1;
        int i = 0;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (i < 3) {
                sortPID[i] = entry.getKey();
                sortCount[i] = entry.getValue();
                i++;
            }
            if (i == 3) {
                int minCount = sortCount[0];
                minIndex = 0;
                if (sortCount[1] < minCount) {
                    minCount = sortCount[1];
                    minIndex = 1;
                }
                if (sortCount[2] < minCount) {
                    minIndex = 2;
                }
            }
            if (i >= 3 && sortCount[minIndex] > entry.getValue()) {
                sortPID[minIndex] = entry.getKey();
                sortCount[minIndex] = entry.getValue();
                
                int minCount = sortCount[0];
                minIndex = 0;
                if (sortCount[1] < minCount) {
                    minCount = sortCount[1];
                    minIndex = 1;
                }
                if (sortCount[2] < minCount) {
                    minIndex = 2;
                }
            }
        }
        
        String[] result = new String[i];
        if (i == 1) {
            result[0] = String.valueOf(sortPID[0]);
        }
        else if (i == 2) {
            int maxIndex = sortCount[0] >= sortCount[1] ? 0 : 1;
            result[0] = String.valueOf(sortPID[maxIndex]);
            result[1] = String.valueOf(sortPID[1 - maxIndex]);
        }
        else {
            int maxCount = sortCount[0];
            int maxIndex = 0;
            if (sortCount[1] > maxCount) {
                maxCount = sortCount[1];
                maxIndex = 1;
            }
            if (sortCount[2] > maxCount) {
                maxIndex = 2;
            }
            result[0] = String.valueOf(sortPID[maxIndex]);
            result[1] = String.valueOf(sortPID[3 - maxIndex - minIndex]);
            result[2] = String.valueOf(sortPID[minIndex]);
        }
        return result;
    }
}
