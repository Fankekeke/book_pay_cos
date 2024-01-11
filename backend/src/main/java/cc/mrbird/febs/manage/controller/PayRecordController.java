package cc.mrbird.febs.manage.controller;


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