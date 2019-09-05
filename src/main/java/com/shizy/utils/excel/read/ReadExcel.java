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
    public ReadExcel(InputStream inputStream, Integer headLineMun, ReadCallback readCallback, int callbackSize, Map<String,String> titleMap) {
        this.inputStream = inputStream;
        this.headLineMun = headLineMun;
        this.readCallback = readCallback;
        this.callbackSize = callbackSize;
        this.titleMap = titleMap;
    }

    public void read() {
        final ExcelReader excelReader = new ExcelReader(inputStream, null,
                new CallbackAnalysisEventListener(headLineMun, readCallback, callbackSize, titleMap));

        for (Sheet sheet : excelReader.getSheets()) {
            excelReader.read(sheet);
        }

    }
}














