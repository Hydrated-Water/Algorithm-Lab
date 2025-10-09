# Overview

本项目用于记录算法的探究与实现





# LeetCode





## 1. 两数之和 Two Sum



### 题目

[简单](https://leetcode.cn/problems/two-sum/description/)

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





## 3. 无重复字符的最长子串 Longest Substring Without Repeating Characters



### 题目

[中等](https://leetcode.cn/problems/longest-substring-without-repeating-characters/description/)

> 给定一个字符串 `s` ，请你找出其中不含有重复字符的 **最长 子串** 的长度。
>
> **示例 1:**
>
> ```
> 输入: s = "abcabcbb"
> 输出: 3 
> 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
> ```
>
> **示例 2:**
>
> ```
> 输入: s = "bbbbb"
> 输出: 1
> 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
> ```
>
> **示例 3:**
>
> ```
> 输入: s = "pwwkew"
> 输出: 3
> 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
>      请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
> ```
>
> **提示：**
>
> - `0 <= s.length <= 5 * 104`
> - `s` 由英文字母、数字、符号和空格组成
>
> ------
>
> 通过次数 3,689,910/8.9M
>
> 通过率 41.5%



### 思路

1. 哈希表

   为了保证无重复字符串，对于长度为`n`的字符串`str`中的任意字符 c<sub>j</sub> ，必定存在某种方式获取到 c<sub>i</sub> 使得 c<sub>i</sub> = c<sub>j</sub> 且 i < j

   为了确保查询到 c<sub>i</sub> 的时间尽可能短，考虑使用哈希表存储，使得其以字符值为键，以字符所在的索引为值，那么查询`str`中`n`个字符的上一个相同字符值的索引，预估时间复杂度为 `O(n)`

   设想从头开始遍历字符串`str`，除了对于每一个字符 c<sub>j</sub> 需要找到上一个相同值的 c<sub>i</sub> 以外，还需要确保在字符子串 i 到 j 之间无其他重复字符

   那么使变量`a`和`b`存储最近最短的重复字符串的位置，遍历字符串`str`，查找当前字符 c<sub>j</sub> 的 c<sub>i</sub> ，如果 a < i，那么使 `(a,b) = (i,j)`，并且对于每一个字符都将执行把 `(c,j)` 存储到哈希表的操作，这将更新可能存在的上一个 `(c,i)` 键值对的值

   由于正在遍历`str`，当查找到当前字符 c<sub>j</sub> 的 c<sub>i</sub> 时，比较`(i,j)`和先前的`(a,b)`，如果子串 i 到 j 覆盖了子串 a 到 b，则子串 i 到 j 不是无重复字符的子串，否则子串是无重复字符的子串

   - 证明如果子串 i 到 j 未覆盖子串 a 到 b，则子串 i 到 j 是无重复字符的子串：

     在遍历`str`时每次遍历均可能更新 a<sub>0</sub> , b<sub>0</sub>  为 a , b  ，并且确保了 a > a<sub>0</sub> ，由于正在遍历，那么也必定有 b > b<sub>0</sub>，那么子串 a 到 b 在每次遍历时必定是最近最短的无重复字符的子串

     如果子串 i 到 j （必有 j >= b）未覆盖子串 a 到 b，那么 i 可能有以下几种情况：

     - a < i < b
     - b <= i < j

     对于除子串 a 到 b 以外的之前任意字符串 p 到 q 使得字符p的值等于字符q且 p < q，必有 q < j，p < a（p不可能等于a），那么若需子串 i 到 j 覆盖子串 p 到 q（即 j >= q 且 i <=p），将发生 i <=p 且 p < a且 a < i 的矛盾

     ```
     str : .....................................
                 a          b     j
                      i1      i2
     ```

   预估时间复杂度为 O(n)

2. 哈希表2

   前述算法虽然精巧但思想过于复杂，且考虑不周，不能解决所有的情况，但以哈希表为核心数据结构的思想仍可保留

   改进后的算法如下：

   遍历字符串序列，并记录当前最长无重复子串长度`m`，对于字符串每个字符 c<sub>j</sub> ，判断其上一个同值字符 c<sub>i</sub> 的索引`i`是否位于当前以索引`j`为结尾的长度`m`的无重复子串内，如果位于，则缩短`m`使得当前最长无重复子串内无重复字符

   预估时间复杂度为 O(n)



### 哈希表

#### 代码

```java
public class Solution {
    public int lengthOfLongestSubstring(String s) {
        int maxResult = -1;
        // 字符 - 索引 键值对
        HashMap<Character, Integer> map = new HashMap<>();
        // 最近最短重复字符索引
        int a = -1;
        int b = 0;
        // 用于计算字符串第一个无重复字符子串
        boolean isFirst = true;
        // 遍历字符串
        for (int j = 0; j < s.length(); j++) {
            char c = s.charAt(j);
            // 找到最近的上一个相同字符索引
            Integer index = map.get(c);
            if (index != null) {
                int i = index;
                // 判断当前子串 i,j 是否存在其他重复字符
                if (i > a) {
                    // 记录当前最大无重复字符串长度
                    maxResult = Math.max(maxResult, j - i);
                    // 更新最近最短重复字符索引
                    a = i;
                    b = j;
                }
            }
            // 更新最近的上一个相同字符索引
            if (isFirst) {
                // 计算第一个无重复字符子串长度
                if (map.put(c, j) != null) {
                    maxResult = Math.max(maxResult, j);
                    isFirst = false;
                }
            }
            else map.put(c, j);
        }
        // 计算数组尾部的最长无重复子串长度
        // 能解决字符串中无重复字符、字符串长度为0的情况
        maxResult = Math.max(maxResult, s.length() - 1 - a);
        return maxResult;
    }
}
```

#### 结果

未通过

#### 分析

该算法不能解决如`ohomm`这样的字符串，这是因为这个算法的本质是计算两个相同字符之间的长度，其在本质上有局限性，但哈希表法仍有可取之处



### 哈希表2

#### 代码

```java
class CorrectSolution {
    public int lengthOfLongestSubstring(String s) {
        int maxResult = 0;
        // 当前无重复子串的长度
        int m = 0;
        // 每个字符对应的最近的上一个重复字符的索引
        HashMap<Character, Integer> map = new HashMap<>();
        // 遍历字符串
        for (int j = 0; j < s.length(); j++) {
            // 查询上一个重复字符索引
            char c = s.charAt(j);
            Integer index = map.get(c);
            // 查询上一个重复字符索引是否在当前无重复子串内
            if (index != null && index >= j - m) {
                m = j - index;
            }
            else {
                // 无重复子串长度增加
                m++;
            }
            maxResult = Math.max(maxResult, m);
            map.put(c, j);
        }
        return maxResult;
    }
}
```

#### 结果

通过

用时4ms，击败86.78%

#### 分析

大道至简





## 11. 盛最多水的容器 Container With Most Water



### 题目

[中等](https://leetcode.cn/problems/container-with-most-water/)

> 给定一个长度为 `n` 的整数数组 `height` 。有 `n` 条垂线，第 `i` 条线的两个端点是 `(i, 0)` 和 `(i, height[i])` 。
>
> 找出其中的两条线，使得它们与 `x` 轴共同构成的容器可以容纳最多的水。
>
> 返回容器可以储存的最大水量。
>
> **说明：**你不能倾斜容器。
>
> **示例 1：**
>
> ![img](https://aliyun-lc-upload.oss-cn-hangzhou.aliyuncs.com/aliyun-lc-upload/uploads/2018/07/25/question_11.jpg)
>
> ```
> 输入：[1,8,6,2,5,4,8,3,7]
> 输出：49 
> 解释：图中垂直线代表输入数组 [1,8,6,2,5,4,8,3,7]。在此情况下，容器能够容纳水（表示为蓝色部分）的最大值为 49。
> ```
>
> **示例 2：**
>
> ```
> 输入：height = [1,1]
> 输出：1
> ```
>
> **提示：**
>
> - `n == height.length`
> - `2 <= n <= 105`
> - `0 <= height[i] <= 104`
>
> ------
>
> 通过次数 1,829,426/3M
>
> 通过率 61.5%



### 思路

1. 双指针

   通过 `max` 记录当前已知的最大容器面积

   对于数组的局部从 n<sub>i</sub> 到 n<sub>j</sub> （i < j），如果 n<sub>i</sub> < n<sub>j</sub>，使 i 递增直到 p，使得 n<sub>p</sub> > n<sub>i</sub> 且 p < (i + j)/2

   因为任取 n<sub>m</sub> 使得 i < m < p 都有 n<sub>m</sub> < n<sub>i</sub> < n<sub>p</sub> ，因此面积 S<sub>mj</sub> < S<sub>ij</sub> <= max

   此时计算面积 S<sub>pj</sub> 并与 max 进行比较或更新

   对于剩余的局部数组从 n<sub>p</sub> 到 n<sub>j</sub> ，重复执行上述操作

   ```
             |                          
             |                          
     |       |                         |
     |   |   |                         |
     |   |   |                         |
     i   m   p                         j
   ```

   可以证明任取 m, q 使得 i < m < q < j 面积 S<sub>mq</sub> < S<sub>ij</sub> <= max

   解决当移动 p 使得 p >= (i + j)/2 的问题

   ```
                             |        
                             |       | 
     |                       |       |
     |   |                   |       |
     |   |                   |       |
     i   m                   p       j
   ```

   由于任取 n<sub>m</sub> 使得 i < m < p 都有 n<sub>m</sub> < n<sub>i</sub> < n<sub>p</sub> ，面积 S<sub>mj</sub> < S<sub>ij</sub> <= max

   且仍保证任取 m, q 使得 i < m < q < j 面积 S<sub>mq</sub> < S<sub>ij</sub> <= max

   那么仅需多计算 S<sub>ip</sub> 并于 max 进行比较或更新

   总结：

   对于局部数组 i 到 j，总能使 max = f<sub>max</sub> ( S<sub>ij</sub> , max<sub>0</sub> ) 直到找到 p 使得 i < p < j ，n<sub>i</sub> < n<sub>p</sub> （在 n<sub>i</sub> <= n<sub>j</sub> 时），并在对局部数据 p 到 j 进行重复性的操作



### 双指针

#### 代码

```java
class Solution {
    public int maxArea(int[] height) {
        if (height.length < 2) return 0;
        int i = 0;
        int j = height.length - 1;
        int p = height[i] <= height[j] ? i : j;
        int max = calArea(i, j, height);
        while (i < j) {
            if (height[i] <= height[j]) {
                if (height[p] <= height[i]) {
                    p++;
                    if (p >= j) break;
                    continue;
                }
                int area_ip = calArea(i, p, height);
                int area_pj = calArea(p, j, height);
                int currentMax = Math.max(area_ip, area_pj);
                max = Math.max(max, currentMax);
                i = p;
                p = height[i] <= height[j] ? i : j;
            }
            else {
                if (height[p] <= height[j]) {
                    p--;
                    if (p <= i) break;
                    continue;
                }
                int area_ip = calArea(i, p, height);
                int area_pj = calArea(p, j, height);
                int currentMax = Math.max(area_ip, area_pj);
                max = Math.max(max, currentMax);
                j = p;
                p = height[i] <= height[j] ? i : j;
            }
        }
        return max;
    }
    
    private int calArea(int a, int b, int[] height) {
        return Math.min(height[a], height[b]) * (b - a);
    }
}
```

#### 结果

通过

用时3ms，击败94.48%





## 15. 三数之和 3Sum



### 题目

[中等](https://leetcode.cn/problems/3sum/)

> 给你一个整数数组 `nums` ，判断是否存在三元组 `[nums[i], nums[j], nums[k]]` 满足 `i != j`、`i != k` 且 `j != k` ，同时还满足 `nums[i] + nums[j] + nums[k] == 0` 。请你返回所有和为 `0` 且不重复的三元组。
>
> **注意：**答案中不可以包含重复的三元组。
>
> **示例 1：**
>
> ```
> 输入：nums = [-1,0,1,2,-1,-4]
> 输出：[[-1,-1,2],[-1,0,1]]
> 解释：
> nums[0] + nums[1] + nums[2] = (-1) + 0 + 1 = 0 。
> nums[1] + nums[2] + nums[4] = 0 + 1 + (-1) = 0 。
> nums[0] + nums[3] + nums[4] = (-1) + 2 + (-1) = 0 。
> 不同的三元组是 [-1,0,1] 和 [-1,-1,2] 。
> 注意，输出的顺序和三元组的顺序并不重要。
> ```
>
> **示例 2：**
>
> ```
> 输入：nums = [0,1,1]
> 输出：[]
> 解释：唯一可能的三元组和不为 0 。
> ```
>
> **示例 3：**
>
> ```
> 输入：nums = [0,0,0]
> 输出：[[0,0,0]]
> 解释：唯一可能的三元组和为 0 。
> ```
>
> **提示：**
>
> - `3 <= nums.length <= 3000`
> - `-105 <= nums[i] <= 105`
>
> ------
>
> 通过次数 2,511,076/6.3M
>
> 通过率 39.8%



### 思路

1. 哈希表

   参考“两数之和”的解题思路，将三数之和转换为对包含n个元素的输入数组`nums`的每一个元素`nums[i]`执行两数之和的算法

   那么可以参考的思路有二分查找、双指针、哈希表等，此处选择哈希表

   对`nums`进行哈希构建`HashMap<Integer, List<Integer>>`，使得哈希表以元素值为键，以该值可能出现的所有位置索引为值，此处时间复杂度为 O(n)

   对`nums`进行双重循环，使得对任意`j>i`找到值为`-(nums[i]+nums[j])`的元素索引`k`，并要求`k>j`，此处时间复杂度为 O(n<sup>2</sup>)

   由于返回值是不重复三元组的列表，哈希表结构转换为`HashMap<Integer, Integer>`，以元素值为键，以该元素值最后一次出现的位置索引为值

   并且应以某种方式对`nums`进行去重，可考虑排序或从哈希表的键中获取以去重的列表或集合`uniqueNums`，并对`uniqueNums`进行双重循环

   因此可考虑优化如下：

   对`nums`构建哈希集合`HashSet<Integer>`，以元素值为值，并导出数组`uniqueNums`和基于数组的哈希表`uniqueNumMap`（以元素值为键，以索引为值）以用于避免在集合上的双重循环迭代器导致的性能损失，此处时间复杂度为 O(n)

   对`uniqueNums`进行双重循环，使得对任意`j>i`找到值为`-(uniqueNums[i]+uniqueNums[j])`的元素索引`k`，并要求`k>j`，此处时间复杂度为 O(n<sup>2</sup>)

   **上述解题存在严重问题，不能解决如`nums=[0,0,0]`这样的情况**





## 42. 接雨水 Trapping Rain Water



### 题目

[困难](https://leetcode.cn/problems/trapping-rain-water/)

> 给定 `n` 个非负整数表示每个宽度为 `1` 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
>
> **示例 1：**
>
> ![img](https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2018/10/22/rainwatertrap.png)
>
> ```
> 输入：height = [0,1,0,2,1,0,1,3,2,1,2,1]
> 输出：6
> 解释：上面是由数组 [0,1,0,2,1,0,1,3,2,1,2,1] 表示的高度图，在这种情况下，可以接 6 个单位的雨水（蓝色部分表示雨水）。 
> ```
>
> **示例 2：**
>
> ```
> 输入：height = [4,2,0,3,2,5]
> 输出：9
> ```
>
> **提示：**
>
> - `n == height.length`
> - `1 <= n <= 2 * 104`
> - `0 <= height[i] <= 105`
>
> ------
>
> 通过次数 1,461,140/2.2M
>
> 通过率 65.7%



### 思路

1. 遍历图形

   考虑当输入数组`height`的最大值为1时，可以简单地通过遍历以 O(n) 的时间复杂度计算容积

   那么当`height`满足`0<=height[i]<=10^5`时，可以对图形的每一行执行上述操作，此时时间复杂度为 O(nm) ，其中m为`height`的最大元素值

2. 双指针

   假设存在数组的局部`height[p]`到`height[q]`，满足当任取 m < p 时 height[m] <= height[q] ，那么由 height[p] 到 height[q] 组成的容器的容积总是可以这样计算：以 height[p] 和 height[q] 中的最小值为容器高，以 q - p - 1 为容器宽，宽高乘积减去height[p] 到 height[q] 中所有元素值即为容积

   ```
          | 
      |   |   |
    0 1 2 3 4 5
      p   q
   ```

   那么假设输入数组 height 中有一个或多个最大元素值 maxHeight ，那么对于在第一个 maxHeight 之前的所有元素可以执行如下算法：

   1. 初始化 p，且 q = p
   2. 使 q 递增
   3. 如果有 n[q] >= n[p] 那么令 p = q 并回到步骤 2
   4. 否则将总容积 v += n[p] - n[q] 并回到步骤 2

   如果有多个maxHeight，那么找到第一个和最后一个 maxHeight 的索引 r、s，执行如下算法：

   1. 初始化 q = r
   2. 使 q 递增
   3. 如果 q >= s，则中止
   4. 否则将总容积 v += maxHeight - n[q] 并回到步骤 2

   而对于最后一个 maxHeight之后的所有元素，可执行反向操作：

   1. 初始化 p = n.length - 1，且 q = p
   2. 使 q 递减
   3. 如果 q <= s，则中止
   4. 如果 n[q] >= n[p] 那么令 p = q 并回到步骤 2
   5. 否则将总容积 v += n[p] - n[q] 并回到步骤 2

   ```
         |     |
       | |   | |   |
   |   | | | | |   |
   0 1 2 3 4 5 6 7 8 9 
   ```

   上述算法的时间复杂度为 O(n)

   需要考虑的边界条件是：

   1. 输入数组开头或结尾包含0元素值
   2. 数组没有最大值（数组由相同元素值组成）
   3. 数组的最大值出现在数组开头或末尾
   4. 数组的长度小于3



### 双指针

#### 代码

```java
class Solution {
    public int trap(int[] height) {
        /// 返回值
        int v = 0;
        /// 找到输入数组的最大值和最大值第一次和最后一次出现的索引
        int maxHeight = 0;
        int r = 0;
        int s = 0;
        for (int i = 0; i < height.length; i++) {
            if (height[i] > maxHeight) {
                maxHeight = height[i];
                r = i;
                s = i;
            }
            else if (height[i] == maxHeight) {
                s = i;
            }
        }
        /// 对于 height[r] 之前的局部数组
        {
            int p = 0;
            int q = p;
            while (true) {
                q++;
                if (q >= r) break;
                if (height[q] >= height[p]) {
                    p = q;
                    continue;
                }
                v += height[p] - height[q];
            }
        }
        /// 对于第一个和最后一个 maxHeight 之间的局部数组
        {
            int q = r;
            while (true) {
                q++;
                if (q >= s) break;
                v += maxHeight - height[q];
            }
        }
        /// 对于最后一个 maxHeight 之后的局部数组
        {
            int p = height.length - 1;
            int q = p;
            while (true) {
                q--;
                if (q <= s) break;
                if (height[q] >= height[p]) {
                    p = q;
                    continue;
                }
                v += height[p] - height[q];
            }
        }
        return v;
    }
}
```

#### 结果

通过

用时0ms，击败100.00%

#### 分析

此解法在空间复杂度和时间复杂度上的表现极好，与官方的解法不同。官方的解法的出发点是对于每个 height[i] 所在的部分能容纳的雨水单位与 height[i] 左侧与右侧的最大高度相关，因此官方的动态规划和双指针解法均是出于尽可能快地寻找到每个 height[i] 的左侧或右侧的最大高度。而此解法的思想是出于对容器的特征的考虑，认为存在一个 O(n) 的算法计算一个凹形容器（容器的最左侧和最右侧是容器高度的最大值或次大值）的容积，并将整个数组视为多个凹形容器的组合。





## 49. 字母异位词分组 Group Anagrams



### 题目

[中等](https://leetcode.cn/problems/group-anagrams/description)

> 给你一个字符串数组，请你将 字母异位词 组合在一起。可以按任意顺序返回结果列表。
>
> **示例 1:**
>
> **输入:** strs = ["eat", "tea", "tan", "ate", "nat", "bat"]
>
> **输出:** [["bat"],["nat","tan"],["ate","eat","tea"]]
>
> **解释：**
>
> - 在 strs 中没有字符串可以通过重新排列来形成 `"bat"`。
> - 字符串 `"nat"` 和 `"tan"` 是字母异位词，因为它们可以重新排列以形成彼此。
> - 字符串 `"ate"` ，`"eat"` 和 `"tea"` 是字母异位词，因为它们可以重新排列以形成彼此。
>
> **示例 2:**
>
> **输入:** strs = [""]
>
> **输出:** [[""]]
>
> **示例 3:**
>
> **输入:** strs = ["a"]
>
> **输出:** [["a"]]
>
> **提示：**
>
> - `1 <= strs.length <= 104`
> - `0 <= strs[i].length <= 100`
> - `strs[i]` 仅包含小写字母
>
> ------
>
> 通过次数 1,232,715/1.8M
>
> 通过率 69.9%



### 思路

1. 哈希表

   `strs[i]` 仅包含小写字母且长度为0到100

   对于每一个`s = strs[i]`，都可以计算`s`的每一个字母的出现次数，并以此作为哈希，使得`s`被正确的分组

   因此需要一种方式计算`s`的哈希以快速地基于每一个字母的出现次数区分开来

   考虑使用哈希表存储，以某种对象为键，以存储被分组的`s`的列表为值，那么作为键的对象必须通过每个字母出现的次数，存储并区别于其他对象

   为了确保性能，该对象还应该高效的生成并区别于其他对象的哈希值

   预估时间复杂度为 O(mn) ，其中`n`为`strs`的元素数量，`m`为字符串平均长度



### 哈希表

#### 代码

```java
class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        // 使用哈希表存储和查找，以每个字母出现次数为键，以存储被分组的字符串列表为值
        HashMap<List<Integer>, List<String>> map = new HashMap<>();
        for (String str : strs) {
            List<Integer> letterCount = this.getLetterCount(str);
            List<String> container = map.get(letterCount);
            if (container == null) {
                container = new ArrayList<>();
                map.put(letterCount, container);
            }
            container.add(str);
        }
        return map.values().stream().toList();
    }
    
    // 计算字符串中每个小写字母的出现次数
    private List<Integer> getLetterCount(String s) {
        int[] letterCount = new int[26];
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            letterCount[c - 'a']++;
        }
        List<Integer> list = new ArrayList<>(letterCount.length);
        for (int count : letterCount) {
            list.add(count);
        }
        return list;
    }
}
```

#### 结果

通过

用时11ms，击败19.47%

### 分析

注意到虽然时间复杂度理论上已经最优，但用时和内存占用仍不理想，考虑这是键的设计不良导致

使用更好的方式设计键，如String、包装数组、大整数等





## 53. 最大子数组和 Maximum Subarray



### 题目

[中等](https://leetcode.cn/problems/maximum-subarray/description/)

> 给你一个整数数组 `nums` ，请你找出一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
>
> **子数组**是数组中的一个连续部分。
>
> **示例 1：**
>
> ```
> 输入：nums = [-2,1,-3,4,-1,2,1,-5,4]
> 输出：6
> 解释：连续子数组 [4,-1,2,1] 的和最大，为 6 。
> ```
>
> **示例 2：**
>
> ```
> 输入：nums = [1]
> 输出：1
> ```
>
> **示例 3：**
>
> ```
> 输入：nums = [5,4,-1,7,8]
> 输出：23
> ```
>
> **提示：**
>
> - `1 <= nums.length <= 105`
> - `-104 <= nums[i] <= 104`
>
> **进阶：**如果你已经实现复杂度为 `O(n)` 的解法，尝试使用更为精妙的 **分治法** 求解。
>
> ------
>
> 通过次数 2,284,332/4.1M
>
> 通过率 56.4%



### 思路

1. 缩小范围

   考虑 nums 数组中的元素特征为正数、负数和 0

   ```
   -  +  +  +  -  -  +  -  0  -  +  0
   0  1  2  3  4  5  6  7  8  9  10 11
   ```

   对于一个子数组，如果包括了一个正数元素（如索引1），那么为了和最大，它必然包括相邻该正数元素的所有正数

   如果这个子数组包括了一个负数元素（如索引5），那么为了和最大，它必然包括该负数元素的一个方向上延伸的所有负数元素（如索引4），并试图找到正数元素（如索引3、2、1）以“抵消”负数元素对和的影响

   而 0 元素对和无影响，可以直接忽略

   并且，如果 nums 数组的最左端或最右端是1个或多个连续的负数，那么子数组必然不会包括它们，因为无法找到更多的正数抵消这些负数对和的影响

   那么一个 nums 数组可以经过如下简化操作变成 datas 数组：

   - 将所有相邻的正数元素合并
   - 将所有相邻的负数元素合并
   - 将所有的0忽略
   - 将数组最左端和最右端的连续的负数全部忽略

   如

   ```
   + - + - +
   0 1 2 3 4
   ```

   那么 datas 数组一定是从正数开始，正负数交替出现的奇数长的数组

   首先考虑 datas 中的所有正数，找到最大值 max ，它是最大和子数组的候选之一

   注意之前的结论，任意子数组如果包括了一个负数，那么它必须再包括一个正数以试图抵消该负数对和的影响，因此，一个候选的子数组必然是以正数开头且以正数结尾

   现在开始遍历 datas，令 i=0，sum = datas[0]，i 每次步进2，保证 datas[i] > 0

   - 如果 sum + datas[i+1] < 0

     那么任意子数组不可能包括 sum 和 datas[i+1]，因为这减小了该子数组的和

     使 sum = datas[i+2]（重置子数组）

     i 步进，继续遍历

   - 如果 sum + datas[i+1] >= 0

     那么任意包括了 datas[i+2] 的子数组必然包括 sum 、datas[i+1]，因为这可以使该子数组的和更大

     使 sum += datas[i+1] + datas[i+2]，并比较 sum 和 max 以找到目前已知的最大和子数组

   考虑边界条件

   - nums 只有一个元素
   - nums 只有正数或0
   - nums 只有负数或0
   - nums 是连续的负数-连续的正数-连续的负数形式



### 缩小范围

#### 代码

```java
class Solution {
    public int maxSubArray(int[] nums) {
        // 它的实际长度应小于nums.length
        int[] datas = new int[nums.length];
        int len = 0;
        for (int i = 0; i < nums.length; i++) {
            // 去除头部负数
            if (len == 0) {
                if (nums[i] <= 0) continue;
                else {
                    len++;
                    datas[0] = nums[i];
                }
            }
            else if (len % 2 == 1) {
                if (nums[i] >= 0) datas[len - 1] += nums[i];
                else {
                    len++;
                    datas[len - 1] = nums[i];
                }
            }
            else {
                if (nums[i] <= 0) datas[len - 1] += nums[i];
                else {
                    len++;
                    datas[len - 1] = nums[i];
                }
            }
        }
        
        // nums 只有负数或0的情况
        // 逻辑错误
//        if (len == 0) return Arrays.stream(nums).sum();
        if(len == 0) return Arrays.stream(nums).max().orElse(0);
        // 去除 datas 尾部负数
        if (datas[len - 1] <= 0) len--;
        
        // 遍历 datas
        int max = datas[0];
        int sum = datas[0];
        for (int i = 0; i <= len - 3; i += 2) {
            if (sum + datas[i + 1] < 0) {
                sum = datas[i + 2];
            }
            else {
                sum += datas[i + 1] + datas[i + 2];
            }
            max = Math.max(max, sum);
        }
        
        return max;
    }
}
```

#### 结果

在测试用例提示下解决全负数元素数组情况下的逻辑错误

通过

用时6ms

击败3.30%

#### 分析

该方法本质上是动态规划，代码简化到极致是

```java
class Solution {
    public int maxSubArray(int[] nums) {
        int sum = nums[0];
        int ans = nums[0];
        for (int i = 1; i < nums.length; i++) {
            sum = Math.max(sum + nums[i], nums[i]);
            ans = Math.max(sum, ans);
        }
        return ans;
    }
}
```

大道至简





## 55. 合并区间 Merge Intervals



### 题目

[中等](https://leetcode.cn/problems/merge-intervals/description/)

>以数组 `intervals` 表示若干个区间的集合，其中单个区间为 `intervals[i] = [starti, endi]` 。请你合并所有重叠的区间，并返回 *一个不重叠的区间数组，该数组需恰好覆盖输入中的所有区间* 。
>
>**示例 1：**
>
>```
>输入：intervals = [[1,3],[2,6],[8,10],[15,18]]
>输出：[[1,6],[8,10],[15,18]]
>解释：区间 [1,3] 和 [2,6] 重叠, 将它们合并为 [1,6].
>```
>
>**示例 2：**
>
>```
>输入：intervals = [[1,4],[4,5]]
>输出：[[1,5]]
>解释：区间 [1,4] 和 [4,5] 可被视为重叠区间。
>```
>
>**示例 3：**
>
>```
>输入：intervals = [[4,7],[1,4]]
>输出：[[1,7]]
>解释：区间 [1,4] 和 [4,7] 可被视为重叠区间。
>```
>
>**提示：**
>
>- `1 <= intervals.length <= 104`
>- `intervals[i].length == 2`
>- `0 <= starti <= endi <= 104`
>
>------
>
>通过次数 1,296,052/2.5M
>
>通过率 52.2%



### 思路

1. 布尔数组

   由于 start_i 、end_i 的范围不是特别大，可以创建一个该范围大小的布尔数组，对intervals 进行遍历使对应布尔数组元素值为 true ，最后遍历布尔数组返回合并后的区间

   时间复杂度 O(mn)，其中 n 为 intervals 大小，m为 start_i、end_i 取值范围

2. 排序并计数

   考虑两个区间 [a,b] 和 [c,d]

   区间不重叠的情况有两种：

   - c > b
   - d < a

   区间重叠的情况有两种：

   - c < a 且 d >= a
   - a <= c <= b

   如果令一个区间的 start 的权重为 1，令 end 的权重为 -1

   那么使 weight = 0，使所有区间的 start、end 排序并赋予权重，遍历排序后的数组

   那么如果遇到一个 start，使 weight++，遇到一个 end，使 weight--

   注意到，当 weight != 0 时，索引总是指向一个或多个区间的内部，当 weight 从 0 变成正数时，索引开始进入一个或多个区间，当 weight 从 正数变成 0 时，索引从一个或多个区间中离开

   weight 不可能为负数，因为不存在 end < start

   因此可以通过这种方式合并区间

   可以使用 TreeMap，以 start 或 end 为键，以权重为值，特别的，当多个 start 相同时，值 >1，当多个 end 相同时，值 <-1 ，当存在一个或多个 start == end 时，值可能为 0 ，有可能不为 0

   时间复杂度 O(nlogn)



### 排序并计数

#### 代码

```java
class Solution {
    public int[][] merge(int[][] intervals) {
        // 排序并赋予权重
        TreeMap<Integer, Integer> map = new TreeMap<>();
        for (int[] ivl : intervals) {
            map.merge(ivl[0], 1, Integer::sum);
            map.merge(ivl[1], -1, Integer::sum);
        }
        // 遍历
        ArrayList<int[]> result = new ArrayList<>(intervals.length);
        int weight = 0;
        int start = -1;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            int key = entry.getKey();
            int value = entry.getValue();
            if (weight == 0) {
                // 单独的 start == end 区间
                if (value == 0) {
                    result.add(new int[]{key, key});
                }
                // 开始一个新区间
                else {
                    weight += value;
                    start = key;
                }
            }
            else {
                weight += value;
                // 结束一个区间
                if (weight == 0) {
                    result.add(new int[]{start, key});
                }
            }
        }
        return result.toArray(new int[0][0]);
    }
}
```

#### 结果

通过

用时 9ms

击败17.79%





## 70. 爬楼梯 Climbing Stairs



### 题目

[简单](https://leetcode.cn/problems/climbing-stairs/description/)

> 假设你正在爬楼梯。需要 `n` 阶你才能到达楼顶。
>
> 每次你可以爬 `1` 或 `2` 个台阶。你有多少种不同的方法可以爬到楼顶呢？
>
> **示例 1：**
>
> ```
> 输入：n = 2
> 输出：2
> 解释：有两种方法可以爬到楼顶。
> 1. 1 阶 + 1 阶
> 2. 2 阶
> ```
>
> **示例 2：**
>
> ```
> 输入：n = 3
> 输出：3
> 解释：有三种方法可以爬到楼顶。
> 1. 1 阶 + 1 阶 + 1 阶
> 2. 1 阶 + 2 阶
> 3. 2 阶 + 1 阶
> ```
>
> **提示：**
>
> - `1 <= n <= 45`
>
> ------
>
> 通过次数 1,966,037/3.5M
>
> 通过率 55.5%



### 思路

1. 递归

   考虑爬 n 台阶的方法数等于爬 n-1 台阶和爬 n-2 台阶的方法数

   ```java
   public class Solution {
       public int climbStairs(int n) {
           if (n == 0) return 1;
           else if (n < 0) return 0;
           return climbStairs(n - 1) + climbStairs(n - 2);
       }
   }
   ```

   时间复杂度 O(2^n)

2. 动态规划

   考虑爬 n 台阶的方法数等于爬 n-1 台阶和爬 n-2 台阶的方法数，即 f(n) = f(n-1) + f(n-2) ，而 f(n-1) = f(n-2) + f(n-3)

   那么已知 f(1)、f(2) 的情况下可在 O(n) 的时间复杂度下计算出 f(n)

   ```java
   class Solution {
       public int climbStairs(int n) {
           int a = 1;
           int b = 2;
           if (n == 1) return a;
           else if (n == 2) return b;
           int i = 3;
           while (true) {
               int c = a + b;
               if (i == n) return c;
               a = b;
               b = c;
               i++;
           }
       }
   }
   ```





## 76. 最小覆盖子串 Minimum Window Substring



### 题目

[困难](https://leetcode.cn/problems/minimum-window-substring/)

> 给你一个字符串 `s` 、一个字符串 `t` 。返回 `s` 中涵盖 `t` 所有字符的最小子串。如果 `s` 中不存在涵盖 `t` 所有字符的子串，则返回空字符串 `""` 。
>
> **注意：**
>
> - 对于 `t` 中重复字符，我们寻找的子字符串中该字符数量必须不少于 `t` 中该字符数量。
> - 如果 `s` 中存在这样的子串，我们保证它是唯一的答案。
>
> **示例 1：**
>
> ```
> 输入：s = "ADOBECODEBANC", t = "ABC"
> 输出："BANC"
> 解释：最小覆盖子串 "BANC" 包含来自字符串 t 的 'A'、'B' 和 'C'。
> ```
>
> **示例 2：**
>
> ```
> 输入：s = "a", t = "a"
> 输出："a"
> 解释：整个字符串 s 是最小覆盖子串。
> ```
>
> **示例 3:**
>
> ```
> 输入: s = "a", t = "aa"
> 输出: ""
> 解释: t 中两个字符 'a' 均应包含在 s 的子串中，
> 因此没有符合条件的子字符串，返回空字符串。
> ```
>
> **提示：**
>
> - `m == s.length`
> - `n == t.length`
> - `1 <= m, n <= 105`
> - `s` 和 `t` 由英文字母组成
>
> **进阶：**你能设计一个在 `o(m+n)` 时间内解决此问题的算法吗？
>
> ------
>
> 通过次数 891,088/1.8M
>
> 通过率 48.3%



### 思路

1. 遍历

   对字符串 t 的所有字符进行计数，并对字符串 s 的所有长度的子串进行字符计数并匹配

2. 哈希

   > 思路来源：
   >
   > 笔者注意到对于字符串 s 的任意子串 s' ，可以假设存在一个哈希表 <字符, List<索引>>，使得通过遍历索引就可判断是否存在指定个数的字符位于 s' 中
   >
   > 笔者又注意到假设任取字符 c 属于 t，对字符串 s 的所有 c 进行计数，得到长度与 s 相同的计数数组 scCount，使得 scCount[i] 即为 s 前 i+1 个字符中 c 字符的个数
   >
   > 那么再对 scCount 进行统计得到预测计数数组 scPre ，使得指定字符 c 的个数 k 时，scPre[i] 即为从 s[i] 开始到 s[scPre[i]] （包括两端）的子串中包含 k 个 c 字符
   >
   > ```
   > 0 1 2 3 4 5 6 7 8 9   // 字符串 s 的索引
   > 0 0 1 2 3 3 3 4 5 6   // 计数数组 scCount，指定字符 c='A'
   > 3 3 3 4 7 8 8 8 9 -1  // 预测计数数组 scPre，指定 k=2
   > O O A A A O O A A A   // 字符串 s
   > ```
   >
   > 也就是说，假设已知字符串 t 中所有字符及其个数（键值对 <字符C, 个数K>），那么可以对字符串 s 进行统计，得到所有 c（属于C）的预测计数数组 <字符C，scCount>，使得对于字符串 s 的任意索引处 i，从 <字符C，scCount> 获取所有 c（属于C）对应的 scCount[i]，并得到最大值 p ，那么索引 i 到 p （包括两端）组成的子串满足从索引 i 开始最小的“涵盖 t 所有字符的子串”
   >
   > 那么只要遍历 i ，即可找到字符串 s 中满足最小的“涵盖 t 所有字符的子串”
   >
   > 但实际情况可有所优化
   >
   > ```
   > 0 1 2 3 4 5 6 7 8 9   // 字符串 s 的索引
   > 
   > 0 0 1 2 3 3 3 4 5 6   // 计数数组 scCount，指定字符 c='A'
   > 3 3 3 4 7 8 8 8 9 -1  // 预测计数数组 scPre，指定 k=2
   > 
   > 1 2 2 2 2 3 4 4 4 4   // 计数数组 scCount，指定字符 c='B'
   > 1 5 6 6 6 6 -1
   > 
   > B B A A A B B A A A   // 字符串 s，假设 t = "AABB"
   > 
   > 字符 'A' 的索引列表：
   > [2,3,4,7,8,9]
   > 字符 'B' 的索引列表：
   > [0,1,5,6]
   > ```
   >
   > 如果仅考虑特定字符的索引列表而不是进行计数，那么可以考虑使用一个指针指向索引列表的指定位置 q，使得当前索引 i 到 q 包括了 k 个字符 c
   >
   > ```
   > 当 i = 0 时
   > 字符 'A' 的索引列表：
   > [2,3,4,7,8,9]
   >    ↑
   > 字符 'B' 的索引列表：
   > [0,1,5,6]
   >    ↑
   > ```
   >
   > 那么这些指针指向的最大值 max ，则有 i 到 max （包括两端）子串满足 i 开始的最小的“涵盖 t 所有字符的子串”
   >
   > 那么递增 i ，当 s[i-1] 对应的字符属于 t 时，可将对应字符索引列表的指针向前移动一位，如果超出了列表范围，则证明 i 及之后的字符组成的子串不满足上述条件

   考虑算法

   1. 对 t 进行字符计数得到 tCount 键值对 <字符C，字符个数K>

      时间复杂度 O(n)

   2. 对 s 进行字符索引哈希，得到 sMap 键值对 <字符C，List<字符索引P>>，其中集合字符C即为上一步的字符C，List<字符索引P> 中的值递增

      时间复杂度 O(m)

   3. 初始化预测索引集 preMap 键值对 <字符C，预测索引Q> ，使得任取 <c,q> 属于 preMap，均有 <c,k> 属于 tCount，<c,pList> 属于 sMap，使得 q 等于 k-1，并且 pList\<q\> 有效

      - 如果存在 pList\<q\> 无效，返回 ""

      时间复杂度 O(n)

   4. 令 u 为 pList\<q\> 的最大值

   5. 令 result = s[0 ~ u]

   6. 使 i = 1

   7. i <= s.length，否则返回 result

   8. 检查字符 ci = s[i-1]，如果 ci 属于 C，那么 <ci,qi> 属于preMap，使 qi 递增，并且 pList\<qi\> 有效，并且当 pList\<qi\> 大于 u 时更新 u 值

      - 如果 pList\<qi\> 无效，返回 result

   9. 如果 u - i + 1 < result.length，使 result = s[i ~ u]

   10. i++，回到步骤 7

       时间复杂度 O(m)

   上述算法时间复杂度为 O(m+n)

3. 窗口

   > 思路来源：
   >
   > 假设 t 的字符计数键值对 tCount <c,k>
   >
   > i 到 p 子串的字符计数键值对 count <c,q>
   >
   > 对于 s 的任意索引处 i，要么不存在从 i 开始的子串满足涵盖 t，要么存在一个最小的子串 i 到 p 涵盖 t
   >
   > 那么假设令 i = 0，找到 p 使得 i 到 p 子串是 i = 0 开始的最小涵盖 t 子串
   >
   > 则当字符 c = s[i]，当前子串 c 字符个数 q = count.get(c)，满足 q 等于 tCount.get(c) 时，说明不存在以 p 结尾的更短的满足涵盖 t 的子串
   >
   > 那么此时不断递增 q 直到 q1，使得 s[q1] 等于 c 导致 q = count.get(c) 大于 tCount.get(c)，即说明不存在以 q 到 q1 - 1 结尾的且以 i = 0 开头的更短的涵盖 t 的子串
   >
   > 由于 q 已经递增到 q1，此时应递增 i 直到 count.get(s[i]) 等于 tCount.get(s[i])，即说明找到以 q1 结尾的最短的满足涵盖 t 的子串
   >
   > 之后继续递增 q ，以此类推
   >
   > 即，上述算法保证对于任意 s 的索引 q，找到最短的以 q 为结尾的子串涵盖 t

   考虑算法

   1. 初始化 t 的字符计数键值对 tCount\<字符, 个数\>

      时间复杂度 O(n)

   2. 初始化当前子串字符计数键值对 count\<字符, 个数\>，确保 count 与 tCount 键相等而值为 0

      时间复杂度 O(n)

   3. i = 0, p = -1

   4. 找到第一个子串涵盖 t

      4.1 令 u = 0

      4.2 当 u >= tCount.size 时，进入步骤 5，当 p >= s.length 时，返回 ""

      4.3 递增 p，使 c = s[p] 令 count.get(c)++

      4.4 当 count.get(c) == tCount.get(c) 时，令 u++

      4.5 回到步骤 4.2

      时间复杂度 O(m)

   5. 令 start = i, end = p

   6. 当 p >= s.length 时，返回 s[start ~ end]

      时间复杂度 O(m)

   7. 如果 c = s[i]，且 count.get(c) > tCount.get(c)

      应递增 i

      7.1 令 count.get(c)--

      7.2 令 i++

      7.3 如果 p - i +1 < end - start + 1，令 start,end = i,p

      7.4 回到步骤 6

   8. 否则

      应递增 p

      8.1 令 p++, cp = s[p]

      8.2 令 count.get(cp)++

      8.3 如果 p - i +1 > end - start + 1，令 start,end = i,p

      8.4 回到步骤 6

   上述算法存在缺陷，它没有考虑 s[i] 不属于 t 的情况，且对 p 是否超出 s 索引范围的判断时机存在问题，应在 p 递增后判断

   考虑边界条件

   - s = "a", t = "b"
   - s = "a", t = "aa"
   - s = "a", t = "a"
   - s = "aa", t = "a"

   总时间复杂度 O(m+n)



### 哈希

#### 代码

```java
class Solution {
    public String minWindow(String s, String t) {
        /// 对 t 进行计数
        Map<Character, Integer> tCount = new HashMap<>();
        for (int i = 0; i < t.length(); i++) {
            char c = t.charAt(i);
            tCount.merge(c, 1, Integer::sum);
        }
        /// 对 s 进行哈希
        Map<Character, List<Integer>> sMap = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (tCount.containsKey(c)) {
                List<Integer> list = sMap.get(c);
                if (list == null) {
                    list = new ArrayList<>();
                    sMap.put(c, list);
                }
                list.add(i);
            }
        }
        // 提高鲁棒性
        if (sMap.isEmpty() || tCount.size() != sMap.size()) return "";
        
        int u = Integer.MIN_VALUE;
        /// 预测索引集
        Map<Character, Integer> preMap = new HashMap<>();
        for (Map.Entry<Character, List<Integer>> entry : sMap.entrySet()) {
            char c = entry.getKey();
            List<Integer> list = entry.getValue();
            int k = tCount.get(c);
            int q = k - 1;
            if (q >= list.size()) return "";
            preMap.put(c, q);
            u = Math.max(u, list.get(q));
        }
        
        String result = s.substring(0, u + 1);
        for (int i = 1; i <= s.length(); i++) {
            char c = s.charAt(i - 1);
            Integer q = preMap.get(c);
            if (q != null) {
                q++;
                List<Integer> list = sMap.get(c);
                if (q >= list.size()) return result;
                preMap.put(c, q);
                u = Math.max(u, list.get(q));
            }
            if (u - i + 1 < result.length()) result = s.substring(i, u + 1);
        }
        
        return result;
    }
}
```

#### 结果

在测试用例提示下解决边界条件问题

通过

用时 164ms

击败 25.69%



### 窗口

#### 代码

```java
class AdvancedSolution {
    public String minWindow(String s, String t) {
        /// t 字符计数
        Map<Character, Integer> tCount = new HashMap<>();
        for (int i = 0; i < t.length(); i++) {
            char c = t.charAt(i);
            tCount.merge(c, 1, Integer::sum);
        }
        /// 初始化当前子串字符计数
        Map<Character, Integer> count = new HashMap<>();
        for (Character c : tCount.keySet()) {
            count.put(c, 0);
        }
        /// 找到第一个子串
        int i = 0;
        int p = -1;
        int u = 0;
        while (u < tCount.size()) {
            p++;
            if (p >= s.length()) return "";
            char c = s.charAt(p);
            Integer k = count.get(c);
            if (k != null) {
                k++;
                count.put(c, k);
                if (k.equals(tCount.get(c))) u++;
            }
        }
        /// 找最小子串
        int start = i;
        int end = p;
        while (true) {
            char c = s.charAt(i);
            Integer k = count.get(c);
            if (k == null) {
                i++;
            }
            else if (k > tCount.get(c)) {
                count.put(c, k - 1);
                i++;
            }
            else {
                p++;
                if (p >= s.length()) return s.substring(start, end + 1);
                char cp = s.charAt(p);
                Integer kp = count.get(cp);
                if (kp != null) {
                    count.put(cp, kp + 1);
                }
            }
            
            if (p - i + 1 < end - start + 1) {
                start = i;
                end = p;
            }
        }
    }
}
```

#### 结果

通过

用时17ms

击败63.15%



## 128. 最长连续序列 Longest Consecutive Sequence



### 题目

[中等](https://leetcode.com/problems/longest-consecutive-sequence/)

> 给定一个未排序的整数数组 `nums` ，找出数字连续的最长序列（不要求序列元素在原数组中连续）的长度。
>
> 请你设计并实现时间复杂度为 `O(n)` 的算法解决此问题。
>
> **示例 1：**
>
> ```
> 输入：nums = [100,4,200,1,3,2]
> 输出：4
> 解释：最长数字连续序列是 [1, 2, 3, 4]。它的长度为 4。
> ```
>
> **示例 2：**
>
> ```
> 输入：nums = [0,3,7,2,5,8,4,6,0,1]
> 输出：9
> ```
>
> **示例 3：**
>
> ```
> 输入：nums = [1,0,1,2]
> 输出：3
> ```
>
> **提示：**
>
> - `0 <= nums.length <= 105`
> - `-109 <= nums[i] <= 109`
>
> ------
>
> 通过次数 1,182,539/2.4M
>
> 通过率 49.7%



### 思路

1. 哈希表

   为了保证时间复杂度为 O(n) ，不应对数组进行排序，另外由题可知数组元素值范围很大，也不应使用bitmap

   因此可以考虑使用哈希表，哈希表插入一个元素和取出一个元素的时间复杂度均为 O(1) 

   那么对原数组进行遍历并构建哈希表，然后反复对哈希表进行如下操作：

   1. 检查哈希表的一个元素`i`，并从哈希表中寻找并移除`i`的前后连续值的所有元素
   2. 统计最长连续序列
   3. 如果哈希表不为空，那么重新执行1操作



### 哈希表

#### 代码1

```java
class Solution {
    public int longestConsecutive(int[] nums) {
        int result = 0;
        HashSet<Integer> set = new HashSet<>(nums.length * 2);
        for (int i : nums) {
            set.add(i);
        }
        while (true) {
            Iterator<Integer> iterator = set.iterator();
            if (!iterator.hasNext()) break;
            int i = iterator.next();
            set.remove(i);
            int len = 1;
            int n = i;
            while (true) {
                if (n == Integer.MAX_VALUE) break;
                n++;
                if (set.remove(n)) len++;
                else break;
            }
            n = i;
            while (true) {
                if (n == Integer.MIN_VALUE) break;
                n--;
                if (set.remove(n)) len++;
                else break;
            }
            result = Math.max(result, len);
        }
        return result;
    }
}
```

#### 结果1

超时

#### 分析1

经过仔细的测试后发现，重复创建迭代器是性能影响最大的因素

为什么迭代器性能影响如此之大尚未得知，但可以知道的是除了反射暂无别的手段高效地从哈希表中任取一个元素

#### 代码2

```java
class Solution {
    public int longestConsecutive(int[] nums) {
        int result = 0;
        HashSet<Integer> set = new HashSet<>(nums.length * 2);
        for (int i : nums) {
            set.add(i);
        }
        for (int c = 0; c < nums.length; c++) {
            int i = nums[c];
            if (!set.remove(i)) continue;
            
            int len = 1;
            int n = i;
            while (true) {
                if (n == Integer.MAX_VALUE) break;
                n++;
                if (set.remove(n)) len++;
                else break;
            }
            n = i;
            while (true) {
                if (n == Integer.MIN_VALUE) break;
                n--;
                if (set.remove(n)) len++;
                else break;
            }
            result = Math.max(result, len);
        }
        return result;
    }
}
```

#### 结果2

通过

用时21ms，击败89.90%





## 152. 乘积最大子数组 Maximum Product Subarray



### 题目

[中等]()

> 给你一个整数数组 `nums` ，请你找出数组中乘积最大的非空连续 子数组（该子数组中至少包含一个数字），并返回该子数组所对应的乘积。
>
> 测试用例的答案是一个 **32-位** 整数。
>
> **示例 1:**
>
> ```
> 输入: nums = [2,3,-2,4]
> 输出: 6
> 解释: 子数组 [2,3] 有最大乘积 6。
> ```
>
> **示例 2:**
>
> ```
> 输入: nums = [-2,0,-1]
> 输出: 0
> 解释: 结果不能为 2, 因为 [-2,-1] 不是子数组。
> ```
>
> **提示:**
>
> - `1 <= nums.length <= 2 * 104`
> - `-10 <= nums[i] <= 10`
> - `nums` 的任何子数组的乘积都 **保证** 是一个 **32-位** 整数
>
> ------
>
> 通过次数 631,772/1.5M
>
> 通过率 43.4%



### 思路

1. 分治

   > 考虑整数数组 nums 元素 a<sub>0</sub> 到 a<sub>n-1</sub> 有以下关键特性
   >
   > a * 0 = 0
   >
   > a * -1 = -1
   >
   > 假设 nums 数组全为正数
   >
   > ```
   > [2, 3, 1, 4]
   > ```
   >
   > 那么最大乘积即为所有元素的乘积
   >
   > 假设 nums 数组包含 0 元素和正数
   >
   > ```
   > [2, 3, 0, 1, 4]
   > ```
   >
   > 那么可以 0 元素分隔成多个子数组，最大乘积为 0 和多个子数组各自的元素乘积中的最大值
   >
   > 假设 nums 数组包含正数和负数，如果负数的个数为偶数
   >
   > ```
   > [2, -3, 1, -4]
   > ```
   >
   > 那么最大乘积即为所有元素的乘积
   >
   > 如果负数的个数为奇数，如：
   >
   > ```
   > [2, -3, 1, -4, -5, 2]
   > ```
   >
   > 那么以头尾第一个负数分隔得到两个子数组，最大乘积即为两个子数组各自的乘积的最大值：
   >
   > ```
   > [2, -3, 1, -4, -5, 2]
   > [2, -3, 1, -4] 乘积 = 24
   >        [1, -4, -5, 2] 乘积 = 40
   > ```
   >
   > 因此上述思路即以 0 元素分割原数组并各自求最大乘积
   >
   > 考虑以下边界条件：
   >
   > - nums 仅包含 0 元素
   >
   > - nums 不包含 0 元素
   >
   > - 一个分治求解的子数组中仅包含一个负数
   >
   >   ```
   >   [-2]
   >   ```
   >
   >   或
   >
   >   ```
   >   [1, 2, 5, 1, -2, 2]
   >   ```

   时间复杂度 O(n)



### 分治

#### 代码

```java
class Solution {
    
    static int result;
    
    public int maxProduct(int[] nums) {
        result = Integer.MIN_VALUE;
        int negCount = 0;
        int i = 0;
        int start = 0;
        int product = 1;
        while (i < nums.length) {
            if (nums[i] == 0) {
                result = Math.max(result, 0);
                process(nums, start, i - 1, negCount, product);
                i++;
                negCount = 0;
                start = i;
                product = 1;
                continue;
            }
            product *= nums[i];
            if (nums[i] < 0) negCount++;
            i++;
        }
        process(nums, start, i - 1, negCount, product);
        return result;
    }
    
    void process(int[] nums, int start, int end, int negCount, int product) {
        if (end - start < 0) return;
        if (negCount % 2 == 0) {
            result = Math.max(product, result);
            return;
        }
        if (negCount == 1) {
            if (end - start == 0) {
                result = Math.max(result, product);
                return;
            }
            int p = 1;
            for (int i = start; i <= end; i++) {
                if (nums[i] < 0) break;
                p *= nums[i];
            }
            result = Math.max(result, p);
            p = 1;
            for (int i = end; i >= start; i--) {
                if (nums[i] < 0) break;
                p *= nums[i];
            }
            result = Math.max(result, p);
            return;
        }
        int p = 1;
        for (int i = start; i <= end; i++) {
            p *= nums[i];
            if (nums[i] < 0) break;
        }
        result = Math.max(result, product / p);
        p = 1;
        for (int i = end; i >= start; i--) {
            p *= nums[i];
            if (nums[i] < 0) break;
        }
        result = Math.max(result, product / p);
    }
}
```

#### 结果

通过

用时1ms

击败97.75%



## 198. 打家劫舍 House Robber



### 题目

[中等](https://leetcode.cn/problems/house-robber/description/)

> 你是一个专业的小偷，计划偷窃沿街的房屋。每间房内都藏有一定的现金，影响你偷窃的唯一制约因素就是相邻的房屋装有相互连通的防盗系统，**如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警**。
>
> 给定一个代表每个房屋存放金额的非负整数数组，计算你 **不触动警报装置的情况下** ，一夜之内能够偷窃到的最高金额。
>
> **示例 1：**
>
> ```
> 输入：[1,2,3,1]
> 输出：4
> 解释：偷窃 1 号房屋 (金额 = 1) ，然后偷窃 3 号房屋 (金额 = 3)。
>      偷窃到的最高金额 = 1 + 3 = 4 。
> ```
>
> **示例 2：**
>
> ```
> 输入：[2,7,9,3,1]
> 输出：12
> 解释：偷窃 1 号房屋 (金额 = 2), 偷窃 3 号房屋 (金额 = 9)，接着偷窃 5 号房屋 (金额 = 1)。
>      偷窃到的最高金额 = 2 + 9 + 1 = 12 。
> ```
>
> **提示：**
>
> - `1 <= nums.length <= 100`
> - `0 <= nums[i] <= 400`
>
> ------
>
> 通过次数 1,393,309/2.5M
>
> 通过率 56.1%



### 思路

1. 动态规划

   考虑 nums 的 i 元素已经被选中，那么下一个被选中的元素应该是 i+2 或 i+3 （确保金额最大）

   ```
   i i+1 i+2 i+3
   √      √
   √          √
   ```

   上一个被选中的元素应该是 i-2 或 i-3（确保金额最大）

   ```
   i-3 i-2 i-1 i
        √      √
    √          √
   ```

   如果计 f(i) 为 nums 从 0 到 i 元素组成的子数组中，i 元素已经被选中的情况下的金额最大值，那么 f(i) = max( f(i-2)+nums[i] , f(i-3)+nums[i] )

   对于 0 到 i 元素的子数组，如果金额最大，那么其结尾部分可能是

   ```
   i-3 i-2 i-1 i
        √      √
    √          √
   ...      √
   ```

   那么计 g(i) 为 0 到 i 元素组成的子数组的金额最大值，g(i) = max( f(i) , f(i-1) )

   特别的，由于 i 取值为 0 到 n-1，需要考虑 f(-3)、f(-2)、f(-1)、f(0) 的值和 g(0) 的值

   时间复杂度 O(n)



### 动态规划

#### 代码

```java
class Solution {
    public int rob(int[] nums) {
        if (nums.length == 0) return 0;
        else if (nums.length == 1) return nums[0];
        else if (nums.length == 2) return Math.max(nums[0], nums[1]);
        else if (nums.length == 3) return Math.max(nums[0] + nums[2], nums[1]);
        int[] f = new int[nums.length];
        f[0] = nums[0];
        f[1] = nums[1];
        f[2] = nums[0] + nums[2];
        for (int i = 3; i < f.length; i++) {
            f[i] = Math.max(f[i - 2] + nums[i], f[i - 3] + nums[i]);
        }
        return Math.max(f[f.length - 1], f[f.length - 2]);
    }
}
```

#### 结果

通过

用时0ms

击败100.00%



## 239. 滑动窗口最大值 Sliding Window Maximum



### 题目

[困难](https://leetcode.cn/problems/sliding-window-maximum/)

> 给你一个整数数组 `nums`，有一个大小为 `k` 的滑动窗口从数组的最左侧移动到数组的最右侧。你只可以看到在滑动窗口内的 `k` 个数字。滑动窗口每次只向右移动一位。
>
> 返回 *滑动窗口中的最大值* 。
>
> **示例 1：**
>
> ```
> 输入：nums = [1,3,-1,-3,5,3,6,7], k = 3
> 输出：[3,3,5,5,6,7]
> 解释：
> 滑动窗口的位置                最大值
> ---------------               -----
> [1  3  -1] -3  5  3  6  7       3
>  1 [3  -1  -3] 5  3  6  7       3
>  1  3 [-1  -3  5] 3  6  7       5
>  1  3  -1 [-3  5  3] 6  7       5
>  1  3  -1  -3 [5  3  6] 7       6
>  1  3  -1  -3  5 [3  6  7]      7
> ```
>
> **示例 2：**
>
> ```
> 输入：nums = [1], k = 1
> 输出：[1]
> ```
>
> **提示：**
>
> - `1 <= nums.length <= 105`
> - `-104 <= nums[i] <= 104`
> - `1 <= k <= nums.length`
>
> ------
>
> 通过次数 952,935/1.9M
>
> 通过率 49.8%



### 思路

1. 红黑树

   比起对每个滑动窗口进行扫描以获取最大值这一时间复杂度高达 O(n<sup>2</sup>) 的算法，红黑树可以在单个元素的插入、查找、删除的时间复杂度在 O(logn) 的情况下提供排序功能

   那么可以考虑使用`TreeMap`，以元素值为键，以元素值在滑动窗口中的次数为值，使用`TreeMap.lastKey`方法即可获取最大的键

   时间复杂度为 O(nlogn)

2. 扫描

   记录当前滑动窗口内的最大值 m 及该最大值出现的次数 c，遍历数组 nums ，如果nums[i] >= m（i >= k），则更新 m 和 c，如果 nums[i - k] == m，则 c 递减

   当 c 为 0 时，说明当前滑动窗口无已知最大值，对当前窗口进行一次扫描找到 m 及 c ，此操作时间复杂度为 O(k)

   该算法实现简单，假设对于每个滑动窗口最大值的分布平均，那么在 k << n 的情况下该算法平均需要进行 n/(k/2) 次对滑动窗口的扫描操作，平均时间复杂度为 O(2n) 

   最坏的情况下 nums 是递增的，此时时间复杂度将退化至 O(nk)

   最好的情况下不需要对滑动窗口进行扫描（除了第一个滑动窗口），此时时间复杂度为 O(n)

3. **上述方案均表现不佳**



### 红黑树

#### 代码

```java
class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        int[] result = new int[nums.length - k + 1];
        TreeMap<Integer, Integer> map = new TreeMap<>();
        for (int i = 0; i < nums.length; i++) {
            map.compute(nums[i], (key, count) -> count == null ? 1 : count + 1);
            if (i >= k) {
                Integer count = map.get(nums[i - k]);
                Integer ignore = count == 1 ? map.remove(nums[i - k]) : map.put(nums[i - k], count - 1);
            }
            if (i >= k - 1) result[i - k + 1] = map.lastKey();
        }
        return result;
    }
}
```

#### 结果

通过

用时293ms

击败5.10%



### 扫描

#### 代码

```java
class AdvancedSolution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        int[] result = new int[nums.length - k + 1];
        int m = Integer.MIN_VALUE;
        int c = 0;
        /// 初始扫描
        for (int i = 0; i < k; i++) {
            if (nums[i] > m) {
                m = nums[i];
                c = 1;
            }
            else if (nums[i] == m) {
                c++;
            }
        }
        result[0] = m;
        /// 后续遍历
        for (int i = k; i < nums.length; i++) {
            if (nums[i - k] == m) c--;
            if (nums[i] > m) {
                m = nums[i];
                c = 1;
            }
            else if (nums[i] == m) {
                c++;
            }
            if (c == 0) {
                m = Integer.MIN_VALUE;
                for (int j = i - k + 1; j <= i; j++) {
                    if (nums[j] > m) {
                        m = nums[j];
                        c = 1;
                    }
                    else if (nums[j] == m) {
                        c++;
                    }
                }
            }
            result[i - k + 1] = m;
        }
        return result;
    }
}
```

#### 结果

通过

用时1410ms

击败5.10%





## 279. 完全平方数 Perfect Squares



### 题目

[中等](https://leetcode.cn/problems/perfect-squares/)

> 给你一个整数 `n` ，返回 *和为 `n` 的完全平方数的最少数量* 。
>
> **完全平方数** 是一个整数，其值等于另一个整数的平方；换句话说，其值等于一个整数自乘的积。例如，`1`、`4`、`9` 和 `16` 都是完全平方数，而 `3` 和 `11` 不是。
>
> **示例 1：**
>
> ```
> 输入：n = 12
> 输出：3 
> 解释：12 = 4 + 4 + 4
> ```
>
> **示例 2：**
>
> ```
> 输入：n = 13
> 输出：2
> 解释：13 = 4 + 9
> ```
>
> **提示：**
>
> - `1 <= n <= 104`
>
> ------
>
> 通过次数 796,575/1.2M
>
> 通过率 68.3%



### 思路

1. 动态规划

   定义 L = { k<sup>2</sup> | k ∈ Z<sup>+</sup> , k<sup>2</sup> <= n}

   定义 f(n) 为组成整数 n 的完全平方数的最小数量

   那么在已知 f(1) 到 f(n-1) 的值的情况下，f(n) = min{ f(p) + f(q) }，使得 n = p + q

   因此可考虑以下算法：

   定义数组 f 存储 f(1) 到 f(n) 的值，使 f(L) = 1

   如果 f(n) == 1，返回 f(n)

   遍历 f ，当 f(i) != 0 时，遍历 p 从 1 到 i/2（向下取整），q = i - p，找到 min{ f(p) + f(q) } 赋值给 f(i)

   返回 f(n)

   时间复杂度为 O(n<sup>2</sup>)

2. 优化的动态规划

   考虑 f(n) = min{ f(p) + f(q) }，那么仅需考虑 p ∈ { t<sup>2</sup> | t ∈ Z<sup>+</sup> , t<sup>2</sup> <= i }

   这是因为 n 必然由若干个 t<sup>2</sup> 组成

   时间复杂度 O(n<sup>1.5</sup>)

3. 数学方法

   尚未想到



### 动态规划

#### 代码

```java
class Solution {
    public int numSquares(int n) {
        int[] f = new int[n + 1];
        /// 使 f(k^2) = 1
        for (int k = 1; ; k++) {
            int u = k * k;
            if (u > n) break;
            f[u] = 1;
        }
        if (f[n] > 0) return f[n];
        /// 动态规划
        for (int i = 1; i <= n; i++) {
            if (f[i] != 0) continue;
            int min = Integer.MAX_VALUE;
            for (int p = 1; p <= i / 2; p++) {
                min = Math.min(min, f[p] + f[i - p]);
            }
            f[i] = min;
        }
        return f[n];
    }
}
```

#### 结果

通过

用时1073ms

击败5.00%



### 优化的动态规划

#### 代码

```java
class AdvancedSolution {
    public int numSquares(int n) {
        int[] f = new int[n + 1];
        /// 使 f(k^2) = 1
        for (int k = 1; ; k++) {
            int u = k * k;
            if (u > n) break;
            f[u] = 1;
        }
        if (f[n] > 0) return f[n];
        /// 动态规划
        for (int i = 1; i <= n; i++) {
            if (f[i] != 0) continue;
            int min = Integer.MAX_VALUE;
            for (int k = 1; ; k++) {
                int p = k * k;
                if(p > i) break;
                min = Math.min(min, f[p] + f[i - p]);
            }
            f[i] = min;
        }
        return f[n];
    }
}
```

#### 结果

通过

用时26ms

击败80.14%





## 300. 最长递增子序列 Longest Increasing Subsequence



### 题目

[中等](https://leetcode.cn/problems/longest-increasing-subsequence/description/)

>给你一个整数数组 `nums` ，找到其中最长严格递增子序列的长度。
>
>**子序列** 是由数组派生而来的序列，删除（或不删除）数组中的元素而不改变其余元素的顺序。例如，`[3,6,2,7]` 是数组 `[0,3,1,6,2,2,7]` 的子序列。
>
>**示例 1：**
>
>```
>输入：nums = [10,9,2,5,3,7,101,18]
>输出：4
>解释：最长递增子序列是 [2,3,7,101]，因此长度为 4 。
>```
>
>**示例 2：**
>
>```
>输入：nums = [0,1,0,3,2,3]
>输出：4
>```
>
>**示例 3：**
>
>```
>输入：nums = [7,7,7,7,7,7,7]
>输出：1
>```
>
>**提示：**
>
>- `1 <= nums.length <= 2500`
>- `-104 <= nums[i] <= 104`
>
>**进阶：**
>
>- 你能将算法的时间复杂度降低到 `O(n log(n))` 吗?
>
>------
>
>通过次数 1,346,212/2.3M
>
>通过率 57.9%



### 思路

1. 动态规划

   对于输入数组 nums：m<sub>0</sub> 到 m<sub>n-1</sub>

   考虑 f[i] 为 m<sub>i</sub> 为递增子序列最大值时的子序列最大长度

   那么有状态转移方程

   f[i] = max{ f[p] + 1 }，其中 p < i 且 m<sub>p</sub> < m<sub>i</sub>

   如果对于一个 f[i] 不存在 p < i 且 m<sub>p</sub> < m<sub>i</sub>，那么 f[i] = 1

   返回值即为 f[0] 到 f[n-1] 中的最大值

   时间复杂度为 O(n<sup>2</sup>)



### 动态规划

#### 代码

```java
public class Solution {
    public int lengthOfLIS(int[] nums) {
        int[] f = new int[nums.length];
        int result = 1;
        for (int i = 0; i < nums.length; i++) {
            int max = 1;
            for (int p = 0; p < i; p++) {
                if (nums[p] < nums[i])
                    max = Math.max(max, f[p] + 1);
            }
            f[i] = max;
            result = Math.max(result, max);
        }
        return result;
    }
}
```

#### 结果

通过

用时50ms

击败78.03%



## 322. 零钱兑换 Coin Change



### 题目

[中等](https://leetcode.cn/problems/coin-change/)

> 给你一个整数数组 `coins` ，表示不同面额的硬币；以及一个整数 `amount` ，表示总金额。
>
> 计算并返回可以凑成总金额所需的 **最少的硬币个数** 。如果没有任何一种硬币组合能组成总金额，返回 `-1` 。
>
> 你可以认为每种硬币的数量是无限的。
>
> **示例 1：**
>
> ```
> 输入：coins = [1, 2, 5], amount = 11
> 输出：3 
> 解释：11 = 5 + 5 + 1
> ```
>
> **示例 2：**
>
> ```
> 输入：coins = [2], amount = 3
> 输出：-1
> ```
>
> **示例 3：**
>
> ```
> 输入：coins = [1], amount = 0
> 输出：0
> ```
>
> **提示：**
>
> - `1 <= coins.length <= 12`
> - `1 <= coins[i] <= 231 - 1`
> - `0 <= amount <= 104`
>
> ------
>
> 通过次数 1,197,627/2.3M
>
> 通过率 51.8%



### 思路

1. 动态规划

   考虑 coins 数组的所有元素 c<sub>0</sub> 到 c<sub>m-1</sub> （从小到大排序）以及 amount 的值 n

   定义 f(n) 为组成金额 n 的最少硬币个数（n >= 0），当无法组成金额 n 时，f(n) = -1

   特别的，f(0) = 0

   如果 f(n) != -1，那么 n 中必然包含一个 c<sub>i</sub> ，因此考虑 f(n) 的值可为 f(n - c<sub>i</sub>) + 1

   因此 f(n) = min{ f(n - c<sub>i</sub>) + 1 }，0 <= i <= m - 1

   那么考虑以下算法：

   定义数组 f[0 ~ n]，遍历 c<sub>i</sub> 使得 f[c<sub>i</sub>] = 1，f[0] = 0

   遍历 p 从 1 到 n，当 f[p] != 1 时

   - 遍历 c<sub>i</sub> < p，计算 f[p - c<sub>i</sub>] + 1 的最小值（除了 f[p - c<sub>i</sub>] == -1），赋给 f[p]
   - 如果 p < c<sub>0</sub>，则 f[p] = -1
   - 如果所有 f[p - c<sub>i</sub>] == -1，则 f[p] = -1

   返回 f[n]

   时间复杂度为 O(mn)

   由于 m 很小（不超过12），该算法可视为 O(n)

   如果考虑 coins 需要排序，时间复杂度为 O(mlogm + mn)



### 动态规划

#### 代码

```java
class Solution {
    public int coinChange(int[] coins, int amount) {
        Arrays.sort(coins);
        /// 动态规划数组
        int[] f = new int[amount + 1];
        for (int c : coins)
            if (c <= amount) f[c] = 1;
        for (int p = 1; p <= amount; p++) {
            if (f[p] > 0) continue;
            int min = Integer.MAX_VALUE;
            for (int i = 0; i < coins.length; i++) {
                if (p <= coins[i]) break;
                if (f[p - coins[i]] == -1) continue;
                min = Math.min(min, f[p - coins[i]] + 1);
            }
            if (min == Integer.MAX_VALUE) f[p] = -1;
            else f[p] = min;
        }
        return f[amount];
    }
}
```

#### 结果

通过

用时22ms

击败14.31%





## 438. 找到字符串中所有字母异位词 Find All Anagrams in a String



### 题目

[中等](https://leetcode.cn/problems/find-all-anagrams-in-a-string/)

> 给定两个字符串 `s` 和 `p`，找到 `s` 中所有 `p` 的 **异位词** 的子串，返回这些子串的起始索引。不考虑答案输出的顺序。
>
> **示例 1:**
>
> ```
> 输入: s = "cbaebabacd", p = "abc"
> 输出: [0,6]
> 解释:
> 起始索引等于 0 的子串是 "cba", 它是 "abc" 的异位词。
> 起始索引等于 6 的子串是 "bac", 它是 "abc" 的异位词。
> ```
>
>  **示例 2:**
>
> ```
> 输入: s = "abab", p = "ab"
> 输出: [0,1,2]
> 解释:
> 起始索引等于 0 的子串是 "ab", 它是 "ab" 的异位词。
> 起始索引等于 1 的子串是 "ba", 它是 "ab" 的异位词。
> 起始索引等于 2 的子串是 "ab", 它是 "ab" 的异位词。
> ```
>
> **提示:**
>
> - `1 <= s.length, p.length <= 3 * 104`
> - `s` 和 `p` 仅包含小写字母
>
> ------
>
> 通过次数 791,780/1.5M
>
> 通过率 54.4%



### 思路

1. 计数

   对于字符串`s`的任意索引处`s[i]`，需知道`s[i]`到`s[i+p.length-1]`处的所有字符及其出现次数是否匹配字符串`p`

   由于`s`和`p`仅包含小写字母，可考虑使用一个长度为26的`int`的计数数组，使其存储一个字符串所有小写字母的出现次数

   那么如果使用计数数组`t`对字符串`p`进行计数并存储，并且，对于字符串`s`的任意索引处`s[i]`均由计数数组`n_i`存储有`s[i]`到`s[i+p.length-1]`处的所有小写字母的计数，那么仅需匹配`t`和`n_i`即可知索引`i`是否符合要求，那么匹配字符串`s`的所有位置的时间复杂度为 O(n)

   对字符串`s`的任意索引处`s[i]`生成所有`n_i`将导致时间复杂度上升至 O(nm) （其中m为字符串`p`的长度），现考虑字符串`s`的两个子串`s[i]`到`s[i+p.length-1]`与`s[i+1]`到`s[i+p.length]`，可知两个子串仅有两个字符的不同，那么其计数数组`n_i`与`n_i+1`也最多有两个元素的不同

   那么考虑在已知`s[i+1]`到`s[i+p.length]`的计数数组`n_i+1`情况下，仅需通过查看`s[i]`和`s[i+p.length]`的元素值即可通过两步修改操作获得`n_i`

   因此可以使用以下算法：

   1. 倒序遍历字符串`s`直到`i == s.length - p.length`，并得到计数数组`ni`
   2. `i`递减，如果`i < 0`，则中止
   3. 查找`s[i]`和`s[i+p.length]`元素值，并修改计数数组`ni`
   4. 匹配计数数组`ni`与目标计数数组`n`，当两者相同时`i`为结果之一
   5. 返回步骤 2

   该算法时间复杂度为 O(n)

   需考虑的边界条件：

   1. `s.length <= p.length`
   2. `p.length <= 1`

2. 哈希

   与上述的“1. 计数”法类似，但使用一个哈希值来表示每个`s[i]`到`s[i+p.length-1]`，并且有以下哈希算法：`hash(str) = hash(str[0]) + ... + hash(str[str.length-1])`

   这个算法保证了在已知`s[i+1]`到`s[i+p.length]`的哈希值的情况下仅需两步计算即可获得`s[i]`到`s[i+p.length]`的哈希值，然后匹配字符串`p`的哈希，如果相等，则匹配详细的字符串内容以进一步确认是否为目标

   这个思路适用于当`p`中不仅包含小写字母、且`s`中匹配的索引较少的情景，且需要设计良好的哈希函数，理想状况下时间复杂度为 O(n) ，最坏情况下时间复杂度为 O(nm) （如当对每一个`i`均匹配`p`时）



### 计数

#### 代码

```java
class Solution {
    public List<Integer> findAnagrams(String s, String p) {
        /// 结果
        List<Integer> result = new ArrayList<>();
        /// 对字符串p进行计数
        int[] n = new int[26];
        for (int i = 0; i < p.length(); i++) {
            char c = p.charAt(i);
            n[c - 'a']++;
        }
        /// 鲁棒性保证
        if (p.length() > s.length() || p.isEmpty()) return result;
        /// 对字符串s进行倒序遍历计算末尾p.length个字符的计数
        int[] ni = new int[26];
        for (int i = s.length() - 1; i >= s.length() - p.length(); i--) {
            char c = s.charAt(i);
            ni[c - 'a']++;
        }
        /// 匹配
        if (Arrays.equals(n, ni)) {
            result.add(s.length() - p.length());
        }
        /// 计数并匹配
        for (int i = s.length() - p.length() - 1; i >= 0; i--) {
            // 修改计数数组
            ni[s.charAt(i) - 'a']++;
            ni[s.charAt(i + p.length()) - 'a']--;
            // 匹配
            if (Arrays.equals(n, ni)) {
                result.add(i);
            }
        }
        return result;
    }
}
```

#### 结果

通过

用时9ms

击败61.54%





## 560. 和为 K 的子数组 Subarray Sum Equals K



### 题目

[中等](https://leetcode.cn/problems/subarray-sum-equals-k/)

> 给你一个整数数组 `nums` 和一个整数 `k` ，请你统计并返回 *该数组中和为 `k` 的子数组的个数* 。
>
> 子数组是数组中元素的连续非空序列。
>
> **示例 1：**
>
> ```
> 输入：nums = [1,1,1], k = 2
> 输出：2
> ```
>
> **示例 2：**
>
> ```
> 输入：nums = [1,2,3], k = 3
> 输出：2
> ```
>
> **提示：**
>
> - `1 <= nums.length <= 2 * 104`
> - `-1000 <= nums[i] <= 1000`
> - `-107 <= k <= 107`
>
> ------
>
> 通过次数 834,526/1.8M
>
> 通过率 45.4%



### 思路

1. 遍历

   对于长度为`n`的数组`nums`，需要考虑从长度1到长度n的共计`n+(n-1)+...+1`个子数组的和是否与目标`k`匹配，当已知子数组`n[i]`到`n[i+p]`的和为`sum_i`时，子数组`n[i+1]`到`n[i+p+1]`的和`sum_i+1 = sum_i + n[i+p+1] - n[i]`

   时间复杂度为 O(n)

2. 哈希表 + 前缀和

   考虑数组的局部`nums[0]`到`nums[i-1]`与新增元素`nums[i]`，那么对于新增元素`nums[i]`需要考虑的包含它的子数组有`{nums[i]}`、`{nums[i-1],nums[i]}`、...、`{nums[0],...,nums[i-1],nums[i]}`共计`i`个子数组，并判断这`i`个子数组各自的和是否为`k`

   分别计算这个`i`个子数组的各自的和将导致算法时间复杂度退化至 O(i) ，那么对长度为`n`的数组`nums`总的时间复杂度将退化至 O(n<sup>2</sup>)

   换个角度，对于`i-1`个子数组`{nums[i-1],nums[i]}`、`{nums[i-2],nums[i-1],nums[i]}`、...、`{nums[0],...,nums[i-1],nums[i]}`均包含元素`nums[i]`，也就是说需判断子数组`{nums[i-1]}`、`{nums[i-2],nums[i-1]}`、...、`{nums[0],...,nums[i-1]}`各自和是否等于`k-nums[i]`

   因此假设存在某种极快的方式，能够实现从某种数据结构中以时间复杂度小于 O(i) 的操作找到值为`k-nums[i]`的子数组

   如果用`sum(0,p)`表示`nums[0]+...+nums[p]`，注意到`{nums[i-1]}`、`{nums[i-2],nums[i-1]}`、...、`{nums[0],...,nums[i-1]}`等`i-1`个子数组实际上可以转换成`sum(0,i-1)-sum(0,i-2)`、`sum(0,i-1)-sum(0,i-3)`、...、`sum(0,i-1)-sum(0,0)`、`sum(0,i-1)`，也就是判断这`i-1`个式子是否等于`k-nums[i]`

   对于`sum(0,i-1)-sum(0,i-2)`、`sum(0,i-1)-sum(0,i-3)`、...、`sum(0,i-1)-sum(0,0)`共计`i-2`个式子又均包含`sum(0,i-1)`，因此问题再次转换为判断`sum(0,i-1)+nums[i]-k = sum(0,i) - k`是否匹配`sum(0,i-2)`到`sum(0,0)`

   那么现在可以考虑存在某种数据结构计算存储了对于数组`nums`的从`sum(0,0)`到`sum(0,n)`的所有值，并且以O(1)的时间复杂度通过值索引到对应的`sum(0,p)`

   因此可以考虑使用如下算法：

   1. 遍历 nums，计算并存储到数组 sums，使得 sums[i] = nums[0] + ... + nums[i]
   2. 哈希化数组 sums 存储到 HashMap <Integer, List\<Integer\>>，其以sums的元素值为键，以元素出现的所有位置索引为值，并保证List\<Integer\>中的元素值递增
   3. 令 i = 2
   4. 判断 i 是否超出 nums.length - 1
   5. 判断 nums[i] 和 sums[i] 是否为 k，如果是，使结果值增加
   6. 查找值为 sums[i] - k 的 sums 元素，且这些元素下标 p 应满足 0 <= p <= i-2，如果有满足的，使结果值增加
   7. 使 i 递增，回到步骤 4

   应考虑的边界条件：

   1. 短数组，如当 nums.length 为1或2时



### 遍历

#### 代码

```java
class Solution {
    public int subarraySum(int[] nums, int k) {
        int result = 0;
        /// 从长度为 1 开始直到长度为 nums.length 遍历 nums
        int headSum = 0;
        for (int len = 1; len <= nums.length; len++) {
            headSum += nums[len - 1];
            if (headSum == k) result++;
            int sum = headSum;
            for (int i = 1; i <= nums.length - len; i++) {
                sum -= nums[i - 1];
                sum += nums[i + len - 1];
                if (sum == k) result++;
            }
        }
        return result;
    }
}

```

#### 结果

用时2249ms

击败4.99%



### 哈希表

#### 代码1

```java
class AdvancedSolution {
    public int subarraySum(int[] nums, int k) {
        int result = 0;
        /// sums[i] 即为 nums 前 i+1 个元素的和
        int[] sums = new int[nums.length];
        {
            int sum = 0;
            for (int i = 0; i < nums.length; i++) {
                sum += nums[i];
                sums[i] = sum;
            }
        }
        /// 哈希表处理 sums
        HashMap<Integer, List<Integer>> map = new HashMap<>(sums.length * 2);
        for (int i = 0; i < sums.length; i++) {
            List<Integer> list = map.get(sums[i]);
            if (list == null) {
                list = new ArrayList<>(3);
                map.put(sums[i], list);
            }
            list.add(i);
        }
        /// 查看前 2 个元素
        if (nums.length >= 1) {
            if (nums[0] == k) result++;
        }
        if (nums.length >= 2) {
            if (nums[1] == k) result++;
            if (sums[1] == k) result++;
        }
        /// 查看之后的元素
        for (int i = 2; i < nums.length; i++) {
            if (nums[i] == k) result++;
            if (sums[i] == k) result++;
            List<Integer> list = map.get(sums[i] - k);
            if (list != null) {
                for (int j = 0; j < list.size(); j++) {
                    if (list.get(j) <= i - 2) result++;
                    else break;
                }
            }
        }
        return result;
    }
}
```

#### 结果1

通过

用时1786ms

击败4.99%

#### 分析1

注意到在此代码中如果sums的元素值分布特别分散时，sums的长度将接近n，也就意味着将新建接近n的数量的List，造成性能下降



#### 优化思路1-A

对于任意sums[i]，实际上将寻找sums数组0到i-2中值为sums[i]-k的元素的数量，那么可以考虑优化至不使用List，使用数组counts存储，使得count[i]的值即为sums数组0到i-2中值为sums[i]-k的元素的数量，而构建counts数组的过程仍需要哈希表，这里采用HashMap，以sums中的元素值为键，以当前遍历至i时sums数组0到i-2中值为sums[i]-k的元素的数量为值

#### 代码1-A

```java
class AdvancedSolution2 {
    public int subarraySum(int[] nums, int k) {
        int result = 0;
        /// sums[i] 即为 nums 前 i+1 个元素的和
        int[] sums = new int[nums.length];
        {
            int sum = 0;
            for (int i = 0; i < nums.length; i++) {
                sum += nums[i];
                sums[i] = sum;
            }
        }
        /// 哈希表处理 sums 到 counts，
        /// counts[i]的值即为sums的0到i-2子数组中值为sums[i]-k的元素的数量
        int[] counts = new int[sums.length];
        {
            HashMap<Integer, Integer> map = new HashMap<>(counts.length * 2);
            for (int i = 0; i < sums.length - 2; i++) {
                Integer sumCount = map.get(sums[i]);
                sumCount = sumCount == null ? 1 : sumCount + 1;
                map.put(sums[i], sumCount);
                
                Integer targetCount = map.get(sums[i + 2] - k);
                counts[i + 2] = targetCount == null ? 0 : targetCount;
            }
        }
        /// 查看前 2 个元素
        if (nums.length >= 1) {
            if (nums[0] == k) result++;
        }
        if (nums.length >= 2) {
            if (nums[1] == k) result++;
            if (sums[1] == k) result++;
        }
        /// 查看之后的元素
        for (int i = 2; i < nums.length; i++) {
            if (nums[i] == k) result++;
            if (sums[i] == k) result++;
            result += counts[i];
        }
        return result;
    }
}
```

#### 结果1-A

通过

用时14ms

击败99.23%





# 技术原理



### 数据结构

- 数组/列表

  - 子数组

    数组中一个或多个连续的元素组成的序列

  - 子序列

    数组中一个或多个保持相对顺序组成的序列

- 字符串

  字符串应视为数组的一种形式，其操作的时间复杂度与数组相同

  - 子串

    字符串的子数组

- 哈希表

- 红黑树

  提供在 O(logn) 时间复杂度下对一个元素进行查找、插入并排序、删除的操作

  如 TreeMap、TreeSet，其相较 HashMap、HashSet 虽然性能略差，但提供了排序功能

### 算法

- 二分查找 O(logn)

  常与排序共用时为 O(nlogn)

- 动态规划

  将大问题分解为相互重叠的子问题，并存储子问题的解来避免重复计算

  - 状态转移方程

- 贪心

### 技巧

- 空间换时间

  构造更多的数据结构以降低时间复杂度

- 双指针 O(n)

  常与排序共用时为 O(nlogn)

- 滑动窗口 O(n)

  常用于处理数组/列表等线性数据结构的关于局部数据的问题
  
- 前缀和

  前缀和 pre[i] 指的是数组 nums[0] 到 nums[i] 的和，对于数组元素 i+1 到 j 的子数组的和，可以用 pre[j] - pre[i] 来计算