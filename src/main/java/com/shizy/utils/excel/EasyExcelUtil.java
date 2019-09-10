package com.shizy.utils.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.shizy.entity.user.UserExp;
import com.shizy.utils.excel.read.ReadCallback;
import com.shizy.utils.excel.read.ReadExcel;
import com.shizy.utils.excel.write.ExcelExporter;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 简单数据导入导出的excel工具类
 * <p>
 * 使用阿里的easyexcel包，称比poi包内存更少，速度更快
 * https://github.com/alibaba/easyexcel
 * <p>
 * easyexcel封装了批量读写，但是若有对复杂excel，如具体操作某行某列的情况，还是建议用poi手动处理（easyexcel中已经引入poi的包）。
 */
public class EasyExcelUtil {

    /**
     * read
     *
     * @param inputStream  excel file imputstream
     * @param readCallback 读取到一定条数或者读完一页的回调函数
     * @param callbackSize 读取到一定条数或者读完一页就调用函数的"一定条数"
     * @param head         列名的中文名，英文名相互转换
     */
    public static void read(InputStream inputStream, ReadCallback readCallback, int callbackSize, Class head) {
        new ReadExcel(inputStream, 0, readCallback, callbackSize, head).read();
    }

    /****************************************/

    /**
     * web excel data 导出
     * 一次性将data写入excel并返回到response
     *
     * @param data     数据
     * @param fileName 导出excel的文件名
     * @param response http response
     * @param clazz    实体类。可以加上列名的中英文映射的注解
     */
    public static void export(List data, String fileName, HttpServletResponse response, Class clazz) {
        ExcelExporter exportExcel = new ExcelExporter();
        exportExcel.init(fileName, response, clazz);
        exportExcel.write(data);
        exportExcel.finish();//关闭response流
    }

    /**
     * local excel data 导出
     * 一次性将data写入excel并返回到response
     *
     * @param data     数据
     * @param fileName 导出excel的文件名
     * @param clazz    实体类。可以加上列名的中英文映射的注解
     */
    public static void write(List data, String fileName, Class clazz) {
        ExcelWriter excelWriter = EasyExcel.write(fileName, clazz).build();
        WriteSheet writeSheet = EasyExcel.writerSheet("data").build();
        excelWriter.write(data, writeSheet);
        excelWriter.finish();
    }

    public static void main(String[] args) {
        List list = new ArrayList();
        list.add(new UserExp("1", "2", "3"));
        list.add(new UserExp("1", "2", "3"));
        list.add(new UserExp("1", "2", "3"));
        EasyExcelUtil.write(list, "D:/file/test.xlsx", UserExp.class);
    }

    /**
     * 获得ExportExcel，可以分批写入
     * 用法见EasyExcelUtil.export();
     */
    public static ExcelExporter getExportExcel() {
        return new ExcelExporter();
    }

}






















