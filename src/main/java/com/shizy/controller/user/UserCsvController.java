package com.shizy.controller.user;

import com.shizy.service.user.UserService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Api(tags = "user-demo", description = "user CRUD")
public class UserCsvController {

    private static final Logger logger = LoggerFactory.getLogger(UserCsvController.class);

    @Autowired
    private UserService userService;

}
























