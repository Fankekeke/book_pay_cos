package cc.mrbird.febs.manage.dao;

import cc.mrbird.febs.manage.entity.PayRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface PayRecordMapper extends BaseMapper<PayRecord> {

    /**
     * 分页获取支付记录信息
     *
     * @param page      分页对象
     * @param payRecord 支付记录信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectRecordPage(Page<PayRecord> page, @Param("payRecord") PayRecord payRecord);
}
