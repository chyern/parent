package com.chyern.core.utils;


import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2022/7/29 19:40
 */
public class FileUtil {

    public static List<String> readFileByLine(File file) throws Exception {
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try (FileReader fileReader = new FileReader(file); BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            List<String> list = new ArrayList<>();
            String line = bufferedReader.readLine();
            while (line != null) {
                list.add(line);
                line = bufferedReader.readLine();
            }
            return list;
        }
    }

    public static void writeFile(String url, String content) throws Exception {
        File file = new File(url);
        if (file.exists()) {
            throw new FileAlreadyExistsException(file.getPath());
        }
        file.createNewFile();
        try (FileOutputStream fileOutputStream = new FileOutputStream(file); OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream); BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter)) {
            bufferedWriter.write(content);
            bufferedWriter.flush();
        }
    }

    public static void writeFile(String url, List<String> lines) throws Exception {
        String join = StringUtils.join(lines, System.lineSeparator());
        writeFile(url, join);
    }

    public static File mergeFile(String outFilePath, LinkedList<File> files) throws Exception {
        File outputFile = new File(outFilePath);

        File outputFileParentFolder = new File(outputFile.getParent());
        if (!outputFileParentFolder.exists()) {
            outputFileParentFolder.mkdirs();
        }
        if (outputFile.exists()) {
            outputFile.delete();
        }

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
            }
            fileOutputStream.write(System.lineSeparator().getBytes());
            fileOutputStream.flush();
        }

        return outputFile;
    }

}
