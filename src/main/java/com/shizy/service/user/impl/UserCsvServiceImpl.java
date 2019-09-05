package com.shizy.service.user.impl;

import com.alibaba.fastjson.JSONObject;
import com.shizy.entity.user.UserPo;
import com.shizy.service.user.UserCsvService;
import com.shizy.utils.jdbc.InserBatchUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
    public JSONObject importData(MultipartFile file, Map<String, Object> params) {

        try (InputStream inputStream = file.getInputStream()) {

            System.out.println(inputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }

        List inserted = readExcel();
        int[][] insertRst = inserBatchUtil.insertBatch(inserted);

        return genReturn(insertRst, file.getOriginalFilename());
    }

    private JSONObject genReturn(int[][] insertRst, String fileName) {
        JSONObject rtn = new JSONObject();
        rtn.put("file name", fileName);

        int insertSum = 0;
        for (int[] batch : insertRst) {
            insertSum += batch.length;
        }
        rtn.put("insert sum", insertSum);

        return rtn;
    }

    private List readExcel() {

        //todo poi or fastExcel 两种方式读写excel

        /**
         * 今天把导入导出的两种弄好 明天弄原生mybatis crud 导入导出
         */

        List z = new ArrayList();
        z.add(new UserPo("1", "2", "3"));
        z.add(new UserPo("2", "2", "3"));
//        z.add(new UserPo("2", "2", "3"));

        return z;

    }

    /***********************************************************/

    @Override
    public void exportData(Map<String, Object> params) {

        System.out.println();

    }

}












