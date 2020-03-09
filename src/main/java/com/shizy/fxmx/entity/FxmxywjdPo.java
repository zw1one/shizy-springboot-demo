package com.shizy.fxmx.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * 分析模型业务节点
 * </p>
 *
 * @author shizy
 * @since 2020-03-09
 */
@TableName("LY_YY_CB_FXMXYWJD")
public class FxmxywjdPo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 分析模型id
     */
    @TableField("FXMDID")
    private String fxmdid;
    /**
     * 流程节点id
     */
    @TableField("RDSJ")
    private String rdsj;


    public String getFxmdid() {
        return fxmdid;
    }

    public void setFxmdid(String fxmdid) {
        this.fxmdid = fxmdid;
    }

    public String getRdsj() {
        return rdsj;
    }

    public void setRdsj(String rdsj) {
        this.rdsj = rdsj;
    }

    @Override
    public String toString() {
        return "FxmxywjdPo{" +
        "fxmdid=" + fxmdid +
        ", rdsj=" + rdsj +
        "}";
    }
}
