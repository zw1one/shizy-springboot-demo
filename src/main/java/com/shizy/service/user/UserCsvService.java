package com.shizy.service.user;

import com.baomidou.mybatisplus.plugins.Page;
import com.shizy.entity.user.UserDto;
import com.shizy.entity.user.UserPo;
import com.shizy.entity.user.UserVo;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author shizy
 * @since 2019-08-19
 */
public interface UserCsvService {

    public void importData(InputStream inputStream, Map<String, Object> params);

    public void exportData(Map<String, Object> params);
}



















