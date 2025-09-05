# Overview

本项目用于记录算法的探究与实现





# 技术原理



### 数据结构

- 数组/列表

- 字符串

  字符串应视为数组的一种形式，其操作的时间复杂度与数组相同

- 哈希表

### 算法

- 二分查找 O(logn)

  常与排序共用时为 O(nlogn)

- 动态规划

- 贪心

### 技巧

- 空间换时间

  构造更多的数据结构以降低时间复杂度

- 双指针 O(n)

  常与排序共用时为 O(nlogn)

- 滑动窗口 O(n)

  常用于处理数组/列表等线性数据结构的关于局部数据的问题





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