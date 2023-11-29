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
 * @author chenyu
 * @since 2023/6/8 12:34
 */
@Slf4j
public class ExcelUtilTest {

    public static void main(String[] args) {
        File generate = generate();
        List<ReadBO> readBOS = ExcelUtil.readExcel(generate.getPath(), ReadBO.class);
        File append = append(generate);

        //generate.delete();
        //append.delete();
    }

    private static File generate() {
        List<WriteBO> writeBOS = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            WriteBO writeBO = new WriteBO();
            writeBO.setOrderId(UUID.randomUUID().toString());
            writeBO.setPayment(0.0D);
            writeBO.setCreationTime(new Date());

            writeBOS.add(writeBO);
        }

        String path = System.getProperty("user.dir") + File.separator + "excelFile" + File.separator;
        String name = UUID.randomUUID() + ".xlsx";

        return ExcelUtil.generateExcel(path + name, "data", WriteBO.class, writeBOS);
    }

    private static File append(File file) {
        List<WriteBO> writeBOS = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            WriteBO writeBO = new WriteBO();
            writeBO.setOrderId(UUID.randomUUID().toString());
            writeBO.setPayment(0.0D);
            writeBO.setCreationTime(new Date());

            writeBOS.add(writeBO);
        }

        String path = System.getProperty("user.dir") + File.separator + "excelFile" + File.separator;
        String name = UUID.randomUUID() + ".xlsx";

        return ExcelUtil.appendExcel(path + name, file.getPath(), "data", WriteBO.class, writeBOS);
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