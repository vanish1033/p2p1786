package com.bjpowernode.p2p.controller;

import com.bjpowernode.p2p.common.constant.Constants;
import com.bjpowernode.p2p.service.LoanInfoService;
import com.bjpowernode.p2p.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

        return "/index";
    }

}
