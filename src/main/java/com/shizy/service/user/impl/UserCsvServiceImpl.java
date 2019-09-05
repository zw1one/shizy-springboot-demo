package com.shizy.service.user.impl;

import com.alibaba.fastjson.JSONObject;
import com.shizy.entity.user.UserPo;
import com.shizy.service.user.UserCsvService;
import com.shizy.utils.bean.BeanUtil;
import com.shizy.utils.excel.EasyExcelUtil;
import com.shizy.utils.jdbc.InserBatchUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

//    @Autowired
//    private UserMapper userMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private InserBatchUtil inserBatchUtil;

    /***********************************************/

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
            }, 5000);

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

        System.out.println();

    }

}












