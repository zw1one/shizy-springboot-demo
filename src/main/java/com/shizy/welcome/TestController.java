package com.shizy.welcome;

import com.alibaba.fastjson.JSON;
import com.shizy.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/test")
@Api(tags = "test", description = "lol")
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "lol", method = RequestMethod.POST)
    @ApiOperation(value = "lol")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param", value = "{\"param-key\":\"param-value\"}"),
    })
    public String add(@RequestBody Map param) {

        try {
            userService.queryDetailVo("1");
        }catch (Exception e){
            System.out.println();
        }

        return JSON.toJSONString(param);
    }


}






















