package com.shizy.utils.batch;

import org.apache.ibatis.session.SqlSession;

public interface SqlSessionBatchInsertMethod<PO> {
    public Integer insert(PO po, SqlSession sqlSession);
}