package com.shizy.utils.excel.write;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

@Deprecated
public class ExcelExporter {

    ExcelWriter excelWriter = null;

    WriteSheet writeSheet = null;

    OutputStream outputStream = null;

    public void init(String fileName, HttpServletResponse response, Class clazz) {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName));

        try {
            outputStream = response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        excelWriter = EasyExcel.write(outputStream, clazz)
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())//自动列宽
                .build();

        writeSheet = EasyExcel.writerSheet("data").build();

    }

    public void write(List data) {
        // 第一次写入会创建头
        excelWriter.write(data, writeSheet);
    }

    public void finish() {
        //finish 关闭流
        excelWriter.finish();
    }
}
