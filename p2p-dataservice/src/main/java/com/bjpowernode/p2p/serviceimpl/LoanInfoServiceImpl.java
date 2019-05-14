package com.bjpowernode.p2p.serviceimpl;

import com.bjpowernode.p2p.common.constant.Constants;
import com.bjpowernode.p2p.dao.loan.LoanInfoMapper;
import com.bjpowernode.p2p.service.LoanInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

@Service("loanInfoServiceImpl")
public class LoanInfoServiceImpl implements LoanInfoService {

    /**
     * 注入Dao层对象
     */
    @Autowired
    private LoanInfoMapper loanInfoMapper;
    /**
     * 注入redis模板
     */
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;


    /**
     * 查询历史年化收益率
     *
     * @return
     */
    @Override
    public Double queryHistoryAverage() {

        //修改key的序列化方式
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //先从缓存中查找数据
        Double historyAverage = (Double) redisTemplate.opsForValue().get(Constants.HISTORY_AVERAGE_RATE);
        //如果缓存里没有，就从数据库里查，并放到缓存里
        if (historyAverage == null) {
            synchronized (this) {
                if (historyAverage == null) {
                    //从数据库里查
                    historyAverage = loanInfoMapper.queryHistoryAverage();
                    //放到缓存里
                    redisTemplate.opsForValue().set(Constants.HISTORY_AVERAGE_RATE, historyAverage);
                }
            }
        }
        return historyAverage;
    }
}
