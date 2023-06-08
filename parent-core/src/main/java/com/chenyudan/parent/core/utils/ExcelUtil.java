package com.chenyudan.parent.core.utils;

import com.alibaba.excel.EasyExcel;

import java.io.File;
import java.util.List;

/**
 * Description: excel文件
 *
 * @author Chyern
 * @since 2023/6/8 10:07
 */
public class ExcelUtil {

    /**
     * 生成excel文件
     *
     * @param filePath  文件路径
     * @param sheetName 页签名称
     * @param data      数据
     */
    public static <T> File generateExcel(String filePath, String sheetName, List<T> data) {
        EasyExcel.write(filePath, data.get(0).getClass()).sheet(sheetName).doWrite(data);
        return new File(filePath);
    }
}
