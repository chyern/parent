package com.chenyudan.parent.core.utils;


import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.NumberFormat;
import com.alibaba.excel.converters.localdatetime.LocalDateTimeDateConverter;
import com.alibaba.excel.util.FileUtils;
import lombok.Data;

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
public class ExcelUtilTest {

    public static void main(String[] args) {
        List<Demo> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Demo demo = new Demo();
            demo.setOrderId(UUID.randomUUID().toString());
            demo.setPayment(0.0D);
            demo.setCreationTime(LocalDateTime.now());

            list.add(demo);
        }

        String path = FileUtils.getTempFilePrefix() + UUID.randomUUID() + ".xls";
        File file = ExcelUtil.generateExcel(path, "data", list);
        file.delete();
    }

    @Data
    private static class Demo{
        @ExcelProperty("订单编号")
        private String orderId; // 订单编号

        @ExcelProperty("支付金额")
        @NumberFormat("￥#,###")
        private Double payment; // 支付金额

        @ExcelProperty(value = "创建日期",converter = LocalDateTimeDateConverter.class)
        private LocalDateTime creationTime; // 创建时间
    }
}