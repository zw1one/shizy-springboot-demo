package com.shizy.utils.query;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryUtilTest<PO, DTO> {

    @Test
    public void getEntityConditionTest() throws Exception {
        Object z = QueryUtil.getEntityCondition(
                new QueryDemoDTO("name", "address",
                        null, 15, 20,
                        null, "2018-01-20", "2018-03-20"),
                new QueryDemoPO()
        );
        System.out.println();
    }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class QueryDemoPO {
    private String name;
    private String address;
    private Integer age;
    private String brithday;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class QueryDemoDTO {

    @QueryParam(eq = true)
    private String name;

    @QueryParam(like = true)
    private String address;

    @QueryParam(eq = true)
    private Integer age;

    @QueryParam(column = "age", betweenStart = true)
    private Integer ageStart;

    @QueryParam(column = "age", betweenEnd = true)
    private Integer ageEnd;

    @QueryParam(eq = true)
    private String brithday;

    @QueryParam(column = "brithday", betweenStart = true)
    private String brithdayStart;

    @QueryParam(column = "brithday", betweenEnd = true)
    private String brithdayEnd;

}
















