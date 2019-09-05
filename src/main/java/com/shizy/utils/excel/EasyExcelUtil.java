package com.shizy.utils.excel;

import com.shizy.utils.excel.read.ReadCallback;
import com.shizy.utils.excel.read.ReadExcel;

import java.io.InputStream;

public class EasyExcelUtil {

    public static void read(InputStream inputStream, ReadCallback readCallback) {
        //开读!
        new ReadExcel(inputStream, 0, readCallback)
                .read();
    }


}






















