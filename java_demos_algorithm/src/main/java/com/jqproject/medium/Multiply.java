package com.jqproject.medium;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 姜庆
 * @create 2019-09-26 16:51
 * @desc 给定两个以字符串形式表示的非负整数 num1 和 num2，返回 num1 和 num2 的乘积，它们的乘积也表示为字符串形式
 * num1 和 num2 的长度小于110。
 * num1 和 num2 只包含数字 0-9。
 * num1 和 num2 均不以零开头，除非是数字 0 本身。
 * 不能使用任何标准库的大数类型（比如 BigInteger）或直接将输入转换为整数来处理。

 **/
public class Multiply {

    /**
     * 解析乘法计算
     * @param num1
     * @param num2
     * @return
     */
    public static String multiply(String num1, String num2){

        String result = "0";
        char[] char1 = num1.toCharArray();
        char[] char2 = num2.toCharArray();
        int carry = 0;
        int rem = 0;
         List<String> list = new ArrayList<>();
         for (int i = char1.length-1; i >= 0; i--) {
             StringBuilder sb = new StringBuilder();
             for (int j = 0; j < char1.length-1 -i; j++) {
                 sb.append("0");
             }
             carry = 0;
             for (int j = char2.length-1; j >= 0 ; j--) {
                int int1 = Integer.valueOf(char1[i]+"") * Integer.valueOf(char2[j]+"") + carry;
                sb.append(int1 % 10);
                carry = int1 / 10;
             }
             if(carry>0){
                 sb.append(carry);
             }
             result = addStrings(result,sb.reverse().toString());
        }

        return result;
    }

    /**
     * 2个数进行相加
     * @param result
     * @param num
     * @return
     */
    private static String addStrings(String result, String num) {
        char[] char1 = result.toCharArray();
        char[] char2 = num.toCharArray();
        int carry=0;
        StringBuilder sb = new StringBuilder();
        for (int i = char1.length-1, j = char2.length-1; i>=0||j>=0||carry!=0; i--,j--) {
            int x = i< 0?0:char1[i]-'0';
            int y = j< 0?0:char2[j]-'0';
            int sum = (x+y+carry) % 10;
            sb.append(sum);
            carry = (x+y+carry) / 10;
        }
        return sb.reverse().toString();
    }

    /**
     * 优化下竖式的，使用下标的计算规律
     * @param num1
     * @param num2
     * @return
     */
    public static String multiply2(String num1, String num2){

        char[] char1 = num1.toCharArray();
        char[] char2 = num2.toCharArray();
        List<String> list = new ArrayList<>();
        int[] arr = new int[char1.length + char2.length];
        for (int i = char1.length-1; i >= 0; i--) {
            for (int j = char2.length-1; j >= 0 ; j--) {
                int sum = arr[i+j+1] +  (char1[i]-'0') * (char2[j]-'0') ;
                arr[i+j+1] = sum % 10;
                arr[i+j] += sum / 10;  //加进去之后可能需要进位
            }
        }
        StringBuilder sb = new StringBuilder();
        boolean flag = false;
        for (int i = 0; i < arr.length ; i++) {
            //排除掉多余的0
            if(!flag && arr[i]!=0){
                flag = true;
            }
            if(flag){
                sb.append(arr[i]);
            }
        }
        return sb.length()>0?sb.toString():"0";
    }

    public static void main(String[] args) {
        String result = multiply2("6666", "0");
        System.out.println(result);
    }

}
