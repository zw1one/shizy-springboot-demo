package com.shizy.fxmx.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * 业务分析模型
 * </p>
 *
 * @author shizy
 * @since 2020-03-09
 */
@TableName("LY_YY_CB_YWFXMX")
public class YwfxmxPo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 分析模型id
     */
    @TableField("FXMXID")
    private String fxmxid;
    /**
     * 分析模型名称
     */
    @TableField("FXMXMC")
    private String fxmxmc;
    /**
     * 是否系统自带
     */
    @TableField("FSXTZD")
    private String fsxtzd;
    /**
     * 分析模型路径
     */
    @TableField("FXMXLJ")
    private String fxmxlj;
    /**
     * 页面类型
     */
    @TableField("YMLX")
    private String ymlx;


    public String getFxmxid() {
        return fxmxid;
    }

    public void setFxmxid(String fxmxid) {
        this.fxmxid = fxmxid;
    }

    public String getFxmxmc() {
        return fxmxmc;
    }

    public void setFxmxmc(String fxmxmc) {
        this.fxmxmc = fxmxmc;
    }

    public String getFsxtzd() {
        return fsxtzd;
    }

    public void setFsxtzd(String fsxtzd) {
        this.fsxtzd = fsxtzd;
    }

    public String getFxmxlj() {
        return fxmxlj;
    }

    public void setFxmxlj(String fxmxlj) {
        this.fxmxlj = fxmxlj;
    }

    public String getYmlx() {
        return ymlx;
    }

    public void setYmlx(String ymlx) {
        this.ymlx = ymlx;
    }

    @Override
    public String toString() {
        return "YwfxmxPo{" +
        "fxmxid=" + fxmxid +
        ", fxmxmc=" + fxmxmc +
        ", fsxtzd=" + fsxtzd +
        ", fxmxlj=" + fxmxlj +
        ", ymlx=" + ymlx +
        "}";
    }
}
