package com.hzhiping.util;

import java.util.ArrayList;
import java.util.List;
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
        String text = "text你好，我是你die，\n" + "    hello+Test=helloTest";
        System.out.println(autoCorrect(text));
    }

    /**
     * 利用正则表达式实现自动美化空格的需求
     *
     * @param input 输入内容
     * @return 美化后的内容
     */
    public static String autoCorrect(String input) {
        // 1、中文字符范围
        String zhChar = "([\\u4e00-\\u9fa5])";
        // 2、字符范围
        String enChar = "([a-zA-Z])";
        // 3、数字范围
        String numberChar = "([0-9])";
        // 4、英文符号范围
        String enSymbol = "([.,;:!?])";
        // 5、英文特殊符号
        String enSpacialSymbol = "([_$@^~`#])";
        // 6、英文左括号正则
        String enLeftSymbol = "([({\\[<])";
        // 7、英文右括号正则
        String enRightSymbol = "([)}\\]>])";
        // 8、英文引号，遇到任何字符都不需要加空格
        String enQuoteSymbol = "(['\"])";
        // 9、中文引号和括号，遇到任何都不需要加空格
        String zhQuoteSymbol = "([“”‘’【】（）、《》])";
        // 10、算术逻辑符号正则表达式，遇到任何都要加空格
        String calcSymbol = "([+\\-%*/=|&])";
        // 中间加空格的正则表达式，形如：“$1 $2”
        List<String> midBlankRegexList = new ArrayList<>();
        midBlankRegexList.add(zhChar + enChar);
        midBlankRegexList.add(enChar + zhChar);
        midBlankRegexList.add(zhChar + numberChar);
        midBlankRegexList.add(numberChar + zhChar);
        midBlankRegexList.add(zhChar + enSpacialSymbol);
        midBlankRegexList.add(enSpacialSymbol + zhChar);
        midBlankRegexList.add(zhChar + enLeftSymbol);
        midBlankRegexList.add(enSymbol + zhChar);
        midBlankRegexList.add(zhChar + calcSymbol);
        midBlankRegexList.add(calcSymbol + zhChar);
        midBlankRegexList.add(calcSymbol + enChar);
        midBlankRegexList.add(numberChar + calcSymbol);
        midBlankRegexList.add(calcSymbol + numberChar);
        midBlankRegexList.add(enRightSymbol + zhChar);
        // 中间和末尾加空格的正则表达式，形如：“$1 $2 ”
        List<String> midBlankAndTailBlankRegexList = new ArrayList<>();
        midBlankAndTailBlankRegexList.add(enChar + calcSymbol);
        for (String midRegex : midBlankRegexList) {
            Pattern pattern = Pattern.compile(midRegex);
            Matcher matcher = pattern.matcher(input);
            while (matcher.find()) {
                input = matcher.replaceAll("$1 $2");
            }
        }
        for (String midBlankAndTailBlankRegex : midBlankAndTailBlankRegexList) {
            Pattern pattern = Pattern.compile(midBlankAndTailBlankRegex);
            Matcher matcher = pattern.matcher(input);
            while (matcher.find()) {
                input = matcher.replaceAll("$1 $2 ");
            }
        }
        // 连续两个空格替换成一个空格【除了开头】
        String[] split = input.split("\n");
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < split.length; i++) {
            String begin = extractLeadingSpaces(split[i]);
            String subString = split[i].substring(begin.length());
            String replacedStr = begin + subString.replaceAll("\\s{2}", " ");
            result.append(replacedStr);
            if (i != split.length - 1) {
                result.append("\n");
            }
        }
        input = result.toString();
        // 去掉中文之间的多余空格
        Pattern chinesePatternWithBlank = Pattern.compile("([\\u4e00-\\u9fa5])\\s+([\\u4e00-\\u9fa5])");
        Matcher chineseMatcher = chinesePatternWithBlank.matcher(input);
        return chineseMatcher.replaceAll("$1$2");
    }

    public static String extractLeadingSpaces(String input) {
        // 使用正则表达式提取开头的空格
        // ^\\s* 表示从字符串开始位置匹配零个或多个空白字符
        Matcher matcher = Pattern.compile("^\\s*").matcher(input);
        if (matcher.find()) {
            return matcher.group();
        }
        return "";
    }
}
