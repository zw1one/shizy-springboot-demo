package com.shizy.entity.user;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 导入/导出的实体类，以及列名中英文映射
 * </p>
 *
 * @author shizy
 * @since 2019-08-19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserExp {

    @ExcelProperty("用户id")
    private String userId;

    @ExcelProperty("用户名")
    private String userAccount;

    @ExcelProperty("用户账号")
    private String userName;

}
