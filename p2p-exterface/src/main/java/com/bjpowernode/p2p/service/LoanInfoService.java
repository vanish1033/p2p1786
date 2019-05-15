package com.bjpowernode.p2p.service;

import com.bjpowernode.p2p.model.loan.LoanInfo;

import java.util.HashMap;
import java.util.List;

public interface LoanInfoService {
    /**
     * 查询历史年化收益率
     *
     * @return
     */
    Double queryHistoryAverage();

    /**
     * 根据不同的产品类型查出不同数量的数据
     */
    List<LoanInfo> queryLoanInfoListByProductType(HashMap<String, Object> paramMap);
}
