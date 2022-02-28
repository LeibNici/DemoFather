package com.leetcode.Learn20220227;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenming
 * @description
 * @create: 2022-02-27
 */
public class twoSumTest {

    public static void main(String[] args) {
        int[] nums = {3, 2, 4};
        int target = 6;
        int[] ints = twoSum(nums, target);
    }

    /**
     * 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 target  的那 两个 整数，并返回它们的数组下标。
     * 输入：nums = [3,2,4], target = 6
     * 输出：[1,2]
     */
    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target - nums[i])) {
                return new int[]{map.get(target - nums[i]), i};
            }
            map.put(nums[i], i);
        }
        throw new IllegalArgumentException("weizhaodao");
    }

}
