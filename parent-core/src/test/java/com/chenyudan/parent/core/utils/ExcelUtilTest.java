package com.chenyudan.parent.core.utils;


import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.NumberFormat;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
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
        for (int i = 0; i < 1; i++) {
            WriteBO writeBO = new WriteBO();
            writeBO.setOrderId(UUID.randomUUID().toString());
            writeBO.setPayment(0.0D);
            writeBO.setCreationTime(new Date());

            writeBOS.add(writeBO);
        }

        String path = "/Users/chenyudan/git/parent/parent-core/src/test/java/com/chenyudan/parent/core/utils/";
        //String path = FileUtils.getTempFilePrefix();
        String name = UUID.randomUUID() + ".xlsx";

        File file = ExcelUtil.generateExcel(path + name, "data", WriteBO.class, writeBOS);

        log.info(file.getPath());

        List<ReadBO> readBOS = ExcelUtil.readExcel(file.getPath(), ReadBO.class);


        ExcelUtil.appendExcel(path + UUID.randomUUID() + ".xlsx", file.getPath(), "data", WriteBO.class, writeBOS);

        boolean delete = file.delete();
    }

    @Data
    public static class WriteBO {
        @ExcelProperty("订单编号")
        private String orderId;

        @ExcelProperty("支付金额")
        @NumberFormat("￥#,###")
        private Double payment;

        @ExcelProperty(value = "创建日期")
        private Date creationTime;
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