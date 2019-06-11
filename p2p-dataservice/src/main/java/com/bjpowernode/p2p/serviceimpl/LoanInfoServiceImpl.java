package com.bjpowernode.p2p.serviceimpl;

import com.bjpowernode.p2p.common.constant.Constants;
import com.bjpowernode.p2p.dao.loan.LoanInfoMapper;
import com.bjpowernode.p2p.model.loan.LoanInfo;
import com.bjpowernode.p2p.model.vo.PaginatinoVO;
import com.bjpowernode.p2p.service.LoanInfoService;
import org.jboss.netty.util.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    /**
     * 根据不同的产品类型查出不同数量的数据
     *
     * @param paramMap
     * @return
     */
    @Override
    public List<LoanInfo> queryLoanInfoListByProductType(HashMap<String, Object> paramMap) {
        return loanInfoMapper.queryLoanInfoListByProductType(paramMap);
    }

    /**
     * 根据分页参数和产品类型进行查询
     *
     * @param paramMap
     * @return
     */
    @Override
    public PaginatinoVO<LoanInfo> queryLoanInfoByPage(Map<String, Object> paramMap) {
        PaginatinoVO<LoanInfo> infoPaginatinoVO = new PaginatinoVO<>();

        //查询总数据量
        Long total = loanInfoMapper.selectTotal(paramMap);
        infoPaginatinoVO.setTotal(total);

        //查询产品详细数据
        List<LoanInfo> loanInfoList = loanInfoMapper.queryLoanInfoListByProductType(paramMap);
        infoPaginatinoVO.setDataList(loanInfoList);
        return infoPaginatinoVO;
    }

    /**
     * 通过产品id查询产品详情
     *
     * @return
     */
    @Override
    public LoanInfo queryLoanInfoById(Integer id) {
        return loanInfoMapper.selectByPrimaryKey(id);
    }
}
