package com.bjpowernode.p2p.service;

import com.bjpowernode.p2p.model.loan.LoanInfo;
import com.bjpowernode.p2p.model.vo.PaginatinoVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * 根据分页参数和产品类型进行查询
     *
     * @param paramMap
     * @return
     */
    PaginatinoVO<LoanInfo> queryLoanInfoByPage(Map<String, Object> paramMap);

    /**
     * 查询产品详情
     *
     * @return
     */
    LoanInfo queryLoanInfoById(Integer id);
}
