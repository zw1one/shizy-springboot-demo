package com.shizy.utils.excel;

import com.shizy.utils.excel.read.ReadCallback;
import com.shizy.utils.excel.read.ReadExcel;

import java.io.InputStream;
import java.util.Map;

public class EasyExcelUtil {

    public static void read(InputStream inputStream, ReadCallback readCallback, int callbackSize, Map<String, String> titleMap) {
        new ReadExcel(inputStream, 0, readCallback, callbackSize, titleMap).read();
    }
    public static void read(InputStream inputStream, ReadCallback readCallback, int callbackSize) {
        new ReadExcel(inputStream, 0, readCallback, callbackSize).read();
    }
    public static void read(InputStream inputStream, ReadCallback readCallback) {
        read(inputStream, readCallback, 1000);
    }


}






















