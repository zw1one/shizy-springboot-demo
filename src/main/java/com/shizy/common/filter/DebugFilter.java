package com.shizy.common.filter;


import com.shizy.utils.LogUtil;
import com.shizy.utils.http.HttpUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

//@Component
//@ServletComponentScan
//@WebFilter(urlPatterns = "*")
public class DebugFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

//        if (request.getContentType() != null && request.getContentType().contains("multipart/form-data")) {
//            ((HttpServletRequest) request).getParameterMap();
//        }

        LogUtil.info("=========debug request headers==========");
        LogUtil.info(HttpUtil.getHeadersInfo((HttpServletRequest) request));

        long time1 = System.currentTimeMillis();

        chain.doFilter(request, response);

        long time2 = System.currentTimeMillis();
        LogUtil.info("========================================");
        LogUtil.info("--server request process time: " + (time2 - time1) + "ms");
        LogUtil.info("========================================");

    }

    @Override
    public void destroy() {

    }
}
