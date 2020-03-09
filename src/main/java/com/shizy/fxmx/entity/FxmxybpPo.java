package com.shizy.fxmx.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * 分析模型仪表盘
 * </p>
 *
 * @author shizy
 * @since 2020-03-09
 */
@TableName("LY_YY_CB_FXMXYBP")
public class FxmxybpPo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 仪表盘id
     */
    @TableField("YBPID")
    private String ybpid;
    /**
     * 分析类型
     */
    @TableField("FXLX")
    private String fxlx;
    /**
     * 分析模型id
     */
    @TableField("FXMXID")
    private String fxmxid;
    /**
     * 图标类型
     */
    @TableField("TBLX")
    private String tblx;
    /**
     * 栏数
     */
    @TableField("LS")
    private Double ls;


    public String getYbpid() {
        return ybpid;
    }

    public void setYbpid(String ybpid) {
        this.ybpid = ybpid;
    }

    public String getFxlx() {
        return fxlx;
    }

    public void setFxlx(String fxlx) {
        this.fxlx = fxlx;
    }

    public String getFxmxid() {
        return fxmxid;
    }

    public void setFxmxid(String fxmxid) {
        this.fxmxid = fxmxid;
    }

    public String getTblx() {
        return tblx;
    }

    public void setTblx(String tblx) {
        this.tblx = tblx;
    }

    public Double getLs() {
        return ls;
    }

    public void setLs(Double ls) {
        this.ls = ls;
    }

    @Override
    public String toString() {
        return "FxmxybpPo{" +
        "ybpid=" + ybpid +
        ", fxlx=" + fxlx +
        ", fxmxid=" + fxmxid +
        ", tblx=" + tblx +
        ", ls=" + ls +
        "}";
    }
}
