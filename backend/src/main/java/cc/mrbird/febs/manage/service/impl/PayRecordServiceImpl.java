package cc.mrbird.febs.manage.service.impl;

import cc.mrbird.febs.manage.dao.BookInfoMapper;
import cc.mrbird.febs.manage.dao.ClassInfoMapper;
import cc.mrbird.febs.manage.dao.StudentInfoMapper;
import cc.mrbird.febs.manage.entity.*;
import cc.mrbird.febs.manage.dao.PayRecordMapper;
import cc.mrbird.febs.manage.service.IBulletinInfoService;
import cc.mrbird.febs.manage.service.IPayRecordService;
import cc.mrbird.febs.manage.service.IStudentInfoService;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PayRecordServiceImpl extends ServiceImpl<PayRecordMapper, PayRecord> implements IPayRecordService {

    private final StudentInfoMapper studentInfoMapper;

    private final ClassInfoMapper classInfoMapper;

    private final BookInfoMapper bookInfoMapper;

    private final IBulletinInfoService bulletinInfoService;

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

    /**
     * 获取支付记录详情
     *
     * @param id 支付记录ID
     * @return 结果
     */
    @Override
    public LinkedHashMap<String, Object> selectRecordDetail(Integer id) {
        // 返回信息
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>() {
            {
                put("payRecord", null);
                put("student", null);
                put("book", null);
                put("class", null);
            }
        };
        // 支付记录详情
        PayRecord payRecord = this.getById(id);
        result.put("payRecord", payRecord);

        StudentInfo studentInfo = studentInfoMapper.selectById(payRecord.getStudentId());
        result.put("student", studentInfo);

        BookInfo bookInfo = bookInfoMapper.selectById(payRecord.getBookId());
        result.put("book", bookInfo);

        ClassInfo classInfo = classInfoMapper.selectById(studentInfo.getClassId());
        result.put("class", classInfo);
        return result;
    }

    /**
     * 订单付款
     *
     * @param orderCode 订单编号
     */
    @Override
    public void orderPaymentPlatform(String orderCode) {
        PayRecord payRecord = this.getOne(Wrappers.<PayRecord>lambdaQuery().eq(PayRecord::getCode, orderCode));
        // 更新缴费状态
        payRecord.setStatus("1");
        this.updateById(payRecord);
    }

    /**
     * 主页数据
     *
     * @return 结果
     */
    @Override
    public LinkedHashMap<String, Object> homeData() {
        LinkedHashMap<String, Object> result = new LinkedHashMap<>();
        // 总缴费数量
        result.put("orderCode", this.count());
        // 总收益
        result.put("orderPrice", baseMapper.selectOrderPrice());
        // 学生数量
        result.put("pharmacyNum", studentInfoMapper.selectCount(Wrappers.<StudentInfo>lambdaQuery()));
        // 图书数量
        result.put("staffNum", bookInfoMapper.selectCount(Wrappers.<BookInfo>lambdaQuery()));

        // 本月订单数量
        List<PayRecord> orderList = baseMapper.selectOrderByMonth();
        result.put("monthOrderNum", CollectionUtil.isEmpty(orderList) ? 0 : orderList.size());
        BigDecimal orderPrice = orderList.stream().map(PayRecord::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        // 获取本月收益
        result.put("monthOrderPrice", orderPrice);

        // 本年订单数量
        List<PayRecord> orderYearList = baseMapper.selectOrderByYear();
        result.put("yearOrderNum", CollectionUtil.isEmpty(orderYearList) ? 0 : orderYearList.size());
        // 本年总收益
        BigDecimal orderYearPrice = orderYearList.stream().map(PayRecord::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        result.put("yearOrderPrice", orderYearPrice);

        // 公告信息
        result.put("bulletin", bulletinInfoService.list(Wrappers.<BulletinInfo>lambdaQuery().eq(BulletinInfo::getRackUp, 1)));

        // 近十天内订单统计
        result.put("orderNumWithinDays", baseMapper.selectOrderNumWithinDays(null));
        // 近十天内收益统计
        result.put("orderPriceWithinDays", baseMapper.selectOrderPriceWithinDays(null));
        return result;
    }
}
