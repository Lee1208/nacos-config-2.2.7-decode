//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.alibaba.cloud.nacos.utils;

import java.lang.Character.UnicodeBlock;

public final class NacosConfigUtils {
    private NacosConfigUtils() {
    }

    public static String selectiveConvertUnicode(String configValue) {
        StringBuilder sb = new StringBuilder();
        char[] chars = configValue.toCharArray();
        char[] var3 = chars;
        int var4 = chars.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            char aChar = var3[var5];
            if (isBaseLetter(aChar)) {
                sb.append(aChar);
            } else {
                sb.append(String.format("\\u%04x", Integer.valueOf(aChar)));
            }
        }

        return sb.toString();
    }

    public static boolean isBaseLetter(char ch) {
        Character.UnicodeBlock ub = UnicodeBlock.of(ch);
        return ub == UnicodeBlock.BASIC_LATIN || Character.isWhitespace(ch);
    }

    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = UnicodeBlock.of(c);
        return ub == UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub == UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == UnicodeBlock.GENERAL_PUNCTUATION || ub == UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS;
    }
}
