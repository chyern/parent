package com.chyern.core.utils;


import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
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

}
