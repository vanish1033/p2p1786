package com.bjpowernode.p2p.controller;

import com.bjpowernode.p2p.common.constant.Constants;
import com.bjpowernode.p2p.model.loan.LoanInfo;
import com.bjpowernode.p2p.service.BidInfoService;
import com.bjpowernode.p2p.service.LoanInfoService;
import com.bjpowernode.p2p.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;

/**
 * 首页信息展示
 */
@Controller
public class IndexController {


    /**
     * 注入service层对象
     */
    @Autowired
    private LoanInfoService loanInfoService;
    @Autowired
    private UserService userService;
    @Autowired
    private BidInfoService bidInfoService;


    /**
     * 首页信息展示
     *
     * @param model
     * @return
     */
    @RequestMapping("/index")
    public String index(Model model) {

        //查询历史年化收益率
        Double historyAverage = loanInfoService.queryHistoryAverage();
        model.addAttribute(Constants.HISTORY_AVERAGE_RATE, historyAverage);

        //查询平台总人数
        Long allUserCount = userService.queryAllUserCount();
        model.addAttribute(Constants.ALL_USER_COUNT, allUserCount);

        //查询平台交易总额
        Double allBidMoney = bidInfoService.queryAllAbidMoney();
        model.addAttribute(Constants.ALL_BID_MONEY, allBidMoney);

        /**
         * 根据不同的产品类型查出不同数量的数据
         */
        /**
         * 新手宝查1个
         */
        //创建一个map装参数
        HashMap<String, Object> paramMap = new HashMap<>();

        //页码
        paramMap.put("currentPage", 0);

        //每页显示一个
        paramMap.put("pageSize", 1);

        //查的产品类别(新手宝 = 0)
        paramMap.put("productType", Constants.PRODUCT_TYPE_X);

        //调用Service层
        List<LoanInfo> xLoanInfoList = loanInfoService.queryLoanInfoListByProductType(paramMap);

        /**
         * 优选查4个
         */
        //页码
        paramMap.put("currentPage", 0);

        //每页显示一个
        paramMap.put("pageSize", 4);

        //查的产品类别(优选 = 1)
        paramMap.put("productType", Constants.PRODUCT_TYPE_U);

        //调用Service层
        List<LoanInfo> uLoanInfoList = loanInfoService.queryLoanInfoListByProductType(paramMap);

        /**
         * 散标查8个
         */
        //页码
        paramMap.put("currentPage", 0);

        //每页显示一个
        paramMap.put("pageSize", 8);

        //查的产品类别(散标 = 2)
        paramMap.put("productType", Constants.PRODUCT_TYPE_S);

        //调用Service层
        List<LoanInfo> sLoanInfoList = loanInfoService.queryLoanInfoListByProductType(paramMap);

        model.addAttribute("xLoanInfoList", xLoanInfoList);
        model.addAttribute("uLoanInfoList", uLoanInfoList);
        model.addAttribute("sLoanInfoList", sLoanInfoList);

        return "/index";
    }

}
