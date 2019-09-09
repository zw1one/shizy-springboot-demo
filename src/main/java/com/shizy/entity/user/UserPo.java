package com.shizy.entity.user;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author shizy
 * @since 2019-08-19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("user")
public class UserPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ExcelProperty("用户id")
    @TableId("user_id")
    private String userId;


    @ExcelProperty("用户名")
    @TableField("user_account")
    private String userAccount;


    @ExcelProperty("用户账号")
    @TableField("user_name")
    private String userName;

}
