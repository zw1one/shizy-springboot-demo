package com.shizy.user.service.impl;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.shizy.user.entity.UserDto;
import com.shizy.user.entity.UserPo;
import com.shizy.user.entity.UserVo;
import com.shizy.user.mapper.UserMapper;
import com.shizy.user.service.UserService;
import com.shizy.utils.bean.BeanUtil;
import com.shizy.utils.query.QueryUtil;
import com.shizy.utils.redis.CacheUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Autowired
    private CacheUtil<String, String, UserVo> cacheUtil;

    /***********************************************/

    @Override
    public UserPo queryDetailPo(String id) {
        if (StringUtils.isBlank(id)) {
            return null;
        }
        return userMapper.selectById(id);
    }

    @Override
//    @Cacheable(value = cacheKey, key = "#id")
    public UserVo queryDetailVo(String id) {
        if (StringUtils.isBlank(id)) {
            return null;
        }

        UserVo userVo = null;

        userVo = cacheUtil.getHash(cacheKey, id);
        if (userVo != null) {
            return userVo;
        }

        UserPo userPo = userMapper.selectById(id);
        if (userPo == null) {
            return null;
        }

        userVo = BeanUtil.copyProperties(userPo, new UserVo());
        cacheUtil.putHash(cacheKey, id, userVo);
        return userVo;
    }

    @Override
    public Page queryList(UserDto dto, Page page) {
        Wrapper wrapper = QueryUtil.getEntityCondition(dto, new UserPo());

        if (dto != null && dto.getNameAndAccount() != null && wrapper != null) {
            String param = dto.getNameAndAccount();
            wrapper.andNew()
                    .like("user_account", param)
                    .or()
                    .like("user_name", param);
        }

        List<UserPo> listPo = userMapper.selectPage(page, wrapper);

        List<UserVo> listVo = BeanUtil.copyPropertiesList(listPo, UserVo.class);
        page.setRecords(listPo);

        return page;
    }

    /***********************************************/

    /**
     * 若使用@Cacheable做缓存，返回值必须为要缓存的数据，且不方便设置过期时间、缓存格式，缓存在代码中的位置不可控，为了方便还是自己缓存好些
     * <p>
     * 缓存清理见 ClearRedisCache.java
     */

    @Override
    public String add(UserPo po) {

        String id = IdWorker.get32UUID();
        po.setUserId(id);

        int result = userMapper.insert(po);
        if (result > 0) {
            cacheUtil.putHash(cacheKey, po.getUserId(), BeanUtil.copyProperties(po, new UserVo()));
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
            cacheUtil.deleteHash(cacheKey, id);
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
            cacheUtil.deleteHash(cacheKey, ids.toArray());
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
            cacheUtil.putHash(cacheKey, po.getUserId(), BeanUtil.copyProperties(po, new UserVo()));
            return true;
        }
        return false;
    }

    /***********************************************/


}












