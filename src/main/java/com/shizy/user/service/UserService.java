package com.shizy.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shizy.user.entity.UserDto;
import com.shizy.user.entity.UserPo;
import com.shizy.user.entity.UserVo;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author shizy
 * @since 2019-08-19
 */
public interface UserService {

    public static final String cacheKey = UserVo.class.getSimpleName();

    public UserPo queryDetailPo(String id);

    public UserVo queryDetailVo(String id);

    public Page queryList(UserDto dto, Page page);

    public String add(UserPo po);

    public boolean delete(String id);

    public int deleteBatch(List ids);

    public boolean updateById(UserPo po);

}



















