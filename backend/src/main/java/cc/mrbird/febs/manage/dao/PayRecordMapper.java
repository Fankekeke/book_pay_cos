package cc.mrbird.febs.manage.dao;

import cc.mrbird.febs.manage.entity.PayRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;

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

    /**
     * 查询总收益
     *
     * @return 结果
     */
    BigDecimal selectOrderPrice();

    /**
     * 获取本月订单信息
     *
     * @return 结果
     */
    List<PayRecord> selectOrderByMonth();

    /**
     * 获取本年订单信息
     *
     * @return 结果
     */
    List<PayRecord> selectOrderByYear();

    /**
     * 十天内订单数量统计
     *
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectOrderNumWithinDays(@Param("pharmacyId") Integer pharmacyId);

    /**
     * 十天内订单收益统计
     *
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectOrderPriceWithinDays(@Param("pharmacyId") Integer pharmacyId);

}
