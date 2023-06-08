package com.chenyudan.parent.core.utils;


import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.NumberFormat;
import com.alibaba.excel.converters.localdatetime.LocalDateTimeDateConverter;
import com.alibaba.excel.util.FileUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2023/6/8 12:34
 */
@Slf4j
public class ExcelUtilTest {

    public static void main(String[] args) {
        List<WriteBO> writeBOS = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            WriteBO writeBO = new WriteBO();
            writeBO.setOrderId(UUID.randomUUID().toString());
            writeBO.setPayment(0.0D);
            writeBO.setCreationTime(LocalDateTime.now());

            writeBOS.add(writeBO);
        }

        String path = FileUtils.getTempFilePrefix() + UUID.randomUUID() + ".xls";
        File file = ExcelUtil.generateExcel(path, "data", writeBOS);

        log.info(file.getPath());

        List<ReadBO> readBOS = ExcelUtil.readExcel(path, ReadBO.class);

        file.delete();
    }

    @Data
    public static class WriteBO {
        @ExcelProperty("订单编号")
        private String orderId;

        @ExcelProperty("支付金额")
        @NumberFormat("￥#,###")
        private Double payment;

        @ExcelProperty(value = "创建日期", converter = LocalDateTimeDateConverter.class)
        private LocalDateTime creationTime;
    }

    @Data
    public static class ReadBO {
        @ExcelProperty(index = 0)
        private String orderId;

        @ExcelProperty(index = 1)
        private String payment;

        @ExcelProperty(index = 2)
        private String creationTime;
    }
}