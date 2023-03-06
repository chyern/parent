package com.chyern.core.utils;


import com.chyern.spicore.exception.BaseException;
import com.chyern.spicore.exception.enums.CoreExceptionEnum;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2022/7/29 19:40
 */
public class FileUtil {


    /**
     * 创建文件
     * 文件存在时报错
     *
     * @param path 文件路径
     */
    public static File createFile(String path) throws BaseException, IOException {
        File file = new File(path);
        if (file.exists()) {
            throw new BaseException(CoreExceptionEnum.FILE_EXIST);
        }
        createParentFile(file);
        file.createNewFile();
        return file;
    }

    /**
     * 创建文件
     * 文件存在时删除
     *
     * @param path 文件路径
     */
    public static File createFileMandatory(String path) throws BaseException, IOException {
        File file = new File(path);
        createParentFile(file);
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        return file;
    }

    /**
     * 逐行读取文件内容
     *
     * @param file 文件
     */
    public static List<String> readFileByLine(File file) throws IOException {
        List<String> result = new ArrayList<>();

        try (FileReader fileReader = new FileReader(file);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String line = bufferedReader.readLine();
            while (line != null) {
                result.add(line);
                line = bufferedReader.readLine();
            }
        }

        return result;
    }

    /**
     * 写入文件
     *
     * @param path    文件路径
     * @param content 写入内容
     */
    public static void writeFile(String path, String content) throws IOException {
        File file = createFileMandatory(path);

        try (FileOutputStream fileOutputStream = new FileOutputStream(file);
             OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
             BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter)) {
            bufferedWriter.write(content);
            bufferedWriter.flush();
        }
    }

    /**
     * 换行写入文件
     *
     * @param path     文件路径
     * @param contents 写入内容
     */
    public static void writeFile(String path, List<String> contents) throws IOException {
        String content = StringUtils.join(contents, System.lineSeparator());
        writeFile(path, content);
    }

    /**
     * 多文件合并
     *
     * @param outputFile 待写入文件
     * @param files      写入文件
     */
    public static void mergeFile(File outputFile, List<File> files) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(outputFile)) {
            for (File file : files) {
                if (!file.exists() || file.isDirectory()) {
                    continue;
                }
                try (FileInputStream fileInputStream = new FileInputStream(file)) {
                    byte b[] = new byte[10240];
                    int read = fileInputStream.read(b);
                    while (read > 0) {
                        fileOutputStream.write(b, 0, read);
                        fileOutputStream.flush();
                        read = fileInputStream.read(b);
                    }
                }
                fileOutputStream.write(System.lineSeparator().getBytes());
                fileOutputStream.flush();
            }
        }
    }

    /**
     * 递归查询文件
     *
     * @param patch 文件路径
     */
    public static List<File> listFile(String patch) {
        List<File> result = new ArrayList<>();

        listFile(result, new File(patch));

        return result;
    }

    private static void listFile(List<File> result, File file) {
        if (!file.exists()) {
            return;
        }

        if (file.isFile()) {
            result.add(file);
        } else if (file.isDirectory()) {
            File[] files = file.listFiles();

            if (files != null) {
                for (File subFile : files) {
                    listFile(result, subFile);
                }
            }
        }
    }

    /**
     * 生成父文件路径
     *
     * @param file
     */
    private static void createParentFile(File file) {
        if (file == null) {
            return;
        }
        String parent = file.getParent();
        File parentFile = new File(parent);
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
    }

}
