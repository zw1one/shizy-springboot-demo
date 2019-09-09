package com.shizy.utils.excel.read;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.metadata.Sheet;

import java.io.InputStream;
import java.util.Map;


public class ReadExcel {

    private InputStream inputStream;

    private Integer headLineMun;
    private ReadCallback readCallback;
    private int callbackSize;
    private Map titleMap;

    public ReadExcel(InputStream inputStream, Integer headLineMun, ReadCallback readCallback) {
        this.inputStream = inputStream;
        this.headLineMun = headLineMun;
        this.readCallback = readCallback;
        this.callbackSize = 1000;
    }

    public ReadExcel(InputStream inputStream, Integer headLineMun, ReadCallback readCallback, int callbackSize) {
        this.inputStream = inputStream;
        this.headLineMun = headLineMun;
        this.readCallback = readCallback;
        this.callbackSize = callbackSize;
    }

    public ReadExcel(InputStream inputStream, Integer headLineMun, ReadCallback readCallback, int callbackSize, Map<String, String> titleMap) {
        this.inputStream = inputStream;
        this.headLineMun = headLineMun;
        this.readCallback = readCallback;
        this.callbackSize = callbackSize;
        this.titleMap = titleMap;
    }

    public ReadExcel() {

    }

    /**
     * 旧版read
     */
    public void read() {
        final ExcelReader excelReader = new ExcelReader(inputStream, null,
                new CallbackAnalysisEventListener(headLineMun, readCallback, callbackSize, titleMap));

        for (Sheet sheet : excelReader.getSheets()) {
            excelReader.read(sheet);
        }

    }

    /**
     * 新版read
     * 待修改：
     * 1、Listener里面要加泛型，要改类型转换
     * 2、新版读取时发生异常，会导致excel损坏，虽然对上传来的文件无所谓
     */
//    public void read() {
//
//        ExcelReader excelReader = null;
//        try {
//
//            excelReader = EasyExcel.read(inputStream, UserPo.class,
//                    new CallbackAnalysisEventListener(headLineMun, readCallback, callbackSize, titleMap))
//                    .build();
//
//            for (ReadSheet readSheet : excelReader.excelExecutor().sheetList()) {
//                excelReader.read(readSheet);
//            }
//
//        }finally {
//            // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
//            //运行这个关闭，若Listener中报错，则这个关闭也会出现异常，导致文件损坏，等官方修复吧
//            excelReader.finish();
//        }
//
//    }


}














