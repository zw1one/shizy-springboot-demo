
package com.shizy.controller.welcome;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//@ApiIgnore
@Controller
@RequestMapping("/")
@Api(tags = "Welcome", description = "主页面")
public class WelcomeController {

    @ApiOperation(value = "进入主页面", notes = "")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String welcome() {
        return "welcome";
    }

}



























