package com.shizy.utils.excel.write;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class ExportExcel {

    ExcelWriter excelWriter = null;

    WriteSheet writeSheet = null;

    OutputStream outputStream = null;

    public void init(String fileName, HttpServletResponse response, Class clazz) {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);

        try {
            outputStream = response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        excelWriter = EasyExcel.write(outputStream, clazz).build();
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
