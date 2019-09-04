package com.shizy.controller.user;

import com.shizy.common.json.JsonResult;
import com.shizy.service.user.UserCsvService;
import com.shizy.utils.format.FormatUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Api(tags = "user-demo", description = "user CRUD")
public class UserCsvController {

    private static final Logger logger = LoggerFactory.getLogger(UserCsvController.class);

    @Autowired
    private UserCsvService userCsvService;

    @ApiOperation(value = "import user data", notes = "")
    @RequestMapping(value = "/user/importData", method = RequestMethod.POST)
    public JsonResult importData(@RequestParam("file") MultipartFile file, @RequestParam Map<String, Object> params) {
        try {
            if (file == null || file.isEmpty()) {
                return JsonResult.fail("file is null");
            }

            synchronized (UserCsvController.class) {
                return importDataCatch(file.getInputStream(), params);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return JsonResult.fail();
        }
    }

    private JsonResult importDataCatch(InputStream inputStream, Map<String, Object> params) throws Exception {

        try {
            userCsvService.importData(inputStream, params);
        } catch (DuplicateKeyException e) {
            return JsonResult.fail("primary key duplicate: [" + FormatUtil.getDuplicateKey(e.getMessage()) + "]");
        } catch (Exception e) {
            throw e;
        }
        return JsonResult.success();
    }

    @ApiOperation(value = "export user data", notes = "")
    @RequestMapping(value = "/user/exportData", method = RequestMethod.GET)
    public JsonResult exportData(@RequestParam Map<String, Object> params) {
        try {
            userCsvService.exportData(params);
            return JsonResult.success();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return JsonResult.fail();
        }
    }

}
























