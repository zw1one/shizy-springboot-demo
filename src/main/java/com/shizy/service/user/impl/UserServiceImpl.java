package com.shizy.service.user.impl;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.shizy.entity.user.UserDto;
import com.shizy.entity.user.UserPo;
import com.shizy.entity.user.UserVo;
import com.shizy.mapper.user.UserMapper;
import com.shizy.service.user.UserService;
import com.shizy.utils.IdUtil;
import com.shizy.utils.bean.BeanUtil;
import com.shizy.utils.query.QueryUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author shizy
 * @since 2019-08-19
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /***********************************************/

    @Override
    public UserPo queryDetailPo(String userId) {
        if (StringUtils.isBlank(userId)) {
            return null;
        }
        return userMapper.selectById(userId);
    }

    @Override
    public UserVo queryDetailVo(String userId) {
        if (StringUtils.isBlank(userId)) {
            return null;
        }

        UserPo userPo = userMapper.selectById(userId);
        if (userPo == null) {
            return null;
        }

        return BeanUtil.copyParam2Entity(userPo, new UserVo());
    }

    @Override
    public List<UserVo> queryList(UserDto dto) {
        Wrapper wrapper = QueryUtil.getEntityCondition(dto, new UserPo());

        if(dto.getNameAndAccount() != null && wrapper != null){
            String param = dto.getNameAndAccount();
            wrapper.andNew()
                    .like("user_account", param)
                    .or()
                    .like("user_name", param);
        }

        List<UserPo> listPo = userMapper.selectList(wrapper);

        return BeanUtil.copyParam2EntityList(listPo, new UserVo());
    }

    /***********************************************/

    @Override
    public String add(UserPo po) {

        String id = IdUtil.genUUID();
        po.setUserId(id);

        int result = userMapper.insert(po);
        if(result > 0){
            return id;
        }
        return null;
    }

    @Override
    public boolean delete(String id) {
        int result = userMapper.deleteById(id);
        if(result > 0){
            return true;
        }
        return false;
    }

    @Override
    public boolean updateById(UserPo po) {
        int result = userMapper.updateById(po);;
        if(result > 0){
            return true;
        }
        return false;
    }

    /***********************************************/


}






















