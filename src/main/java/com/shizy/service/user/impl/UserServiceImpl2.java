package com.shizy.service.user.impl;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.shizy.entity.user.UserDto;
import com.shizy.entity.user.UserPo;
import com.shizy.entity.user.UserVo;
import com.shizy.mapper.user.UserMapper;
import com.shizy.service.user.UserService;
import com.shizy.utils.auth.IdUtil;
import com.shizy.utils.bean.BeanUtil;
import com.shizy.utils.query.QueryUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return BeanUtil.copyParam2Entity(userPo, new UserVo());
    }

    @Override
    public Page queryList(UserDto dto, Page page) {
        Wrapper wrapper = QueryUtil.getEntityCondition(dto, new UserPo());

        if (dto.getNameAndAccount() != null && wrapper != null) {
            String param = dto.getNameAndAccount();
            wrapper.andNew()
                    .like("user_account", param)
                    .or()
                    .like("user_name", param);
        }

        List<UserPo> listPo = userMapper.selectPage(page, wrapper);

        List<UserVo> listVo = BeanUtil.copyParam2EntityList(listPo, new UserVo());
        page.setRecords(listPo);

        return page;
    }

    /***********************************************/

    @Override
    public String add(UserPo po) {
        String id = IdUtil.genUUID();
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
        if (result > 0) {
            return true;
        }
        return false;
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
        if (result > 0) {
            return true;
        }
        return false;
    }

    /***********************************************/


}












