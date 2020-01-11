package com.xtp;

import java.util.HashMap;

public class TestOne {
    //代码片段(3)[全屏查看所有代码]1. [代码]Java的默认实现方法

    // public int hashCode() { int h = hash; if (h == 0) { int off = offset; char val[] = value; int len = count; for (int i = 0; i < len; i++) { h = 31*h + val[off++]; } hash = h; } return h; }

    ///2. [代码]测试代码 (TestHashCode.java)
    static HashMap map = new HashMap();

    private static char startChar = 'A';
    private static char endChar = 'z';
    private static int offset = endChar - startChar + 1;
    private static int dup = 0;

    public static void main(String[] args) {
       /* int len = 3;
        char[] chars = new char[len];
        tryBit(chars, len);
        System.out.println((int) Math.pow(offset, len) + ":" + dup);*/

        String str  = "轷龚";
        System.out.println("轷龚:"+ str.hashCode());
        String str1  = "轸齻";
        System.out.println("轸齻:"+ str1.hashCode());
        String str2  = "轹齜";
        System.out.println("轹齜:"+ str2.hashCode());
        String str3  = "轺鼽";
        System.out.println("轺鼽:"+ str3.hashCode());
        String str4  = "轷";
        System.out.println("轷:"+ str4.hashCode());
        String str5  = "轷";
        System.out.println("轷:"+ str5.hashCode());
    }

    private static void tryBit(char[] chars, int i) {
        for (char j = startChar; j <= endChar; j++) {
            chars[i - 1] = j;
            if (i > 1) tryBit(chars, i - 1);
            else test(chars);
        }
    }

    private static void test(char[] chars) {
       /* String str1 = new String(chars).replaceAll("[^a-zA-Z_]", "").toUpperCase();// 195112:0 //
        String str2 = new String(chars).toLowerCase();//195112:6612
        String str3 = new String(chars).replaceAll("[^a-zA-Z_]", "");// 195112:122500*/
        String str = new String(chars);//195112:138510
        int hash = str.hashCode();
        if (map.containsKey(hash)) {
            //System.out.println("hashcode:"+hash);
            String s = (String) map.get(hash);
            if (!s.equals(str)) {
                dup++;
               // System.out.println(s + ":" + str);
            }
        } else {
            map.put(hash, str);
            System.out.println(str);
        }


// 3. [代码]结论在A-z范围内有特殊字符，从结果看，仅仅3位长度的字符串：不处理： 138510次重复去掉字母意外字符： 122500次重复所有字符转小写：6612次重复(少了很多）去掉字母意外字符，并且转小写：没有重复！4位字符串也没见重复 不难看出： 1. 缺省实现为英文字母优化 2. 字母大小写可能导致重复 可能：长字符串可能hashcode重复中文字符串和特殊字符可能hashcode重复
    }
}