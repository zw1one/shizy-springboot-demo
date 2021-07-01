package com.shizy.utils.batch;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.shizy.utils.bean.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.List;

@Component
public class JdbcBatchUtil {

    @Autowired
    public JdbcTemplate jdbcTemplate;

    public <PO> int[][] insertBatch(List<PO> data) {
        return insertBatch(data, 1000);
    }

    /**
     * 根据list的实体类类型批量插入
     * 实体类需使用mybatis-plus注解。若报错，请查看是否有字段注解
     *
     * @param batchSize 提交一个批次的数据条数
     */
    public <PO> int[][] insertBatch(List<PO> data, int batchSize) {

        if (data == null || data.size() == 0) {
            return null;
        }


        Class poClass = data.get(0).getClass();

        List<Field> fields = BeanUtil.getFields(poClass);
        String sql = getInsertSql(poClass, fields);

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

    private String getInsertSql(Class poClass, List<Field> fields) {

        String tableName = getPoTableName(poClass);
        String columns = getPoColumns(poClass, fields);
        String valueColumns = getValueColumns(poClass, fields);

        String sql = "INSERT INTO " + tableName + " (" + columns + ") VALUES (" + valueColumns + ")";
        return sql;
    }

    private String getPoTableName(Class clazz) {
        TableName tableName = (TableName) clazz.getAnnotation(TableName.class);
        if (tableName == null) {
            return null;
        }
        return tableName.value();
    }

    private String getPoColumns(Class clazz, List<Field> fields) {
        StringBuilder str = new StringBuilder();
        for (Field field : fields) {
            String columns = null;
            TableId tableId = field.getAnnotation(TableId.class);
            if (tableId != null) {
                columns = tableId.value();
            }
            TableField tableField = field.getAnnotation(TableField.class);
            if (tableField != null) {
                columns = tableField.value();
            }

            if (columns == null) {
                continue;
            }
            str.append(columns).append(", ");
        }
        String rtnStr = str.toString().substring(0, str.length() - ", ".length());
        return rtnStr;
    }

    private String getValueColumns(Class clazz, List<Field> fields) {
        StringBuilder str = new StringBuilder();
        for (Field field : fields) {
            str.append("?").append(", ");
        }
        String rtnStr = str.toString().substring(0, str.length() - ", ".length());
        return rtnStr;
    }

    /************************************************************/

    public <PO> int[][] deleteBatch(List<PO> data) {
        return deleteBatch(data, 10000);
    }

    /**
     * 根据list的实体类类型批量删除
     * 实体类需使用mybatis-plus注解
     * <p>
     * 这种删除比起truncate慢太多了
     *
     * @param batchSize 提交一个批次的数据条数
     */
    public <PO> int[][] deleteBatch(List<PO> data, int batchSize) {

        if (data == null || data.size() == 0) {
            return null;
        }

        Class poClass = data.get(0).getClass();

        List<Field> fields = BeanUtil.getFields(poClass);
        String sql = getDeleteSql(poClass, fields, data.get(0));

        int[][] updateCounts = jdbcTemplate.batchUpdate(
                sql,
                data,
                batchSize,
                (ps, po) -> {
                    int i = 1;
                    for (Field field : fields) {
                        Object fieldValue = BeanUtil.get(po, field.getName(), field.getType());
                        if (fieldValue == null) {
                            continue;
                        }
                        ps.setObject(i, fieldValue);
                        i++;
                    }
                });

        return updateCounts;
    }

    private <PO> String getDeleteSql(Class poClass, List<Field> fields, PO po) {

        String tableName = getPoTableName(poClass);
        String[] columns = getPoColumnsIgnoreNull(poClass, fields, po).split(", ");

        StringBuilder sql = new StringBuilder("DELETE FROM " + tableName + " WHERE 1=1 ");
        for (String column : columns) {
            sql.append("AND ").append(column).append(" = ").append("?").append(" ");
        }

        return sql.toString();
    }

    private <PO> String getPoColumnsIgnoreNull(Class clazz, List<Field> fields, PO po) {
        StringBuilder str = new StringBuilder();
        for (Field field : fields) {
            String columns = null;
            TableId tableId = field.getAnnotation(TableId.class);
            if (tableId != null) {
                columns = tableId.value();
            }
            TableField tableField = field.getAnnotation(TableField.class);
            if (tableField != null) {
                columns = tableField.value();
            }

            if (columns == null) {
                continue;
            }
            Object fieldValue = BeanUtil.get(po, field.getName(), field.getType());
            if (fieldValue == null) {
                continue;
            }

            str.append(columns).append(", ");
        }
        String rtnStr = str.toString().substring(0, str.length() - ", ".length());
        return rtnStr;
    }
}




















