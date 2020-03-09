package com.shizy.fxmx.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * 算法表
 * </p>
 *
 * @author shizy
 * @since 2020-03-09
 */
@TableName("LY_YY_CB_SFB")
public class SfbPo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 算法id
     */
    @TableField("SFID")
    private String sfid;
    /**
     * 仪表盘id
     */
    @TableField("YBPID")
    private String ybpid;
    /**
     * 算法内容
     */
    @TableField("SFNR")
    private String sfnr;


    public String getSfid() {
        return sfid;
    }

    public void setSfid(String sfid) {
        this.sfid = sfid;
    }

    public String getYbpid() {
        return ybpid;
    }

    public void setYbpid(String ybpid) {
        this.ybpid = ybpid;
    }

    public String getSfnr() {
        return sfnr;
    }

    public void setSfnr(String sfnr) {
        this.sfnr = sfnr;
    }

    @Override
    public String toString() {
        return "SfbPo{" +
        "sfid=" + sfid +
        ", ybpid=" + ybpid +
        ", sfnr=" + sfnr +
        "}";
    }
}
