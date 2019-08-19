package com.shizy.entity.user;

import com.baomidou.mybatisplus.annotations.TableField;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author shizy
 * @since 2019-08-19
 */
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("user_id")
    private String userId;
    @TableField("user_account")
    private String userAccount;
    @TableField("user_name")
    private String userName;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "User{" +
        "userId=" + userId +
        ", userAccount=" + userAccount +
        ", userName=" + userName +
        "}";
    }
}
