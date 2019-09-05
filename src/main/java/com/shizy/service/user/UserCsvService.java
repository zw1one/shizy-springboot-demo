package com.shizy.service.user;

import com.alibaba.fastjson.JSONObject;
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
public interface UserCsvService {

    public JSONObject importData(MultipartFile file, Map<String, Object> params) throws Exception;

    public void exportData(Map<String, Object> params);
}



















