package com.jqproject.medium;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author 姜庆
 * @create 2019-09-27 10:20
 * @desc 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
 **/
public class SubStringWithNoDup {

    /**
     * 利用stringbuilder做一个滑动窗口
     * @param s
     * @return
     */
    public static int lengthOfLongestSubstring(String s){

        char[] chars = s.toCharArray();
        StringBuilder sb = new StringBuilder();
        Set<String> set = new HashSet<>();
        for (int i = 0; i < chars.length; i++) {
            String str = chars[i] + "";
            int index = sb.indexOf(str);
            if(index >= 0){
                set.add(sb.toString());
                sb.delete(0,index+1);
            }
            sb.append(str);
        }
        set.add(sb.toString());

        int maxLength = 0;
        for (String str : set) {
            if(str.length()>maxLength){
                maxLength = str.length();
            }
        }
        return maxLength;
//        return list.stream().mapToInt(a->a.length()).max().getAsInt();
    }

    /**
     * 利用HashMap做一个滑动窗口
     * key存储字符，value存储字符在字符串中的下标位置+1(只存储最大的值)
     * @param s
     * @return
     */
    public static int lengthOfLongestSubstring2(String s){
        int maxLength = 0;
        char[] chars = s.toCharArray();
        Map<String,Integer> map = new HashMap<>();
        for (int i = 0 ,j = 0; i < chars.length; i++) {
            String str = chars[i] + "";
            if(map.containsKey(str)){
                //每出现一次重复，就要记录下重复点的下标
                j = Math.max(map.get(str),i);
            }
            //后续的字符重新计算与重复点的距离
            maxLength = Math.max(maxLength,i-j+1);
            map.put(str,i+1);
        }
        return maxLength;
    }

    /**
     * 假设字符集，假设都是ASCII字符
     * @param s
     * @return
     */
    public static int lengthOfLongestSubstring3(String s){
        int maxLength = 0;
        char[] chars = s.toCharArray();
        int[] index = new int[128];
        for (int i = 0 ,j = 0; i < chars.length; i++) {
            j = Math.max(index[chars[i]], j);
            maxLength = Math.max(maxLength,i-j+1);
            index[chars[i]] = i+1;
        }
        return maxLength;
    }




    public static void main(String[] args) {

        int result = lengthOfLongestSubstring2("abcccc");
        System.out.println(result);
    }

}
