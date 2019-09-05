package com.shizy.utils.excel.read;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.metadata.Sheet;

import java.io.InputStream;


public class ReadExcel {

    private InputStream inputStream;

    private Integer headLineMun;
    private ReadCallback readCallback;

    public ReadExcel(InputStream inputStream, Integer headLineMun, ReadCallback readCallback) {
        this.inputStream = inputStream;
        this.headLineMun = headLineMun;
        this.readCallback = readCallback;
    }

    public void read() {
        final ExcelReader excelReader = new ExcelReader(inputStream, null,
                new CallbackAnalysisEventListener(headLineMun, readCallback));

        for (Sheet sheet : excelReader.getSheets()) {
            excelReader.read(sheet);
        }

    }
}














