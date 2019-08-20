package com.shizy.service.user;

import com.shizy.entity.user.UserDto;
import com.shizy.entity.user.UserPo;
import com.shizy.entity.user.UserVo;

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

    public UserPo queryDetailPo(String id);

    public UserVo queryDetailVo(String id);

    public List<UserVo> queryList(UserDto dto);

    public String add(UserPo po);

    public boolean delete(String id);

    public boolean updateById(UserPo po);


}
