package com.shizy.fxmx.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * 分析模型热点时间
 * </p>
 *
 * @author shizy
 * @since 2020-03-09
 */
@TableName("LY_YY_CB_FXMXRDSJ")
public class FxmxrdsjPo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 分析模型id
     */
    @TableField("FXMXID")
    private String fxmxid;
    /**
     * 热点时间
     */
    @TableField("RDSJ")
    private String rdsj;


    public String getFxmxid() {
        return fxmxid;
    }

    public void setFxmxid(String fxmxid) {
        this.fxmxid = fxmxid;
    }

    public String getRdsj() {
        return rdsj;
    }

    public void setRdsj(String rdsj) {
        this.rdsj = rdsj;
    }

    @Override
    public String toString() {
        return "FxmxrdsjPo{" +
        "fxmxid=" + fxmxid +
        ", rdsj=" + rdsj +
        "}";
    }
}
