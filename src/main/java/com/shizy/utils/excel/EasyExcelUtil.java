package com.shizy.utils.excel;

import com.shizy.utils.excel.read.ReadCallback;
import com.shizy.utils.excel.read.ReadExcel;

import java.io.InputStream;

public class EasyExcelUtil {

    public static void read(InputStream inputStream, ReadCallback readCallback, int callbackSize) {
        //开读!
        new ReadExcel(inputStream, 0, readCallback, callbackSize)
                .read();
    }
    public static void read(InputStream inputStream, ReadCallback readCallback) {
        read(inputStream, readCallback, 1000);
    }


}






















