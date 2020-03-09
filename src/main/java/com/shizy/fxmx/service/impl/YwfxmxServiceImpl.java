package com.shizy.fxmx.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shizy.fxmx.entity.YwfxmxPo;
import com.shizy.fxmx.mapper.YwfxmxMapper;
import com.shizy.fxmx.service.YwfxmxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 业务分析模型 服务实现类
 * </p>
 *
 * @author shizy
 * @since 2020-03-09
 */
@Service
public class YwfxmxServiceImpl extends ServiceImpl<YwfxmxMapper, YwfxmxPo> implements YwfxmxService {

    @Autowired
    private YwfxmxMapper ywfxmxMapper;

    @Override
    public Page queryList(Map param, Page pageOrDefalt) {
        return null;
    }
}
