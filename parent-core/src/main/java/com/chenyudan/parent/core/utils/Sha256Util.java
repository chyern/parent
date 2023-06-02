package com.chenyudan.parent.core.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * Description: sha256工具类
 *
 * @author Chyern
 * @since 2021/4/12
 */
public class Sha256Util {

    /**
     * String 转256
     *
     * @param string
     */
    public static String getSHA256(String string) throws Exception {
        MessageDigest messageDigest;
        messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(string.getBytes(StandardCharsets.UTF_8));
        return byte2Hex(messageDigest.digest());
    }

    private static String byte2Hex(byte[] bytes) {
        StringBuilder stringBuffer = new StringBuilder();
        String temp;
        for (byte aByte : bytes) {
            temp = Integer.toHexString(aByte & 0xFF);
            if (temp.length() == 1) {
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }
}
