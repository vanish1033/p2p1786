package com.bjpowernode.p2p.service;

import com.bjpowernode.p2p.model.loan.BidInfo;

import java.util.List;

/**
 * 交易信息Service
 */
public interface BidInfoService {
    /**
     * 查询平台所有的交易金额
     *
     * @return
     */
    Double queryAllAbidMoney();

    /**
     * 查询产品投资记录
     *
     * @return
     * @param id
     */
    List<BidInfo> queryBidInfoByLoanId(Integer id);
}
