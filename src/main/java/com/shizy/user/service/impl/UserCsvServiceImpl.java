package com.shizy.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shizy.user.entity.UserDto;
import com.shizy.user.entity.UserExp;
import com.shizy.user.entity.UserPo;
import com.shizy.user.mapper.UserMapper;
import com.shizy.user.service.UserCsvService;
import com.shizy.user.service.UserService;
import com.shizy.utils.bean.BeanUtil;
import com.shizy.utils.excel.EasyExcelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author shizy
 * @since 2019-08-19
 */
@Service
public class UserCsvServiceImpl extends ServiceImpl<UserMapper, UserPo> implements UserCsvService {

    private static final Logger logger = LoggerFactory.getLogger(UserCsvServiceImpl.class);

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private UserService userService;

    @Override
    public JSONObject importData(MultipartFile file, Map<String, Object> params) throws Exception {
        Integer excelSum = 0;
        final Integer[] sqlSum = {0};

        /*try (InputStream inputStream = file.getInputStream()) {
            excelSum = EasyExcelUtil.read(inputStream, (context, data) -> {
                List<UserPo> inserted = BeanUtil.copyPropertiesList(data, UserPo.class);
//                this.saveOrUpdateBatch(inserted, 5000);
                sqlSum[0] += data.size();
            }, 1000, UserExp.class);
        }*/

        /**
         * InputStream只能被一个ExcelRead占用，不能同时占用
         * http://www.hohode.com/2018/09/26/Excel%E8%A7%A3%E6%9E%90%E5%B7%A5%E5%85%B7easyexcel/
         */

        try (InputStream inputStream = file.getInputStream()) {
            excelSum = EasyExcelUtil.read(inputStream, (context, data) -> {
                List<UserPo> inserted = BeanUtil.copyPropertiesList(data, UserPo.class);
//                this.saveOrUpdateBatch(inserted, 5000);
                sqlSum[0] += data.size();
            }, 1000, UserExp.class, 0);
        }

        try (InputStream inputStream = file.getInputStream()) {
            excelSum = EasyExcelUtil.read(inputStream, (context, data) -> {
                List<UserPo> inserted = BeanUtil.copyPropertiesList(data, UserPo.class);
//                this.saveOrUpdateBatch(inserted, 5000);
                sqlSum[0] += data.size();
            }, 1000, UserExp.class, 1);
        }



        JSONObject result = new JSONObject();
        result.put("excelSum", excelSum);
        result.put("sqlSum", sqlSum[0]);
        return result;
    }

    public JSONObject importDataParallel(MultipartFile file, Map<String, Object> params) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(8);
        try (InputStream inputStream = file.getInputStream()) {
            EasyExcelUtil.read(inputStream, (context, data) -> {
                executor.execute(() -> {
                    List<UserPo> inserted = BeanUtil.copyMapParam2EntityList(data, UserPo.class);
                    this.saveOrUpdateBatch(inserted, 5000);
                });
            }, 5000, UserExp.class);
        }
        executor.shutdown();
        //手动等待，不需要结果则可以不等待
        while (!executor.isTerminated()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void exportDataAll(Map<String, Object> params) {
        EasyExcelUtil.export(
//                userService.queryList(null, new Page(1, 500)).getRecords(),
                getMockList(),
                "user_data.xlsx",
                response,
                UserExp.class
        );
    }

    @Override
    public void exportDataBatch(Map<String, Object> params) {
        EasyExcelUtil.EasyExcelExporter easyExcelExporter = new EasyExcelUtil.EasyExcelExporter("user_data.xlsx", response, UserExp.class);
//        queryListAndExport(1, 5000, easyExcelExporter);
        easyExcelExporter.write(getMockList());
        easyExcelExporter.finish();
    }

    /**
     * 边查库边写入，避免一次查询数据过多内存溢出
     */
    private void queryListAndExport(int page, int pageSize, EasyExcelUtil.EasyExcelExporter easyExcelExporter) {
        Page pageRecord = userService.queryList(null, new Page(page, pageSize));
        List data = pageRecord.getRecords();
        if (data.size() != 0) {
            easyExcelExporter.write(data);
            queryListAndExport(++page, pageSize, easyExcelExporter);
        }
    }

    private List getMockList() {
        List list = new ArrayList();
        for (int i = 0; i < 10; i++) {
            list.add(UserDto.builder()
                    .userId("111")
                    .userName("111")
                    .userAccount("111")
                    .build());
        }
        return list;
    }
}




















