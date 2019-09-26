package com.jqproject.simple;

import java.util.Arrays;

/**
 * @author 姜庆
 * @create 2019-09-25 22:42
 * @desc 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标，
 * 你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素
 **/
public class TwoSum {

    /**
     * 使用简单的双重循环查询，当然可以还可以做内部缓存避免重复查询
     * @param nums
     * @param target
     * @return
     */
    public static int[] search01(int[] nums, int target) {

        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (target == nums[i] + nums[j]) {
                    return new int[]{i, j};
                }
            }
        }
        throw new IllegalArgumentException("数组中任意两个之无法求和得到" + target);
    }

    public static void main(String[] args) {
        int[] arr = {1, 10, 10, 3, 4, 9, 77, 66};
        int[] result = search01(arr, 20);
        System.out.println(Arrays.toString(result));
        int[] result1 = search01(arr, 7);
        System.out.println(Arrays.toString(result1));
        int[] result2 = search01(arr, 76);
        System.out.println(Arrays.toString(result2));
    }
}
