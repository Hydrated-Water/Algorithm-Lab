# Overview

本项目用于记录算法的探究与实现





## 1. 两数之和 Two Sum



### 题目

> 给定一个整数数组 `nums` 和一个整数目标值 `target`，请你在该数组中找出 **和为目标值** *`target`* 的那 **两个** 整数，并返回它们的数组下标。
>
> 你可以假设每种输入只会对应一个答案，并且你不能使用两次相同的元素。
>
> 你可以按任意顺序返回答案。
>
> **示例 1：**
>
> ```
> 输入：nums = [2,7,11,15], target = 9
> 输出：[0,1]
> 解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 。
> ```
>
> **示例 2：**
>
> ```
> 输入：nums = [3,2,4], target = 6
> 输出：[1,2]
> ```
>
> **示例 3：**
>
> ```
> 输入：nums = [3,3], target = 6
> 输出：[0,1]
> ```
>
> **提示：**
>
> - `2 <= nums.length <= 104`
> - `-109 <= nums[i] <= 109`
> - `-109 <= target <= 109`
> - **只会存在一个有效答案**
>
> **进阶：**你可以想出一个时间复杂度小于 `O(n2)` 的算法吗？
>
> 
>
> ------
>
> 通过次数 6,687,514/12.1M
>
> 通过率 55.1%



### 思路

1. 暴力穷举

   对包含n个元素的数组`nums`，至多穷举 C <sub>n</sub><sup>2 </sup>即 1/2 * n (n-1) 个两元素组合，并判断每个组合的和是否为`target`

   时间复杂度为 O(n<sup>2</sup>)

2. 二分查找

   对包含n个元素的数组`nums`，先使用时间复杂度为 O(nlogn) 的排序算法使其有序

   随后对`nums`的每个元素`ni`，使用二分查找可能存在的元素`target-ni`

   时间复杂度为 O(nlogn)，其中查找部分时间复杂度为 O(nlogn)

3. 双指针

   对包含n个元素的数组`nums`，先使用时间复杂度为 O(nlogn) 的排序算法使其有序

   随后使索引`i`从数组头开始遍历，索引`j`从数组尾开始遍历，对于每个`i`元素`ni`，使`j`不断向前找可能对应的`target-ni`元素值，直到两个指针相遇

   时间复杂度为 O(nlogn)，其中查找部分时间复杂度为 O(n)

4. 哈希表

   *思路来自官方解题*

   对于包含n个元素的数组`nums`，对于其中的每一个元素`ni`，需找到另一个元素值为`target-ni`，使用哈希表对所有元素建立索引

   时间复杂度为 O(n)，其中建表部分和查找部分时间复杂度均为 O(n)

### 二分查找

#### 代码

```java
class Solution {
    public int[] twoSum(int[] nums, int target) {
        // 排序
        int[] sortedNums = Arrays.copyOf(nums, nums.length);
        Arrays.sort(sortedNums);
        // 两个找到的元素值
        int a = Integer.MIN_VALUE;
        int b = Integer.MIN_VALUE;
        // 遍历sortedNums，使用二分查找找到target-sortedNums[i]所在的位置
        for (int i = 0; i < sortedNums.length; i++) {
            // 当 index < 0 或 index == i 时无效
            int index = Arrays.binarySearch(sortedNums, target - sortedNums[i]);
            if (index < 0 || index == i) continue;
            a = sortedNums[i];
            b = sortedNums[index];
            break;
        }
        // 查找a、b在原数组中的索引，需处理当a == b的情况
        boolean aFound = false;
        boolean bFound = false;
        int aIndex = Integer.MIN_VALUE;
        int bIndex = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            if (!aFound && nums[i] == a) {
                aFound = true;
                aIndex = i;
            }
            else if (!bFound && nums[i] == b) {
                bFound = true;
                bIndex = i;
            }
            else if (aFound && bFound) break;
        }
        return new int[]{aIndex, bIndex};
    }
}
```

#### 结果

通过

用时3ms，击败45.08%

### 双指针

#### 代码

```java
class AdvancedSolution1 {
    public int[] twoSum(int[] nums, int target) {
        // 排序
        int[] sortedNums = Arrays.copyOf(nums, nums.length);
        Arrays.sort(sortedNums);
        // 两个找到的元素值
        int a = Integer.MIN_VALUE;
        int b = Integer.MIN_VALUE;
        // 双指针
        {
            int i = 0;
            int j = sortedNums.length - 1;
            boolean isFound = false;
            while (i != j) {
                if (sortedNums[i] + sortedNums[j] == target) {
                    isFound = true;
                    break;
                }
                // 向前移动j指针以寻找可能的元素值
                if (sortedNums[j] > target - sortedNums[i])
                    j--;
                else i++;
            }
            // 保证代码鲁棒性
            if (!isFound) return null;
            a = sortedNums[i];
            b = sortedNums[j];
        }
        // 查找a、b在原数组中的索引，需处理当a == b的情况
        {
            boolean aFound = false;
            boolean bFound = false;
            int aIndex = Integer.MIN_VALUE;
            int bIndex = Integer.MIN_VALUE;
            for (int i = 0; i < nums.length; i++) {
                if (!aFound && nums[i] == a) {
                    aFound = true;
                    aIndex = i;
                }
                else if (!bFound && nums[i] == b) {
                    bFound = true;
                    bIndex = i;
                }
                else if (aFound && bFound) break;
            }
            return new int[]{aIndex, bIndex};
        }
    }
}
```

#### 结果

通过

用时3ms，击败45.08%

### 哈希表

#### 代码1

```java
class AdvancedSolution2 {
    public int[] twoSum(int[] nums, int target) {
        // 建立哈希表，使得键为元素值，值为数组索引（注意可能存在多个相同值）
        HashMap<Integer, List<Integer>> map = new HashMap<>(nums.length * 2);
        for (int i = 0; i < nums.length; i++) {
            List<Integer> indexList = map.get(nums[i]);
            if (indexList == null) {
                indexList = new ArrayList<>();
                indexList.add(i);
                map.put(nums[i], indexList);
            }
            else {
                indexList.add(i);
            }
        }
        // 遍历数组，查找对应的target-nums[i]
        for (int i = 0; i < nums.length; i++) {
            List<Integer> indexList = map.get(target - nums[i]);
            if (indexList == null) continue;
            if (nums[i] != target - nums[i]) {
                return new int[]{i, indexList.get(0)};
            }
            else {
                if (indexList.size() < 2) continue;
                if (indexList.get(0) != i)
                    return new int[]{i, indexList.get(0)};
                else return new int[]{i, indexList.get(1)};
            }
        }
        return null;
    }
}
```

#### 结果1

通过

用时7ms，击败33.97%

#### 分析1

时间复杂度更低但用时更长，猜测是自动拆装箱和List的构造影响了性能

#### 代码2

```java
class AdvancedSolution3 {
    public int[] twoSum(int[] nums, int target) {
        // 建立哈希表，使得键为元素值，值为数组索引（假设多个值相同的情况很少见，不在此处处理）
        HashMap<Integer, Integer> map = new HashMap<>(nums.length * 2);
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], i);
        }
        // 遍历数组，查找对应的target-nums[i]，在此处处理可能的nums[i]等于target-nums[i]的情况
        for (int i = 0; i < nums.length; i++) {
            Integer index = map.get(target - nums[i]);
            // 当两值target-nums[i]与nums[i]相等时，如果nums中确实有多个相同的元素值，
            // 那么在map.put(nums[i],i)操作中位置靠后后者将覆盖前者，而此处i是从数组头开始遍历，
            // 那么这里总能正确处理多个值相同的情况
            if (index != null && index != i) {
                return new int[]{i, index};
            }
        }
        return null;
    }
}
```

#### 结果2

通过

用时5ms，击败38.64%

#### 分析2

从上述结果中可猜测装箱拆箱的性能影响为主要因素

#### 代码3

```java
public class AdvancedSolution4 {
    public int[] twoSum(int[] nums, int target) {
        // 建立哈希表，使得键为元素值，值为数组索引
        HashMap<Integer, Integer> map = new HashMap<>(nums.length * 2);
        for (int i = 0; i < nums.length; i++) {
            Integer index = map.get(target - nums[i]);
            // 这里总能正确处理多个值相同的情况，因为i不可能等于index
            if (index != null) {
                return new int[]{i, index};
            }
            map.put(nums[i], i);
        }
        return null;
    }
}
```

#### 结果3

通过

用时2ms，击败99.63%

#### 分析3

> 大道至简