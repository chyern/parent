package com.chyern.parent.core.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2023/3/9 16:33
 */
public class ZipUtil {

    public static void compressZip(String compressPath, String outPath) throws IOException {
        File compressFile = new File(compressPath);
        if (!compressFile.exists()) {
            return;
        }

        File outPathFile = new File(outPath);
        if (!outPathFile.exists()) {
            outPathFile.mkdirs();
        }

        String compressFileName = compressFile.getName();
        File outFile = new File(outPath + File.separator + compressFileName + ".zip");

        try (FileOutputStream out = new FileOutputStream(outFile);
             ZipOutputStream zipOutputStream = new ZipOutputStream(out)) {
            compressZip(zipOutputStream, compressFile, StringUtils.EMPTY);
            zipOutputStream.closeEntry();
        } catch (IOException exception) {
            outFile.deleteOnExit();
            throw exception;
        }
    }

    private static void compressZip(ZipOutputStream zipOutput, File file, String suffixPath) throws IOException {
        File[] listFiles = file.listFiles();
        if (listFiles == null) {
            return;
        }

        for (File fi : listFiles) {
            if (fi.isDirectory()) {
                if (StringUtils.EMPTY.equals(suffixPath)) {
                    compressZip(zipOutput, fi, fi.getName());
                } else {
                    compressZip(zipOutput, fi, suffixPath + File.separator + fi.getName());
                }
            } else {
                zip(zipOutput, fi, suffixPath);
            }
        }
    }

    private static void zip(ZipOutputStream zipOutput, File file, String suffixPath) throws IOException {
        ZipEntry zEntry;
        if (StringUtils.EMPTY.equals(suffixPath)) {
            zEntry = new ZipEntry(file.getName());
        } else {
            zEntry = new ZipEntry(suffixPath + File.separator + file.getName());
        }
        zipOutput.putNextEntry(zEntry);

        try (FileInputStream in = new FileInputStream(file);
             BufferedInputStream bis = new BufferedInputStream(in);) {
            byte[] buffer = new byte[1024];
            int read;
            while ((read = bis.read(buffer)) != -1) {
                zipOutput.write(buffer, 0, read);
            }
        }
    }
}
