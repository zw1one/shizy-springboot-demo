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
public interface UserCsvService extends IService<UserPo> {

    /**
     * 分批导入。读取一批excel数据后，先写入这一批数据
     */
    public JSONObject importData(MultipartFile file, Map<String, Object> params) throws Exception;

    /**
     * 分批导出。查询一批数据库数据后，先写入这一批数据到excel
     */
    public void exportDataBatch(Map<String, Object> params);

    /**
     * 全量导出。查询全量数据后，直接导出到excel
     */
    public void exportDataAll(Map<String, Object> params);

}



















