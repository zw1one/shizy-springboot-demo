package com.shizy.service.user.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.shizy.entity.user.UserPo;
import com.shizy.service.user.UserCsvService;
import com.shizy.service.user.UserService;
import com.shizy.utils.bean.BeanUtil;
import com.shizy.utils.excel.EasyExcelUtil;
import com.shizy.utils.jdbc.InserBatchUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author shizy
 * @since 2019-08-19
 */
@Service
public class UserCsvServiceImpl implements UserCsvService {

    @Autowired
    private UserService userService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private InserBatchUtil inserBatchUtil;

    /***********************************************/

    private static Map<String, String> titleMap = new HashMap<>();

    static {
        /**
         * 若切换到easyexcel则不需要这个，会按字段的顺序对应excel的顺序，中英文转换通过注解
         */

        //列中文名 - po成员变量名
        titleMap.put("用户编号", "userId");
        titleMap.put("用户名", "userName");
        titleMap.put("用户账号", "userAccount");
    }

    @Override
    //默认仅抛出RuntimeException回滚，这里指定抛出任意Exception都回滚
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public JSONObject importData(MultipartFile file, Map<String, Object> params) throws IOException {

        final int[] insertSum = {0};
        try (InputStream inputStream = file.getInputStream()) {
            UserPo po = new UserPo();

            //读excel的回调函数，触发条件为：读完一页，或者一页读了5000条。
            EasyExcelUtil.read(inputStream, (context, data) -> {
                List inserted = BeanUtil.copyMapParam2EntityList(data, po);

                //批量写入数据库
                int[][] insertRst = inserBatchUtil.insertBatch(inserted);

                for (int[] batchSum : insertRst) {
                    insertSum[0] += batchSum.length;
                }
            }, 5000, titleMap);

        }

        return genReturn(insertSum[0], file.getOriginalFilename());
    }

    private JSONObject genReturn(int sum, String fileName) {
        JSONObject rtn = new JSONObject();
        rtn.put("file name", fileName);
        rtn.put("insert sum", sum);
        return rtn;
    }


    /***********************************************************/

    @Override
    public void exportData(Map<String, Object> params) {
        getExportList(1, 5000);


    }

    private void getExportList(int page, int pageSize) {

        Page pageRecord = userService.queryList(null, new Page(page, pageSize));
        List data = pageRecord.getRecords();
        afterQueryDo(data);
        if (data.size() != 0) {
            getExportList(++page, pageSize);
        }
    }

    @Autowired
    private HttpServletResponse response;

    private void afterQueryDo(List data) {

        System.out.println(data.size());


    }


}

class ExportExcel {

    ExcelWriter excelWriter = null;

    WriteSheet writeSheet = null;

    private void init(HttpServletResponse response) {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=demo.xlsx");


        try {
            excelWriter = EasyExcel.write(response.getOutputStream(), UserPo.class).build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        writeSheet = EasyExcel.writerSheet("data").build();

        /// 千万别忘记finish 会帮忙关闭流
        excelWriter.finish();
    }

    private void write(List data) {
        // 第一次写入会创建头
        excelWriter.write(data, writeSheet);
    }


}





















