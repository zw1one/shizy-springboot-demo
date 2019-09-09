package com.shizy.utils.excel.read;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.shizy.utils.excel.EasyExcelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 一个AnalysisEventListener实例，来顺序读取所有页
 */
public class CallbackAnalysisEventListener extends AnalysisEventListener {

    private static final Logger logger = LoggerFactory.getLogger(EasyExcelUtil.class);

    private Integer headLineMun;
    private ReadCallback readCallback;

    //读了callbackSize条，调一次callback。读完该页，也会调一次callback
    private int callbackSize;

    private Class head;//列中文名 -> 列名

    private List<String> titles;//列名
    private List<Map> data = new ArrayList();//数据

    public CallbackAnalysisEventListener(Integer headLineMun, ReadCallback readCallback, int callbackSize) {
        this.headLineMun = headLineMun;
        this.readCallback = readCallback;
        this.callbackSize = callbackSize;
    }

    public CallbackAnalysisEventListener(Integer headLineMun, ReadCallback readCallback, int callbackSize, Class head) {
        this.headLineMun = headLineMun;
        this.readCallback = readCallback;
        this.callbackSize = callbackSize;
        this.head = head;
    }

    public CallbackAnalysisEventListener(Integer headLineMun, ReadCallback readCallback) {
        this.headLineMun = headLineMun;
        this.readCallback = readCallback;
        this.callbackSize = 1000;
    }

    /**
     * 读完一行
     * 把读到的数据丢到dataList中
     */
    @Override
    public void invoke(Object object, AnalysisContext context) {
        List<String> rowData = (List<String>) object;
        //取title
        if (context.getCurrentRowNum().equals(headLineMun)) {
            titles = getTitle(rowData, head);
            return;
        }
        //set data
        Map rowMap = new HashMap<String, Object>();
        for (int i = 0; i < rowData.size(); i++) {
            String rowStr = rowData.get(i);
//            if (rowStr != null) {
//                rowStr = rowStr;
//            }
            rowMap.put(titles.get(i), rowStr);
        }
        data.add(rowMap);

        //读一页的数据时，list达到调用一次callbackSize时，调用一次callback。否则list过大会溢栈
        if (data.size() >= callbackSize) {
            readCallback.doAfterReadSheel(context, data);
            logger.info("-- " + "readed sheet " + context.getCurrentSheet().getSheetNo() + " [" + context.getCurrentSheet().getSheetName() + "]" + " read size：" + data.size());
            data = new ArrayList();//清空上一次的data
        }
    }

    private List getTitle(List<String> rowData, Class head) {
        if (head == null) {
            return rowData;
        }

        List titleEn = new ArrayList();

        for (Field field : head.getDeclaredFields()) {
            ExcelProperty excelProperty = field.getAnnotation(ExcelProperty.class);
            if(excelProperty == null){
                continue;
            }
            titleEn.add(field.getName());
        }
        return titleEn;
    }

    /**
     * 读完一页
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        if (data == null || data.size() <= 0) {
            return;
        }

        readCallback.doAfterReadSheel(context, data);
        logger.info("-- " + "readed sheet " + context.getCurrentSheet().getSheetNo() + " [" + context.getCurrentSheet().getSheetName() + "]" + " read size：" + data.size());
        data = new ArrayList();//清空上一次的data
    }
}























