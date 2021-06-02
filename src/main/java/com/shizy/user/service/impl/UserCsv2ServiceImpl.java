package com.shizy.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shizy.user.entity.UserExp;
import com.shizy.user.entity.UserPo;
import com.shizy.user.mapper.UserMapper;
import com.shizy.user.service.UserCsv2Service;
import com.shizy.utils.bean.BeanUtil;
import com.shizy.utils.excel.EasyExcelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author shizy
 * @since 2019-08-19
 */
@Service
public class UserCsv2ServiceImpl extends ServiceImpl<UserMapper, UserPo> implements UserCsv2Service {

    private static final Logger logger = LoggerFactory.getLogger(UserCsv2ServiceImpl.class);


    @Override
    public JSONObject importData(MultipartFile file, Map<String, Object> params) throws Exception {
        try (InputStream inputStream = file.getInputStream()) {
            EasyExcelUtil.read(inputStream, (context, data) -> {
                List<UserPo> inserted = BeanUtil.copyMapParam2EntityList(data, UserPo.class);
                this.saveOrUpdateBatch(inserted, 5000);
            }, 5000, UserExp.class);
        }
        return null;
    }


    @Override
    public void exportData(Map<String, Object> params) {

    }
}




















