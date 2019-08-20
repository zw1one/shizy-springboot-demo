package com.shizy.common.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

//@JsonInclude(JsonInclude.Include.NON_NULL)//在返回的json中，去掉为null的字段
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonResult implements Serializable {

    private static final long serialVersionUID = 1L;

    private Object data;

    private int code = 200;

    private Object msg;

    public static JsonResult success(Object data) {
        return new JsonResult(data, 200, null);
    }

    public static JsonResult fail(Object msg) {
        return new JsonResult(null, 500, msg);
    }

    public static JsonResult fail() {
        return new JsonResult(null, 500, "server process error!");
    }

}
