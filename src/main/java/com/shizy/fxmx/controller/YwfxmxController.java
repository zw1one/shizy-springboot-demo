package com.shizy.fxmx.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.shizy.common.json.JsonResult;
import com.shizy.common.json.page.PageDto;
import com.shizy.user.controller.UserController;
import com.shizy.fxmx.service.YwfxmxService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 分析模型
 * </p>
 *
 * @author shizy
 * @since 2020-03-09
 */
@RestController
@RequestMapping("/ywfx")
@Api(tags = "YwfxmxController", description = "业务分析模型管理")
public class YwfxmxController {

    private static final Logger logger = LoggerFactory.getLogger(YwfxmxController.class);

    @Autowired
    private YwfxmxService ywfxmxService;

    @ApiOperation(value = "获取分析模型列表", notes = "")
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public JsonResult queryList(@RequestBody(required = false) Map param, @ModelAttribute PageDto pageDto) {
        try {
            Page pageList = ywfxmxService.queryList(param, pageDto.getPageOrDefalt());
            return JsonResult.success(pageList);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return JsonResult.fail();
        }
    }
}




























