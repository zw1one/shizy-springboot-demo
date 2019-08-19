package com.shizy.service.user.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shizy.entity.user.User;
import com.shizy.mapper.user.UserMapper;
import com.shizy.service.user.UserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author shizy
 * @since 2019-08-19
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
