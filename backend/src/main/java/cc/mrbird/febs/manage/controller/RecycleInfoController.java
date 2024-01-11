package cc.mrbird.febs.manage.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.manage.entity.RecycleInfo;
import cc.mrbird.febs.manage.service.IRecycleInfoService;
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
@RequestMapping("/cos/recycle-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RecycleInfoController {

    private final IRecycleInfoService recycleInfoService;

    /**
     * 分页获取图书回收信息
     *
     * @param page        分页对象
     * @param recycleInfo 图书回收信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<RecycleInfo> page, RecycleInfo recycleInfo) {
        return R.ok(recycleInfoService.selectRecyclePage(page, recycleInfo));
    }

    /**
     * 获取图书回收详情
     *
     * @param id 图书回收ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(recycleInfoService.getById(id));
    }

    /**
     * 获取图书回收列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(recycleInfoService.list());
    }

    /**
     * 新增图书回收信息
     *
     * @param recycleInfo 图书回收信息
     * @return 结果
     */
    @PostMapping
    public R save(RecycleInfo recycleInfo) {
        recycleInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(recycleInfoService.save(recycleInfo));
    }

    /**
     * 修改图书回收信息
     *
     * @param recycleInfo 图书回收信息
     * @return 结果
     */
    @PutMapping
    public R edit(RecycleInfo recycleInfo) {
        return R.ok(recycleInfoService.updateById(recycleInfo));
    }

    /**
     * 删除图书回收信息
     *
     * @param ids ids
     * @return 图书回收信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(recycleInfoService.removeByIds(ids));
    }
}
