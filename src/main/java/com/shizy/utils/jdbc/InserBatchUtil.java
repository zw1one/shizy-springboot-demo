package com.shizy.utils.jdbc;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.shizy.entity.user.UserPo;
import com.shizy.utils.bean.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Component
public class InserBatchUtil {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public <PO> int[][] insertBatch(List<PO> data) {
        return insertBatch(data, 1000);
    }

    public <PO> int[][] insertBatch(List<PO> data, int batchSize) {

        List<Field> fields = getFields(data.get(0));
        String sql = getInsertSql(data.get(0));

        int[][] updateCounts = jdbcTemplate.batchUpdate(
                sql,
                data,
                batchSize,
                (ps, po) -> {
                    int i = 1;
                    for (Field field : fields) {
                        Object fieldValue = BeanUtil.get(po, field.getName(), field.getType());
                        ps.setObject(i, fieldValue);
                        i++;
                    }
                });

        return updateCounts;
    }

    private <PO> String getInsertSql(PO po) {
        Class poClass = po.getClass();

        String tableName = "user";
        String columns = "user_id, user_account, user_name";
        String valueColumns = "?, ?, ?";

        String sql = "INSERT INTO " + tableName + " (" + columns + ") VALUES (" + valueColumns + ")";

        return sql;
    }

    private <PO> List getFields(PO po) {
        List<Field> fieldNames = new ArrayList();

        Class poClass = po.getClass();
        for (Field field : poClass.getDeclaredFields()) {
            field.setAccessible(true);

            if (field.getAnnotation(TableId.class) == null && field.getAnnotation(TableField.class) == null) {
                continue;
            }
            fieldNames.add(field);
        }
        return fieldNames;
    }


}




















