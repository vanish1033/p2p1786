package com.bjpowernode.p2p.controller;

import com.bjpowernode.p2p.common.constant.Constants;
import com.bjpowernode.p2p.service.LoanInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
/**
 * 首页信息展示
 */
public class IndexController {


    /**
     * 注入service层对象
     */
    @Autowired
    private LoanInfoService loanInfoService;


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
        //将查出来的历史年化收益率放到model里
        model.addAttribute(Constants.HISTORY_AVERAGE_RATE, historyAverage);
        System.out.println("我爱你");
        return "/index";
    }


}
