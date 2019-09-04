package com.shizy.service.user.impl;

import com.shizy.entity.user.UserPo;
import com.shizy.service.user.UserCsvService;
import com.shizy.utils.jdbc.InserBatchUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
//
//    @Autowired
//    private CacheUtil<String, String, UserVo> cacheUtil;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private InserBatchUtil inserBatchUtil;


    /***********************************************/


    @Override
    public void importData(InputStream inputStream, Map<String, Object> params) {

        List z = new ArrayList();
        z.add(new UserPo("1", "2", "3"));

        inserBatchUtil.insertBatch(z);

        System.out.println();

    }

    @Override
    public void exportData(Map<String, Object> params) {

        System.out.println();

    }

    public static void main(String[] args) {

        String s = "PreparedStatementCallback; SQL [INSERT INTO user (user_id, user_account, user_name) VALUES (?, ?, ?)Duplicate entry '123' for key 'PRIMARY'; nested exception is java.sql.BatchUpdateException: Duplicate entry '123' for key 'PRIMARY'";

        System.out.println(new UserCsvServiceImpl().getDuplicateKey(s));
    }

    private String getDuplicateKey(String emsg) {
        Pattern p = Pattern.compile("Duplicate entry '.*' for key 'PRIMARY';");
        Matcher m = p.matcher(emsg);

        String match = null;
        if (m.find()) {
            match = m.group();
        }
        match = match.substring("Duplicate entry ".length(), match.indexOf(" for key 'PRIMARY';"));
        match = match.substring(1, match.length() - 1);
        return match;
    }


}












