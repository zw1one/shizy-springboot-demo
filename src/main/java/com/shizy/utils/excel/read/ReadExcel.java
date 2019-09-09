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
    private Class head;

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

    public ReadExcel(InputStream inputStream, Integer headLineMun, ReadCallback readCallback, int callbackSize, Class head) {
        this.inputStream = inputStream;
        this.headLineMun = headLineMun;
        this.readCallback = readCallback;
        this.callbackSize = callbackSize;
        this.head = head;
    }

    public ReadExcel() {

    }

    /**
     * 旧版read
     * easyexcel的新版还在迭代中，功能不一定稳定，代码写法也不一定就是现在的模式。暂时不用新版重写了。
     */
    public void read() {
        final ExcelReader excelReader = new ExcelReader(inputStream, null,
                new CallbackAnalysisEventListener(headLineMun, readCallback, callbackSize, head));

        for (Sheet sheet : excelReader.getSheets()) {
            excelReader.read(sheet);
        }

    }

    /**
     * 新版read
     * 待修改：
     * 1、Listener里面要加泛型，要改类型转换。旧版的做法是自己手动处理类型
     * 2、新版读取时发生异常时，会导致excel损坏，虽然对上传来的文件无所谓
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














