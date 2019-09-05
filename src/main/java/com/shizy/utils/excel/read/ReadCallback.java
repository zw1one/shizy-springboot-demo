package com.shizy.utils.excel.read;

import com.alibaba.excel.context.AnalysisContext;

import java.util.List;
import java.util.Map;

public interface ReadCallback {
    public void doAfterReadSheel(AnalysisContext context, List<Map> data);
}
