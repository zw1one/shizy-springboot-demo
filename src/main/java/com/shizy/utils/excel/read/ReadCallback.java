package com.shizy.utils.excel.read;

import com.alibaba.excel.context.AnalysisContext;

import java.util.List;
import java.util.Map;

@Deprecated
public interface ReadCallback<T> {

    public void doAfterReadSheel(AnalysisContext context, List<T> data);

}
