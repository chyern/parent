package com.chyern.parent.core.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

/**
 * Description: base64转换类
 *
 * @author Chyern
 * @since 2022/12/29 17:56
 */
public class Base64Util {

    /**
     * 本地图片转base64
     */
    public static String fileToBase64(File file) throws IOException {
        return inputStreamToBase64(new FileInputStream(file));
    }

    /**
     * 网络文件转base64
     */
    public static String urlToBase64(String fileUrl) throws IOException {
        // 创建链接
        URL url = new URL(fileUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5000);
        InputStream inputStream = conn.getInputStream();

        return inputStreamToBase64(inputStream);
    }

    /**
     * 字符串转换base64
     */
    public static String stringToBase64(String str) throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(str.getBytes());
        return inputStreamToBase64(byteArrayInputStream);
    }

    /**
     * base64解密
     */
    public static byte[] base64ToBytes(String base64Str) {
        return Base64.getDecoder().decode(base64Str);
    }

    /**
     * 将输入类转换成Base64
     */
    private static String inputStreamToBase64(InputStream inputStream) throws IOException {
        try (inputStream; ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            // 将内容读取内存中
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }

            byte[] bytes = outputStream.toByteArray();
            return Base64.getEncoder().encodeToString(bytes);
        }
    }
}
