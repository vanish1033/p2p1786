package com.bjpowernode.p2p.serviceimpl;

import com.bjpowernode.p2p.common.constant.Constants;
import com.bjpowernode.p2p.dao.user.UserMapper;
import com.bjpowernode.p2p.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService {
    /**
     * 注入Dao层对象
     */
    @Autowired
    UserMapper userMapper;

    /**
     * 注入redis模板
     */
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;


    /**
     * 查询平台总人数
     *
     * @return
     */
    @Override
    public Long queryAllUserCount() {

        //修改key的序列化方式
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        //获取一个绑定key的操作对象
        BoundValueOperations<Object, Object> ops = redisTemplate.boundValueOps(Constants.ALL_USER_COUNT);

        //先从缓存中获取平台总人数
        Long allUserCount = (Long) ops.get();

        //如果缓存里没有，就从数据库里查，并放到缓存里
        if (allUserCount == null) {
            synchronized (this) {
                if (allUserCount == null) {
                    //从数据库里查
                    allUserCount = userMapper.queryAllUserCount();
                    //放到缓存里
                    ops.set(allUserCount, 10, TimeUnit.MINUTES);
                }
            }
        }
        return allUserCount;
    }
}
