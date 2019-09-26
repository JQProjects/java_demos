package com.jqproject.medium;

import java.util.Arrays;

/**
 * @author 姜庆
 * @create 2019-09-26 8:51
 * @desc 给定一个包括 n 个整数的数组 nums 和 一个目标值 target。找出 nums 中的三个整数，使得它们的和与 target 最接近。返回这三个数的和。假定每组输入只存在唯一答案
 **/
public class ThreeSumClosest {

    /**
     * 暴力解法
     * @param nums
     * @param target
     * @return
     */
    public static int search01(int[] nums, int target) {
        int result = 0;
        boolean init = false;
        int len = nums.length;
        for (int i = 0; i < len-2; i++) {
            for (int j = i+1; j < len -1 ; j++) {
                for (int k = j+1; k < len; k++) {
                    int sum = nums[i] + nums[j] + nums[k];
                    if(!init){
                        result = sum;
                        init = true;
                        continue;
                    }
                    int abs = Math.abs(sum - target);
                    int last_abs = Math.abs(result - target);
                    if( abs < last_abs ){
                        result = sum;
                    }
                }
            }
        }
        return result;
    }

    /**
     * 双指针优化发
     * @param nums
     * @param target
     * @return
     */
    public static int search02(int[] nums, int target) {
        //先排序
        Arrays.sort(nums);
        int result = Integer.MAX_VALUE;
        int min = Integer.MAX_VALUE;
        int left;
        int right;
        int len = nums.length;

        for (int i = 0; i < len-2; i++) {
            left = i+1;
            right = len -1;
            //最小值 [0,1,2]->[0,2,3]->[0,3,4]
            int sumMin = nums[i] + nums[left] + nums[left+1];
            //最大值
            int sumMax = nums[i] + nums[right] + nums[right-1];
            //若最小值都最大，则直接输出
            if(sumMin > target){
                int abs = Math.abs(sumMin - target);
                if(abs < min){
                    min = abs;
                    result = sumMin;
                }
            //若最大值都最小，则直接输出最大值
            }else if(sumMax < target){
                int abs = Math.abs(sumMax - target);
                if(abs < min){
                    min = abs;
                    result = sumMax;
                }
            }else{
                //假设是10个数据,[0,1,9]->[1,2,9]
                while (left < right){
                   int sum = nums[left] + nums[right] + nums[i];
                   if(sum < target){
                       left++;
                   }else if(sum > target){
                       right--;
                   }else {
                       return sum;
                   }
                    if(Math.abs(sum-target) < min){
                       result = sum;
                       min = Math.abs(sum-target);
                   }
                }
            }

        }
        return result;
    }



    public static void main(String[] args) {
        int[] arr = {-1,2,1,-4};
        int result = search02(arr,1);
        System.out.println(result);
    }
}
