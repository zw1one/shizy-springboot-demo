package com.shizy.user.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shizy.common.json.JsonResult;
import com.shizy.common.json.page.PageDto;
import com.shizy.user.controller.param.UserDto;
import com.shizy.user.entity.UserPo;
import com.shizy.user.entity.UserVo;
import com.shizy.user.service.UserService;
import com.shizy.utils.bean.BeanUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Api(tags = "user-demo", description = "user CRUD")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @ApiOperation(value = "user add", notes = "")
    @RequestMapping(value = "/user/add", method = RequestMethod.POST)
    public JsonResult add(@RequestBody UserDto param) {
        try {
            UserPo po = BeanUtil.copyProperties(param, new UserPo());
            String result = userService.add(po);
            return JsonResult.success(result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return JsonResult.fail();
        }
    }

    @ApiOperation(value = "user update", notes = "")
    @RequestMapping(value = "/user/update", method = RequestMethod.POST)
    public JsonResult add(@RequestBody UserPo param) {
        try {
            UserPo po = BeanUtil.copyProperties(param, new UserPo());
            boolean result = userService.updateById(po);
            return JsonResult.success(result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return JsonResult.fail();
        }
    }

    @ApiOperation(value = "user delete", notes = "")
    @RequestMapping(value = "/user/delete", method = RequestMethod.POST)
    public JsonResult delete(@RequestBody UserDto param) {
        try {
            boolean result = userService.delete(param.getUserId());
            return JsonResult.success(result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return JsonResult.fail();
        }
    }

    @ApiOperation(value = "user query detail", notes = "")
    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public JsonResult queryDetail(@PathVariable String userId) {
        try {
            UserVo userVo = userService.queryDetailVo(userId);
            return JsonResult.success(userVo);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return JsonResult.fail();
        }
    }

    @ApiOperation(value = "user query list", notes = "")
    @RequestMapping(value = "/user/list", method = RequestMethod.POST)
    public JsonResult queryList(@RequestBody(required = false) com.shizy.user.entity.UserDto userDto, @ModelAttribute PageDto pageDto) {
        try {
            Page pageList = userService.queryList(userDto, PageDto.getPageInstance(pageDto));
            return JsonResult.success(pageList);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return JsonResult.fail();
        }
    }

}
















