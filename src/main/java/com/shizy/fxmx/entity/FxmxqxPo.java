package com.shizy.fxmx.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * 分析模型权限
 * </p>
 *
 * @author shizy
 * @since 2020-03-09
 */
@TableName("LY_YY_CB_FXMXQX")
public class FxmxqxPo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 分析模型id
     */
    @TableField("FXMXID")
    private String fxmxid;
    /**
     * 授权对象类型
     */
    @TableField("SQDXLX")
    private String sqdxlx;
    /**
     * 对象id
     */
    @TableField("DXID")
    private String dxid;


    public String getFxmxid() {
        return fxmxid;
    }

    public void setFxmxid(String fxmxid) {
        this.fxmxid = fxmxid;
    }

    public String getSqdxlx() {
        return sqdxlx;
    }

    public void setSqdxlx(String sqdxlx) {
        this.sqdxlx = sqdxlx;
    }

    public String getDxid() {
        return dxid;
    }

    public void setDxid(String dxid) {
        this.dxid = dxid;
    }

    @Override
    public String toString() {
        return "FxmxqxPo{" +
        "fxmxid=" + fxmxid +
        ", sqdxlx=" + sqdxlx +
        ", dxid=" + dxid +
        "}";
    }
}
