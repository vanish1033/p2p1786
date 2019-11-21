package com.bjpowernode.p2p.controller;

import com.bjpowernode.p2p.common.constant.Constants;
import com.bjpowernode.p2p.model.user.User;
import com.bjpowernode.p2p.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

/**
 * 用户相关处理
 */
@Controller
public class UserController {


    @Autowired
    private UserService userService;

    /**
     * 校验手机号是否存在
     *
     * @return
     */
    @RequestMapping("/loan/checkPhone")
    @ResponseBody
    public Object checkPhone(@RequestParam(value = "phone", required = true) String Phone) {
        User user = userService.queryUserByPhone();
        //建立一个map把结果封装到里面
        HashMap<String, Object> resultMap = new HashMap<>();
        //若查不到人，说明之前没注册过，就同意注册
        if (user != null) {
            resultMap.put(Constants.ERROR_MESSAGE, "该手机号已注册，请直接登录！");
        } else {
            resultMap.put(Constants.ERROR_MESSAGE, Constants.OK);
        }
        return resultMap;
    }

}
