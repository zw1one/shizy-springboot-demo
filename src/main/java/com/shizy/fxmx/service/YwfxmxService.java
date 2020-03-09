package com.shizy.fxmx.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.shizy.fxmx.entity.YwfxmxPo;

import java.util.Map;

/**
 * <p>
 * 业务分析模型 服务类
 * </p>
 *
 * @author shizy
 * @since 2020-03-09
 */
public interface YwfxmxService extends IService<YwfxmxPo> {

    public Page queryList(Map param, Page pageOrDefalt);

}
