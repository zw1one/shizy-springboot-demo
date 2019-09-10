package com.shizy.service.user.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.shizy.entity.user.UserExp;
import com.shizy.entity.user.UserPo;
import com.shizy.service.user.UserCsvService;
import com.shizy.service.user.UserService;
import com.shizy.utils.bean.BeanUtil;
import com.shizy.utils.excel.EasyExcelUtil;
import com.shizy.utils.excel.write.ExportExcel;
import com.shizy.utils.jdbc.InserBatchUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
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

    private static final Logger logger = LoggerFactory.getLogger(UserCsvServiceImpl.class);

    @Autowired
    private UserService userService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private InserBatchUtil inserBatchUtil;

    /***********************************************/

    @Override
    //默认仅抛出RuntimeException回滚，这里指定抛出任意Exception都回滚
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public JSONObject importData(MultipartFile file, Map<String, Object> params) throws Exception {

        logger.info("start import excel [" + file.getOriginalFilename() + "].");

        final int[] insertSum = {0};

        try (InputStream inputStream = file.getInputStream()) {
            UserPo po = new UserPo();

            /**
             * 边读取边写入数据库，避免一次内存中保存的查询数据过多内存溢出
             */
            EasyExcelUtil.read(inputStream, (context, data) -> {
                //读excel的回调函数，触发条件为：读完一页，或者一页读了5000条。

                List inserted = BeanUtil.copyMapParam2EntityList(data, po);

                //批量写入数据库
                int[][] insertRst = inserBatchUtil.insertBatch(inserted);

                for (int[] batchSum : insertRst) {
                    insertSum[0] += batchSum.length;
                }
            }, 5000, UserExp.class);

        }

        logger.info("import excel [" + file.getOriginalFilename() + "] successed. import record: " + insertSum[0]);

        return genReturn(insertSum[0], file.getOriginalFilename());
    }

    private JSONObject genReturn(int sum, String fileName) {
        JSONObject rtn = new JSONObject();
        rtn.put("file name", fileName);
        rtn.put("insert sum", sum);
        return rtn;
    }


    /***********************************************************/

    @Autowired
    private HttpServletResponse response;

    ExportExcel exportExcel = null;
    int exportSum = 0;

    @Override
    public void exportData(Map<String, Object> params) {

        String fileName = "user_data.xlsx";

        exportExcel = EasyExcelUtil.getExportExcel();
        exportExcel.init(fileName, response, UserExp.class);

        logger.info("start export excel [" + fileName + "].");

        /**
         * 边查库边写入，避免一次查询数据过多内存溢出
         */
        getExportList(1, 5000);

        logger.info("export excel [" + fileName + "] successed. export record: " + exportSum);

        exportExcel.finish();
    }

    private void getExportList(int page, int pageSize) {

        Page pageRecord = userService.queryList(null, new Page(page, pageSize));
        List data = pageRecord.getRecords();
        if (data.size() != 0) {
            afterQueryDo(data);//afterQueryDo
            getExportList(++page, pageSize);
        }
    }

    private void afterQueryDo(List data) {
        logger.info("export data. write size: " + data.size());
        exportSum += data.size();
        exportExcel.write(data);
    }

}
























