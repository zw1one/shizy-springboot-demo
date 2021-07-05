package com.shizy.utils.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * 简单数据导入导出的excel工具类
 * <p>
 * https://github.com/alibaba/easyexcel
 * <p>
 * easyexcel封装了批量读写，但是若有对复杂excel，如具体操作某行某列的情况，还是建议用poi手动处理（easyexcel中已经引入poi的包）。
 */
public class EasyExcelUtil {

    private static final Logger logger = LoggerFactory.getLogger(EasyExcelUtil.class);

    /**************************************导入****************************************/

    /**
     * read
     *
     * @param inputStream  excel file imputstream
     * @param readCallback 读取到一定条数或者读完一页的回调函数
     * @param callbackSize 读取到一定条数或者读完一页就调用函数的"一定条数"
     * @param head         列名的中文名，英文名相互转换
     * @return excel总条数
     */
    public static <T> Integer read(InputStream inputStream, ReadCallback readCallback, int callbackSize, Class<T> head) {
        CallbackAnalysisEventListener callbackAnalysisEventListener = new CallbackAnalysisEventListener<T>(readCallback, callbackSize);
        EasyExcelFactory.read()
                .registerReadListener(callbackAnalysisEventListener)
                .file(inputStream)
                .head(head)
                .headRowNumber(1)
                .autoCloseStream(true)
                .autoTrim(true)
                .excelType(ExcelTypeEnum.XLSX)
                .build()
                .readAll();
        return callbackAnalysisEventListener.getSum();
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    static class CallbackAnalysisEventListener<T> extends AnalysisEventListener {

        private ReadCallback readCallback;//回调
        private int callbackSize = 1000;//读了callbackSize条，调一次callback。读完该页，也会调一次callback

        CallbackAnalysisEventListener(ReadCallback<T> readCallback, int callbackSize) {
            this.readCallback = readCallback;
            this.callbackSize = callbackSize;
        }

        private List<String> titles;//列名
        private List<T> data = new ArrayList();//数据
        private Integer sum = 0;//数据总数

        /**
         * 读完一行
         * 把读到的数据丢到dataList中
         */
        @Override
        public void invoke(Object object, AnalysisContext context) {
            T rowData = (T) object;
            data.add(rowData);

            //读一页的数据时，list达到调用一次callbackSize时，调用一次callback。否则list过大会溢栈
            if (data.size() >= callbackSize) {
                doAfterReadSheel(context);
            }
        }

        /**
         * 读完一页
         */
        @Override
        public void doAfterAllAnalysed(AnalysisContext context) {
            if (data == null || data.size() <= 0) {
                return;
            }
            doAfterReadSheel(context);
        }

        private void doAfterReadSheel(AnalysisContext context) {
            readCallback.doAfterReadSheel(context, data);
            logger.info("-- " + "readed sheet " + context.readSheetHolder().getSheetNo() + " [" + context.readSheetHolder().getSheetName() + "]" + " read size：" + data.size());
            sum += data.size();
            data = new ArrayList();//清空上一次的data
        }
    }

    public interface ReadCallback<T> {
        public void doAfterReadSheel(AnalysisContext context, List<T> data);
    }

    /**************************************导出****************************************/

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
        if (data == null) {
            throw new RuntimeException("export data is null");
        }

        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName));

        ExcelWriter excelWriter = null;
        try (OutputStream outputStream = response.getOutputStream()) {
            excelWriter = EasyExcel.write(outputStream, clazz)
                    .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())//自动列宽
                    .build();
            excelWriter.write(data, EasyExcel.writerSheet("data").build());//第一次写入会创建头
            excelWriter.finish();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (excelWriter != null) {
                excelWriter.finish();//todo 这个加了有时候会报错？
            }
        }
    }

    /**
     * 导出，分批写入。数据量比较大的情况，不能一次将数据写入excel，需要分批
     */
    public static class EasyExcelExporter {

        ExcelWriter excelWriter = null;
        WriteSheet writeSheet = null;
        OutputStream outputStream = null;

        String fileName;
        int exportSum = 0;

        public EasyExcelExporter(String fileName, HttpServletResponse response, Class clazz) {
            this.fileName = fileName;

            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName));

            OutputStream outputStream = null;
            try {
                outputStream = response.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("OutputStream加载失败");
            }

            excelWriter = EasyExcel.write(outputStream, clazz)
                    .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())//自动列宽
                    .build();
            writeSheet = EasyExcel.writerSheet("data").build();

            logger.info("start export excel [" + fileName + "].");
        }

        public void write(List data) {
            excelWriter.write(data, writeSheet);
            logger.info("export data. write size: " + data.size());
            exportSum += data.size();
        }

        public void finish() {
            //finish 关闭流
            excelWriter.finish();
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            logger.info("export excel [" + fileName + "] successed. export record: " + exportSum);
        }
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

}






