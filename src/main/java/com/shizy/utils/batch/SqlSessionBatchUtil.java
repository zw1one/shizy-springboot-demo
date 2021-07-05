package com.midea.jr.gfp.gbdp.commons.util.sqlsession;

import com.shizy.utils.batch.SqlSessionBatchInsertMethod;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SqlSessionBatchUtil<PO> {

    private final Logger LOGGER = LoggerFactory.getLogger(SqlSessionBatchUtil.class);

    @Autowired
    SqlSessionFactory sqlSessionFactory;

    /**
     * 批量插入
     *
     * @param poList       待插入的数据List
     * @param insertMethod 插入数据的insert语句
     * @param batchSize    多少条数据提交一次
     * @return 执行的sql语句数量。
     */
    public Integer insertBatch(List<PO> poList, SqlSessionBatchInsertMethod insertMethod, Integer batchSize) {
        final Integer[] successTotal = {0};
        final Integer[] i = {0};
        try (SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);) {
            poList.forEach(po -> {
                //调用插入语句，统计返回值(生效的行)
                int result = 0;
                try {
//                  result = insertMethod.insert(po, sqlSession); // 这里返回有异常
                    insertMethod.insert(po, sqlSession);
                    result = 1;
                } catch (Exception e) {
                    LOGGER.error("插入单条数据异常。异常数据[{}], 异常原因[{}]", po.toString(), e.getMessage());
                }
                //统计list中遍历的数量，达到批次号后提交一次
                i[0]++;
                successTotal[0] += result;
                if ((i[0] % batchSize == 0) || i[0] == poList.size()) {
                    LOGGER.debug("批量插入List数据 提交中。 总共提交数据 {} 条，本次提交数据 {} 条", i[0], i[0] % batchSize == 0 ? batchSize : poList.size() % batchSize);
                    long a1 = System.currentTimeMillis();
                    try {
                        sqlSession.flushStatements();
                    } catch (Exception e) {
                        LOGGER.error("批量提交数据部分异常（不影响成功提交的数据）。异常原因[{}]", e.getMessage());
                    }
                    long a2 = System.currentTimeMillis();
                    LOGGER.debug("批量插入List数据 提交成功。 总共提交数据 {} 条，本次提交数据 {} 条，耗时 {} ms", i[0], i[0] % batchSize == 0 ? batchSize : poList.size() % batchSize, a2 - a1);
                }
            });
            sqlSession.commit();
        } catch (Exception e) {
            LOGGER.error("批量插入List数据异常。异常原因[{}]", e.getMessage());
        }

        //这个统计结果不能过滤报错的sql，只能统计执行的sql总数
        return successTotal[0];
    }

    /**
     * 多线程提交。提交效率高，数据库压力大。注意线程数不能设置过多
     */
    public Integer insertBatchParallel(List<PO> poList, SqlSessionBatchInsertMethod insertMethod, Integer batchSize) {
        // todo
        return null;
    }

}
