package com.shizy.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.shizy.common.json.JsonResult;
import com.shizy.service.user.UserCsvService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

@RestController
@RequestMapping("/api")
@Api(tags = "user-demo", description = "user CRUD")
public class UserCsvController {

    private static final Logger logger = LoggerFactory.getLogger(UserCsvController.class);

    @Autowired
    private UserCsvService userCsvService;

    /**************************************************************/

    @ApiOperation(value = "import user data", notes = "测试见：http://127.0.0.1/cvs.html")
    @RequestMapping(value = "/user/importData", method = RequestMethod.POST)
    public JsonResult importData(@RequestParam("file") MultipartFile file,
                                 @RequestParam(required = false) Map<String, Object> params) {
        try {
            if (file == null || file.isEmpty()) {
                return JsonResult.fail("file is null");
            }
            synchronized (UserCsvController.class) {
                JSONObject rtn = userCsvService.importData(file, params);
                return JsonResult.success(rtn);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return JsonResult.fail("import failed!");
        }
    }

    @ApiIgnore
    @RequestMapping(value = "/user/importMultiData", method = RequestMethod.POST)
    public JsonResult importMultiData(@RequestParam("files") MultipartFile[] files, @RequestParam Map<String, Object> params) {
        try {
            return JsonResult.success();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return JsonResult.fail();
        }
    }

    /**************************************************************/

    @ApiOperation(value = "export user data", notes = "不要使用swagger测试导出，文件可能损坏")
    @RequestMapping(value = "/user/exportData", method = RequestMethod.GET)
    public void exportData(@RequestParam(required = false) Map<String, Object> params) {
        try {
            userCsvService.exportData(params);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

}
























