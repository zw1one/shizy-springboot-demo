package com.shizy.utils.excel;

import com.shizy.utils.excel.read.ReadCallback;
import com.shizy.utils.excel.read.ReadExcel;

import java.io.InputStream;
import java.util.Map;

/**
 * 读写excel使用了阿里的easyexcel包，称比poi包内存消耗小很多
 * https://github.com/alibaba/easyexcel
 *
 * easyexcel封装了批量读写，但是若有对复杂excel，如具体操作某行某列的情况，还是建议用poi手动处理。
 */
public class EasyExcelUtil {

    /**
     * read
     *
     * @param inputStream excel file imputstream
     * @param readCallback 读取到一定条数或者读完一页的回调函数
     * @param callbackSize 读取到一定条数或者读完一页的"一定条数"
     * @param titleMap 列名的中文名，英文名相互转换
     */
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






















