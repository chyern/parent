package com.chenyudan.parent.core.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.NumberFormat;
import com.alibaba.excel.converters.localdatetime.LocalDateTimeDateConverter;
import lombok.Data;

import java.io.File;
import java.time.LocalDateTime;
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

    @Data
    private class Demo{
        @ExcelProperty("订单编号")
        private String orderId; // 订单编号

        @ExcelProperty("支付金额")
        @NumberFormat("￥#,###")
        private Double payment; // 支付金额

        @ExcelProperty(value = "创建日期",converter = LocalDateTimeDateConverter.class)
        private LocalDateTime creationTime; // 创建时间
    }
}
