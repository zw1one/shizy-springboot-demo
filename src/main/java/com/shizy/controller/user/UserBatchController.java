package com.shizy.controller.user;

import com.shizy.common.json.JsonResult;
import com.shizy.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@Api(tags = "user-demo", description = "user CRUD")
public class UserBatchController {

    private static final Logger logger = LoggerFactory.getLogger(UserBatchController.class);

    @Autowired
    private UserService userService;

    @ApiOperation(value = "user delete", notes = "")
    @RequestMapping(value = "/user/deleteBatch", method = RequestMethod.POST)
    public JsonResult deleteBatch(@RequestBody List<String> param) {
        try {
            int result = userService.deleteBatch(param);
            return JsonResult.success(result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return JsonResult.fail();
        }
    }

}
























