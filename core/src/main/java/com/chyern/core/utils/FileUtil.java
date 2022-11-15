package com.chyern.core.utils;


import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2022/7/29 19:40
 */
public class FileUtil {

    public static File createFile(String path) throws Exception {
        File file = new File(path);

        File parentFile = new File(file.getParent());
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }

        if (file.exists()) {
            file.delete();
        }

        file.createNewFile();
        return file;
    }

    public static List<String> readFileByLine(File file) throws Exception {
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

    public static void writeFile(String path, String content) throws Exception {
        File file = createFile(path);

        try (FileOutputStream fileOutputStream = new FileOutputStream(file);
             OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
             BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter)) {
            bufferedWriter.write(content);
            bufferedWriter.flush();
        }
    }

    public static void writeFile(String path, List<String> contents) throws Exception {
        String join = StringUtils.join(contents, System.lineSeparator());
        writeFile(path, join);
    }

    public static List<File> listFile(String patch) {
        List<File> result = new ArrayList<>();
        listFile(result, new File(patch));
        return result;
    }

    public static File mergeFile(String outFilePath, List<File> files) throws Exception {
        File outputFile = createFile(outFilePath);

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

        return outputFile;
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

}
