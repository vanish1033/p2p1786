package com.bjpowernode.p2p.serviceimpl;

import com.bjpowernode.p2p.common.constant.Constants;
import com.bjpowernode.p2p.dao.loan.BidInfoMapper;
import com.bjpowernode.p2p.service.BidInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 交易信息Service实现类
 */
@Service("bidInfoServiceImpl")
public class BidInfoServiceImpl implements BidInfoService {
    /**
     * 自动注入Dao层对象
     */
    @Autowired
    private BidInfoMapper bidInfoMapper;

    /**
     * 注入redis模板
     */
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    /**
     * 查询平台交易总金额
     *
     * @return
     */
    @Override
    public Double queryAllAbidMoney() {
        //修改key的序列化方式
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //绑定key
        BoundValueOperations<Object, Object> ops = redisTemplate.boundValueOps(Constants.ALL_BID_MONEY);
        //到redis中取值
        Double allAbidMoney = (Double) ops.get();
        //如果redis中没有值，就到数据库中取
        if (allAbidMoney == null) {
            synchronized (this) {
                if (allAbidMoney == null) {
                    allAbidMoney = bidInfoMapper.queryAllAbidMoney();
                    //把值放到redis中
                    ops.set(allAbidMoney, 15, TimeUnit.MINUTES);
                }
            }
        }
        return allAbidMoney;
    }
}
