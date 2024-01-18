package cc.mrbird.febs.manage.controller;


import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.manage.entity.PayRecord;
import cc.mrbird.febs.manage.service.IPayRecordService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author FanK
 */
@RestController
@RequestMapping("/cos/pay-record")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PayRecordController {

    private final IPayRecordService payRecordService;

    /**
     * 分页获取支付记录信息
     *
     * @param page      分页对象
     * @param payRecord 支付记录信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<PayRecord> page, PayRecord payRecord) {
        return R.ok(payRecordService.selectRecordPage(page, payRecord));
    }

    /**
     * 订单付款
     *
     * @param orderCode 订单编号
     * @return 结果
     */
    @GetMapping("/payment")
    public R orderPaymentPlatform(@RequestParam("orderCode") String orderCode) {
        payRecordService.orderPaymentPlatform(orderCode);
        return R.ok(true);
    }

    /**
     * 统计数据
     *
     * @return 结果
     */
    @GetMapping("/home/data")
    public R selectHomeData() {
        return R.ok(payRecordService.homeData());
    }

    /**
     * 获取学生统计数据
     *
     * @param userId 用户ID
     * @return 结果
     */
    @GetMapping("/home/data/student/{userId}")
    public R selectHomeDataByStudent(@PathVariable("userId") Integer userId) throws FebsException {
        return R.ok(payRecordService.selectHomeDataByStudent(userId));
    }

    /**
     * 获取支付记录详情
     *
     * @param id 支付记录ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(payRecordService.getById(id));
    }

    /**
     * 获取支付记录详情
     *
     * @param id 支付记录ID
     * @return 结果
     */
    @GetMapping("/recordDetail/{id}")
    public R recordDetail(@PathVariable("id") Integer id) {
        return R.ok(payRecordService.selectRecordDetail(id));
    }

    /**
     * 获取支付记录列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(payRecordService.list());
    }

    /**
     * 新增支付记录信息
     *
     * @param payRecord 支付记录信息
     * @return 结果
     */
    @PostMapping
    public R save(PayRecord payRecord) {
        payRecord.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(payRecordService.save(payRecord));
    }

    /**
     * 修改支付记录信息
     *
     * @param payRecord 支付记录信息
     * @return 结果
     */
    @PutMapping
    public R edit(PayRecord payRecord) {
        return R.ok(payRecordService.updateById(payRecord));
    }

    /**
     * 删除支付记录信息
     *
     * @param ids ids
     * @return 支付记录信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(payRecordService.removeByIds(ids));
    }
}
