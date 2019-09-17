package com.shizy.utils.excel.read;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.metadata.Sheet;

import java.io.InputStream;


public class ReadExcel {

    ExcelReader excelReader;

    public ReadExcel(InputStream inputStream, Integer headLineMun, ReadCallback readCallback, int callbackSize, Class head) {
        excelReader = new ExcelReader(inputStream, null,
                new CallbackAnalysisEventListener(headLineMun, readCallback, callbackSize, head));
    }

    /**
     * 旧版read
     * easyexcel的新版还在迭代中，功能不一定稳定，代码写法也不一定就是现在的模式。暂时不用新版重写了。
     */
    public void read() {
        for (Sheet sheet : excelReader.getSheets()) {
            excelReader.read(sheet);
        }
    }

    /**
     * 新版read
     * 待修改：
     * 1、Listener里面要加泛型，要改类型转换。旧版的做法是自己手动处理类型
     * 2、Listener可以不加泛型，返回为map，旧版返回为list，懒得改了
     * 3、新版读取时发生异常时，会导致excel损坏，虽然不影响上传来的excel。因为每次都会传一个
     *
     * 因为新版还不是稳定版 没有必要改成新版的 等哪天他写法又变了的
     */
//    public void read2() {
//
//        try {
//            for (ReadSheet readSheet : excelReader.excelExecutor().sheetList()) {
//                excelReader.read(readSheet);
//            }
//        } finally {
//            //运行这个关闭，若Listener中报错，则这个关闭也会出现异常，导致文件损坏，等官方修复吧
//
//            // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
//            excelReader.finish();
//        }
//
//    }


}














