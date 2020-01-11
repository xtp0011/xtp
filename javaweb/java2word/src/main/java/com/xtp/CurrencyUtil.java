package com.xtp;

import org.junit.Assert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CurrencyUtil {
    /**
     * 转换为中国人民币大写字符串,精确到分
     * @param money 传入小写数字字符串
     * @return
     * @throws Exception
     */
    public static String toChinaUpper(String money) throws Exception {
        boolean lessZero = false;
        if(money.startsWith("-")) {
            money = money.substring(1);
            lessZero = true;
        }

        if (!money.matches("^[0-9]*$|^0+\\.[0-9]+$|^[1-9]+[0-9]*$|^[1-9]+[0-9]*.[0-9]+$")) {
            throw new Exception("钱数格式错误！");
        }
        String[] part = money.split("\\.");
        String integerData = part[0];
        String decimalData = part.length > 1 ? part[1] : "";
        //替换前置0
        if(integerData.matches("^0+$"))
        {
            integerData = "0";
        }else if(integerData.matches("^0+(\\d+)$")){
            integerData = integerData.replaceAll("^0+(\\d+)$", "$1");
        }

        StringBuffer integer = new StringBuffer();
        for (int i = 0; i < integerData.length(); i++) {
            char perchar = integerData.charAt(i);
            integer.append(upperNumber(perchar));
            integer.append(upperNumber(integerData.length() - i - 1));
        }
        StringBuffer decimal = new StringBuffer();
        if (part.length > 1 && !"00".equals(decimalData)) {
            int length = decimalData.length() >= 2 ? 2 : decimalData.length();
            for (int i = 0; i < length; i++) {
                char perchar = decimalData.charAt(i);
                decimal.append(upperNumber(perchar));
                if (i == 0)
                    decimal.append('角');
                if (i == 1)
                    decimal.append('分');
            }
        }
        String result = integer.toString() + decimal.toString();
        result = dispose(result);
        if(lessZero && !"零圆整".equals(result)) {
            result = "负" + result;
        }
        return result;
    }

    private static char upperNumber(char number) {
        switch (number) {
            case '0':
                return '零';
            case '1':
                return '壹';
            case '2':
                return '贰';
            case '3':
                return '叁';
            case '4':
                return '肆';
            case '5':
                return '伍';
            case '6':
                return '陆';
            case '7':
                return '柒';
            case '8':
                return '捌';
            case '9':
                return '玖';
        }
        return '0';
    }

    private static char upperNumber(int index) {
        int realIndex =  index  % 9;
        if(index > 8) {//亿过后进入回归,之后是拾,佰...
            realIndex =  (index - 9) % 8;
            realIndex = realIndex + 1;
        }
        switch (realIndex) {
            case 0:
                return '圆';
            case 1:
                return '拾';
            case 2:
                return '佰';
            case 3:
                return '仟';
            case 4:
                return '万';
            case 5:
                return '拾';
            case 6:
                return '佰';
            case 7:
                return '仟';
            case 8:
                return '亿';
        }
        return '0';
    }

    private static String dispose(String result) {
        result = result.replaceAll("0", "");//处理
        result = result.replaceAll("零仟零佰零拾|零仟零佰|零佰零拾|零仟|零佰|零拾", "零");
        result = result.replaceAll("零+", "零").replace("零亿", "亿");
        result = result.matches("^.*亿零万[^零]仟.*$") ? result.replace("零万", "零") : result.replace("零万", "万");
        result = result.replace("亿万", "亿");
        //处理小数
        result = result.replace("零角", "零").replace("零分", "");
        result = result.replaceAll("(^[零圆]*)(.+$)", "$2");
        result = result.replaceAll("(^.*)([零]+圆)(.+$)", "$1圆零$3");

        result =result.replaceAll("[零]+","零");

        //处理整数单位
        result = result.replaceAll("圆零角零分|圆零角$|圆$|^零$|圆零$|零圆$", "圆整");
        result = result.replaceAll("^圆整$", "零圆整");


        return result;
    }


    public static void main(String[] args) throws Exception {

        String str = "Hello,World! in Java.";
        Pattern pattern = Pattern.compile("W(or)(ld!)");
        Matcher matcher = pattern.matcher(str);
        while(matcher.find()){
            System.out.println("Group 0:"+matcher.group(0));//得到第0组——整个匹配
            System.out.println("Group 1:"+matcher.group(1));//得到第一组匹配——与(or)匹配的
            System.out.println("Group 2:"+matcher.group(2));//得到第二组匹配——与(ld!)匹配的，组也就是子表达式
            System.out.println("Start 0:"+matcher.start(0)+" End 0:"+matcher.end(0));//总匹配的索引
            System.out.println("Start 1:"+matcher.start(1)+" End 1:"+matcher.end(1));//第一组匹配的索引
            System.out.println("Start 2:"+matcher.start(2)+" End 2:"+matcher.end(2));//第二组匹配的索引
            System.out.println(str.substring(matcher.start(0),matcher.end(1)));//从总匹配开始索引到第1组匹配的结束索引之间子串——Wor
        }


      //  System.out.println(toChinaUpper("500000000000"));

       /* Assert.assertEquals(CurrencyUtil.toChinaUpper("0"),"零圆整");
        Assert.assertEquals(CurrencyUtil.toChinaUpper("000000000000"),"零圆整");
        Assert.assertEquals(CurrencyUtil.toChinaUpper("0.00"),"零圆整");
        Assert.assertEquals(CurrencyUtil.toChinaUpper("0.03"),"叁分");
        Assert.assertEquals(CurrencyUtil.toChinaUpper("0.43"),"肆角叁分");
        Assert.assertEquals(CurrencyUtil.toChinaUpper("0000.43"),"肆角叁分");
        Assert.assertEquals(CurrencyUtil.toChinaUpper("10000.43"),"壹万圆零肆角叁分");
        Assert.assertEquals(CurrencyUtil.toChinaUpper("000000000000.000"),"零圆整");
        Assert.assertEquals(CurrencyUtil.toChinaUpper("421.0"),"肆佰贰拾壹圆整");
        Assert.assertEquals(CurrencyUtil.toChinaUpper("421.0000"),"肆佰贰拾壹圆整");
        Assert.assertEquals(CurrencyUtil.toChinaUpper("215224635421.00"),"贰仟壹佰伍拾贰亿贰仟肆佰陆拾叁万伍仟肆佰贰拾壹圆整");
        Assert.assertEquals(CurrencyUtil.toChinaUpper("215224635421.53"),"贰仟壹佰伍拾贰亿贰仟肆佰陆拾叁万伍仟肆佰贰拾壹圆伍角叁分");
        Assert.assertEquals(CurrencyUtil.toChinaUpper("215224635421.03"),     "贰仟壹佰伍拾贰亿贰仟肆佰陆拾叁万伍仟肆佰贰拾壹圆零叁分");
        System.out.println(CurrencyUtil.toChinaUpper("635400.03"));
        Assert.assertEquals(CurrencyUtil.toChinaUpper("2463215224635400.03"),"贰仟肆佰陆拾叁万贰仟壹佰伍拾贰亿贰仟肆佰陆拾叁万伍仟肆佰圆零叁分");
        Assert.assertEquals(CurrencyUtil.toChinaUpper("1"),"壹圆整");
        Assert.assertEquals(CurrencyUtil.toChinaUpper("10"),"壹拾圆整");
        Assert.assertEquals(CurrencyUtil.toChinaUpper("20.7"),"贰拾圆零柒角");
        Assert.assertEquals(CurrencyUtil.toChinaUpper("10.70"),"壹拾圆零柒角");
        Assert.assertEquals(CurrencyUtil.toChinaUpper("18.5"),"壹拾捌圆伍角");
        Assert.assertEquals(CurrencyUtil.toChinaUpper("200.5"),"贰佰圆零伍角");
        Assert.assertEquals(CurrencyUtil.toChinaUpper("2000"),"贰仟圆整");
        Assert.assertEquals(CurrencyUtil.toChinaUpper("50000"),"伍万圆整");
        Assert.assertEquals(CurrencyUtil.toChinaUpper("500000"),"伍拾万圆整");
        Assert.assertEquals(CurrencyUtil.toChinaUpper("5000000"),"伍佰万圆整");
        Assert.assertEquals(CurrencyUtil.toChinaUpper("50000000"),"伍仟万圆整");
        Assert.assertEquals(CurrencyUtil.toChinaUpper("500000000"),"伍亿圆整");
        Assert.assertEquals(CurrencyUtil.toChinaUpper("5000000000"),"伍拾亿圆整");
        Assert.assertEquals(CurrencyUtil.toChinaUpper("5000000001"),"伍拾亿零壹圆整");
        Assert.assertEquals(CurrencyUtil.toChinaUpper("5000000021"),"伍拾亿零贰拾壹圆整");
        Assert.assertEquals(CurrencyUtil.toChinaUpper("5000000421"),"伍拾亿零肆佰贰拾壹圆整");
        Assert.assertEquals(CurrencyUtil.toChinaUpper("5000005421"),"伍拾亿零伍仟肆佰贰拾壹圆整");
        Assert.assertEquals(CurrencyUtil.toChinaUpper("5000035421"),"伍拾亿零叁万伍仟肆佰贰拾壹圆整");
        Assert.assertEquals(CurrencyUtil.toChinaUpper("5000635421"),"伍拾亿零陆拾叁万伍仟肆佰贰拾壹圆整");
        Assert.assertEquals(CurrencyUtil.toChinaUpper("5004635421"),"伍拾亿零肆佰陆拾叁万伍仟肆佰贰拾壹圆整");
        Assert.assertEquals(CurrencyUtil.toChinaUpper("5024635421"),"伍拾亿贰仟肆佰陆拾叁万伍仟肆佰贰拾壹圆整");
        Assert.assertEquals(CurrencyUtil.toChinaUpper("5224635421"),"伍拾贰亿贰仟肆佰陆拾叁万伍仟肆佰贰拾壹圆整");
        Assert.assertEquals(CurrencyUtil.toChinaUpper("15224635421"),"壹佰伍拾贰亿贰仟肆佰陆拾叁万伍仟肆佰贰拾壹圆整");
        Assert.assertEquals(CurrencyUtil.toChinaUpper("215224635421"),"贰仟壹佰伍拾贰亿贰仟肆佰陆拾叁万伍仟肆佰贰拾壹圆整");
        Assert.assertEquals(CurrencyUtil.toChinaUpper("500021"),"伍拾万零贰拾壹圆整");
        Assert.assertEquals(CurrencyUtil.toChinaUpper("5000821"),"伍佰万零捌佰贰拾壹圆整");
        Assert.assertEquals(CurrencyUtil.toChinaUpper("5050006501"),"伍拾亿伍仟万陆仟伍佰零壹圆整");
        Assert.assertEquals(CurrencyUtil.toChinaUpper("550300001"),"伍亿伍仟零叁拾万零壹圆整");
*/
    }
}
