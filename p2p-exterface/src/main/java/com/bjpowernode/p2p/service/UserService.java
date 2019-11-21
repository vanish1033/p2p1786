package com.bjpowernode.p2p.service;

import com.bjpowernode.p2p.model.user.User;

/**
 * 用户Service
 *
 * @return
 */
public interface UserService {

    /**
     * 查询平台总人数
     *
     * @return
     */
    Long queryAllUserCount();

    /**
     * 根据手机号看看能不能查到这个人
     * @return
     */
    User queryUserByPhone();
}
