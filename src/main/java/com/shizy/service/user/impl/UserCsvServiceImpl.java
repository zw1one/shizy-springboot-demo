package com.shizy.service.user.impl;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.shizy.entity.user.UserDto;
import com.shizy.entity.user.UserPo;
import com.shizy.entity.user.UserVo;
import com.shizy.mapper.user.UserMapper;
import com.shizy.service.user.UserCsvService;
import com.shizy.service.user.UserService;
import com.shizy.utils.auth.IdUtil;
import com.shizy.utils.bean.BeanUtil;
import com.shizy.utils.query.QueryUtil;
import com.shizy.utils.redis.CacheUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
//
//    @Autowired
//    private CacheUtil<String, String, UserVo> cacheUtil;

    /***********************************************/


    @Override
    public void importData(InputStream inputStream, Map<String, Object> params) {

        System.out.println();

    }

    @Override
    public void exportData(Map<String, Object> params) {

        System.out.println();

    }


}












