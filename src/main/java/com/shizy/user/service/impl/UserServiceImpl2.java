package com.shizy.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shizy.user.entity.UserDto;
import com.shizy.user.entity.UserPo;
import com.shizy.user.entity.UserVo;
import com.shizy.user.mapper.UserMapper;
import com.shizy.user.service.UserService;
import com.shizy.utils.bean.BeanUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>
 * no redis
 * </p>
 *
 * @author shizy
 * @since 2019-08-19
 */
//@Service
public class UserServiceImpl2 implements UserService {

    @Autowired
    private UserMapper userMapper;

    /***********************************************/

    @Override
    public UserPo queryDetailPo(String id) {
        if (StringUtils.isBlank(id)) {
            return null;
        }

        return userMapper.selectById(id);
    }

    @Override
    public UserVo queryDetailVo(String id) {
        if (StringUtils.isBlank(id)) {
            return null;
        }

        UserPo userPo = userMapper.selectById(id);
        if (userPo == null) {
            return null;
        }
        return BeanUtil.copyProperties(userPo, new UserVo());
    }

    @Override
    public Page queryList(UserDto dto, Page page) {
        return null;
    }

    /***********************************************/

    @Override
    public String add(UserPo po) {
        String id = IdWorker.get32UUID();
        po.setUserId(id);

        int result = userMapper.insert(po);
        if (result > 0) {
            return id;
        }
        return null;
    }

    @Override
    public boolean delete(String id) {
        if (StringUtils.isBlank(id)) {
            return false;
        }

        int result = userMapper.deleteById(id);
        return result > 0;
    }

    @Override
    public int deleteBatch(List ids) {
        if (ids == null || ids.size() <= 0) {
            return -1;
        }

        int result = userMapper.deleteBatchIds(ids);
        if (result > 0) {
            return result;
        }
        return -1;
    }

    @Override
    public boolean updateById(UserPo po) {
        if (StringUtils.isBlank(po.getUserId())) {
            return false;
        }

        int result = userMapper.updateById(po);
        return result > 0;
    }

    /***********************************************/


}












