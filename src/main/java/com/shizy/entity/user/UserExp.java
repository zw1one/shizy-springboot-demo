package com.shizy.entity.user;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * <p>
 * 导出中英文映射
 * </p>
 *
 * @author shizy
 * @since 2019-08-19
 */
public class UserExp {

    @ExcelProperty("用户id")
    private String userId;

    @ExcelProperty("用户名")
    private String userAccount;

    @ExcelProperty("用户账号")
    private String userName;

}
