package com.jqproject.medium;

import java.util.*;

/**
 * @author 姜庆
 * @create 2019-09-25 23:10
 * @desc 给定一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？找出所有满足条件且不重复的三元组。
 * 注意：答案中不可以包含重复的三元组。
 **/
public class ThreeSum {

    /**
     * 较为慢速的查找方式
     * @param nums
     * @return
     */
    public static List<List<Integer>> search01(int[] nums){
        List<List<Integer>> lists = new LinkedList<>();
        boolean spe_found = false;
        for (int i = 0; i < nums.length-2; i++) {
            for (int j = i+1; j < nums.length -1; j++) {
                for (int k = j+1; k < nums.length; k++) {
                    if(nums[i]+nums[j]+nums[k] ==0){
                        List<Integer> integers = new LinkedList<>();
                        integers.add(nums[i]);
                        integers.add(nums[j]);
                        integers.add(nums[k]);
//                        lists.add(integers);
//                        不能有重复的
                        if(lists.size()==0){
                            if(nums[i]==0 && nums[j]==0 && nums[k]==0){
                                spe_found =true;
                            }
                            lists.add(integers);
                            continue;
                        }
                        if(!spe_found && nums[i]==0 && nums[j]==0 && nums[k]==0){
                            lists.add(integers);
                            spe_found = true;
                            continue;
                        }
                        boolean found = false;
                        for (List<Integer> list : lists) {
                            if(list.containsAll(integers)){
//                                System.out.println("发现重复的"+integers);
                                found = true;
                                break;
                            }
                        }
                        if(!found){
                            lists.add(integers);
                        }

                    }
                }
            }
        }
        return lists;
    }

    /**
     * 利用排序后数组特性
     * @param nums
     * @return
     */
    public static List<List<Integer>> search02(int[] nums){
        List<List<Integer>> lists = new ArrayList<>();
        Arrays.sort(nums); // 先排序
        int len = nums.length;
        for (int i = 0; i < len; i++) {
            //由于思路中是第一个数是三个数中最小的数，故要求它必须小于过等于0
            if(nums[i]>0){
                break;
            }
            //若最小的数已经处理过，则不用在次处理，消重
            if(i>0 && nums[i]==nums[i-1]){
                continue;
            }
            //j下标必须大于i下标，且数据也比i下标的大
            int j=i+1;
            //k下标采取倒排下标
            int k=len-1;
            //采用类似二分查找的思路解题
            while (j<k){
                int sum = nums[i] + nums[j] + nums[k];
                if(sum==0){
                    lists.add(Arrays.asList(nums[i],nums[j],nums[k]));
                    //消重
                    while(j<k && nums[j]==nums[j+1]){
                        j++;
                    }
                    //消重
                    while(j<k && nums[k]==nums[k-1]){
                        k--;
                    }
                    j++;
                    k--;
                }else if( sum < 0 ) {
                    j++;
                }else if( sum > 0 ) {
                    k--;
                }
            }
        }
        return lists;
    }


    public static void main(String[] args) {
        int[] arr = {-4,-2,1,-5,-4,-4,4,-2,0,4,0,-2,3,1,-5,0};
        List<List<Integer>> result = search02(arr);
        for (List<Integer> list: result) {
            System.out.println(list);
        }
    }
}
