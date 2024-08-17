package com.hzhiping.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式匹配
 *
 * @author hzhiping
 * @date 2024/08/17
 */
public class RegexUtil {

    public static void main(String[] args) {
        System.out.println(autoCorrect("//你 好，@世$界！I Love You，帮#我Test一下^这个*对不对。\n" +
                ",中文，hello world,[]好的"));
    }

    /**
     * 利用正则表达式实现自动美化空格的需求
     *
     * @param input 输入内容
     * @return 美化后的内容
     */
    public static String autoCorrect(String input) {
        // 中文字符范围
        String chineseChar = "([\\u4e00-\\u9fa5])";
        // 英文字符范围
        String englishChar = "([0-9a-zA-Z])";
        // 英文符号范围，FIXME：待完善
        String englishSymbol = "([,\\-.?!:'\";(){}\\[\\]])";
        // 中文符号范围，FIXME：待完善
        String chineseSymbol = "([\\u3002\\uff1b\\uff0c\\uff1a\\u201c\\u201d\\uff08\\uff09\\u3001\\uff1f\\u300a\\u300b])";
        // 英文特殊字符范围，FIXME：待完善
        String englishSpacialSymbol = "([/*&%$#@^])";
        // 遇到中文加上英文字符的，加空格
        Pattern pattern1 = Pattern.compile(chineseChar + englishChar);
        Matcher matcher1 = pattern1.matcher(input);
        input = matcher1.replaceAll("$1 $2");
        // 遇到英文加上中文字符的，加空格
        Pattern pattern2 = Pattern.compile(englishChar + chineseChar);
        Matcher matcher2 = pattern2.matcher(input);
        input = matcher2.replaceAll("$1 $2");
        // 遇到中文字符加上英文符号的，不加空格
        Pattern pattern3 = Pattern.compile(chineseChar + englishSymbol);
        Matcher matcher3 = pattern3.matcher(input);
        input = matcher3.replaceAll("$1$2");
        // 遇到英文符号加上中文字符的，加空格
        Pattern pattern4 = Pattern.compile(englishSymbol + chineseChar);
        Matcher matcher4 = pattern4.matcher(input);
        input = matcher4.replaceAll("$1 $2");
        // 遇到中文加英文特殊符号的，加空格
        Pattern pattern5 = Pattern.compile(chineseChar + englishSpacialSymbol);
        Matcher matcher5 = pattern5.matcher(input);
        input = matcher5.replaceAll("$1 $2");
        // 遇到英文特殊符号加中文的，加空格
        Pattern pattern6 = Pattern.compile(englishSpacialSymbol + chineseChar);
        Matcher matcher6 = pattern6.matcher(input);
        input = matcher6.replaceAll("$1 $2");
        // 去掉中文之间的多余空格
        Pattern chinesePatternWithBlank = Pattern.compile("([\\u4e00-\\u9fa5])\\s+([\\u4e00-\\u9fa5])");
        Matcher chineseMatcher = chinesePatternWithBlank.matcher(input);
        return chineseMatcher.replaceAll("$1$2");
    }
}
