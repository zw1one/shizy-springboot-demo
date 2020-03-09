package com.shizy.fxmx.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * 分析链接表
 * </p>
 *
 * @author shizy
 * @since 2020-03-09
 */
@TableName("LY_YY_CB_FXLJB")
public class FxljbPo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableField("ID")
    private String id;
    /**
     * 分析模型id
     */
    @TableField("FXMXID")
    private String fxmxid;
    /**
     * 页面url
     */
    @TableField("YM_URL")
    private String ymUrl;
    /**
     * 栏数
     */
    @TableField("LS")
    private Double ls;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFxmxid() {
        return fxmxid;
    }

    public void setFxmxid(String fxmxid) {
        this.fxmxid = fxmxid;
    }

    public String getYmUrl() {
        return ymUrl;
    }

    public void setYmUrl(String ymUrl) {
        this.ymUrl = ymUrl;
    }

    public Double getLs() {
        return ls;
    }

    public void setLs(Double ls) {
        this.ls = ls;
    }

    @Override
    public String toString() {
        return "FxljbPo{" +
        "id=" + id +
        ", fxmxid=" + fxmxid +
        ", ymUrl=" + ymUrl +
        ", ls=" + ls +
        "}";
    }
}
