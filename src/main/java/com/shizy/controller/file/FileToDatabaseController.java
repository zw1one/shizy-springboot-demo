
package com.shizy.controller.file;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

//@ApiIgnore
@RestController
@RequestMapping("/ftd")
@Api(tags = "FileToDatabase", description = "文件导入到数据库")
public class FileToDatabaseController {

    @ApiOperation(value = "XLSX to MongoDB", notes = "")
    @RequestMapping(value = "excel2mongo", method = RequestMethod.POST)
//    public void excel2mongo(@RequestBody(required = false) Map param, @RequestParam("file") MultipartFile files) {
    public Map excel2mongo(@RequestParam("file") MultipartFile files, @RequestParam Map<String, Object> params) {

        System.out.println();

        Map rtnMap = new HashMap();
        rtnMap.put("flag", true);
        rtnMap.put("msg", "msg");

        return rtnMap;
    }

}



























