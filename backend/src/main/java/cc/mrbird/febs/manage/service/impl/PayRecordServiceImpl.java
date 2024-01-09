package cc.mrbird.febs.manage.service.impl;

import cc.mrbird.febs.manage.entity.PayRecord;
import cc.mrbird.febs.manage.dao.PayRecordMapper;
import cc.mrbird.febs.manage.service.IPayRecordService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
@Service
public class PayRecordServiceImpl extends ServiceImpl<PayRecordMapper, PayRecord> implements IPayRecordService {

    /**
     * 分页获取支付记录信息
     *
     * @param page      分页对象
     * @param payRecord 支付记录信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectRecordPage(Page<PayRecord> page, PayRecord payRecord) {
        return baseMapper.selectRecordPage(page, payRecord);
    }
}
