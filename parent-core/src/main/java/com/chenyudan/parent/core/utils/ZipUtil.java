package com.chenyudan.parent.core.utils;

import com.chenyudan.parent.api.exception.enums.BaseExceptionEnum;
import com.chenyudan.parent.core.constant.Constant;

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
 * @author chenyu
 * @since 2023/3/9 16:33
 */
public class ZipUtil {

    /**
     * 压缩目录为zip
     *
     * @param compressFile 被压缩的目录
     * @param outPath      输出目录
     */
    public static void compressZip(File compressFile, String outPath) throws IOException {
        checkCompressFile(compressFile);

        String compressFileName = compressFile.getName();
        File outFile = new File(outPath + File.separator + compressFileName + ".zip");
        FileUtil.createFileMandatory(outFile.getPath());

        try (FileOutputStream fileOutputStream = new FileOutputStream(outFile);
             ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream)) {
            compressZip(zipOutputStream, compressFile, Constant.EMPTY);
            zipOutputStream.closeEntry();
        } catch (IOException exception) {
            outFile.deleteOnExit();
            throw exception;
        }
    }

    /**
     * 校验被压缩文件
     *
     * @param compressFile 被压缩文件
     */
    private static void checkCompressFile(File compressFile) {
        String path = compressFile.getPath();
        AssertUtil.isTrue(compressFile.exists(), BaseExceptionEnum.FILE_NOT_FIND, path);
        AssertUtil.isTrue(compressFile.isDirectory(), BaseExceptionEnum.FILE_NOT_IS_DIRECTORY, path);
        AssertUtil.isTrue(compressFile.listFiles() != null, BaseExceptionEnum.DIRECTORY_IS_EMPTY);
    }

    /**
     * 压缩目录
     *
     * @param zipOutputStream 压缩文件流
     * @param compressFile    被压缩文件
     * @param suffixPath      压缩文件中的路径
     */
    private static void compressZip(ZipOutputStream zipOutputStream, File compressFile, String suffixPath) throws IOException {
        File[] files = compressFile.listFiles();
        if (files == null) {
            return;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                if (Constant.EMPTY.equals(suffixPath)) {
                    compressZip(zipOutputStream, file, file.getName());
                } else {
                    compressZip(zipOutputStream, file, suffixPath + File.separator + file.getName());
                }
            } else {
                zip(zipOutputStream, file, suffixPath);
            }
        }
    }

    /**
     * 压缩文件
     *
     * @param zipOutputStream 压缩文件流
     * @param compressFile    被压缩文件
     * @param suffixPath      压缩文件中的路径
     */
    private static void zip(ZipOutputStream zipOutputStream, File compressFile, String suffixPath) throws IOException {
        ZipEntry zEntry;
        if (Constant.EMPTY.equals(suffixPath)) {
            zEntry = new ZipEntry(compressFile.getName());
        } else {
            zEntry = new ZipEntry(suffixPath + File.separator + compressFile.getName());
        }
        zipOutputStream.putNextEntry(zEntry);

        try (FileInputStream fileInputStream = new FileInputStream(compressFile);
             BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);) {
            byte[] buffer = new byte[1024];
            int read;
            while ((read = bufferedInputStream.read(buffer)) != -1) {
                zipOutputStream.write(buffer, 0, read);
            }
        }
    }
}
