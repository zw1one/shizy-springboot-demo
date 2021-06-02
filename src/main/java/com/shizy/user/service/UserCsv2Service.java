package com.shizy.user.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shizy.user.entity.UserPo;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author shizy
 * @since 2019-08-19
 */
public interface UserCsv2Service extends IService<UserPo> {

    public JSONObject importData(MultipartFile file, Map<String, Object> params) throws Exception;

    public void exportData(Map<String, Object> params);
}



















