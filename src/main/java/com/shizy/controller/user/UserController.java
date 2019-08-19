
package com.shizy.controller.user;

import com.shizy.common.json.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@Api(tags = "user-demo", description = "user CRUD")
public class UserController {

    @ApiOperation(value = "user add", notes = "")
    @RequestMapping(value = "/user/add", method = RequestMethod.POST)
    public JsonResult add(@RequestBody Map<String, Object> params) {

        return JsonResult.success("");
    }

    @ApiOperation(value = "user delete", notes = "")
    @RequestMapping(value = "/user/delete", method = RequestMethod.POST)
    public JsonResult delete(@RequestBody Map<String, Object> params) {

        return JsonResult.success("");
    }

    @ApiOperation(value = "user query detail", notes = "")
    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public JsonResult queryDetail(@PathVariable String userId) {

        return JsonResult.success("");
    }

    @ApiOperation(value = "user query list", notes = "")
    @RequestMapping(value = "/user/queryList", method = RequestMethod.POST)
    public JsonResult queryList(@RequestBody Map<String, Object> params) {

        return JsonResult.success("");
    }

}



























