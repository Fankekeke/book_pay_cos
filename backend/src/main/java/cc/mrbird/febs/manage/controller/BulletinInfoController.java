package cc.mrbird.febs.manage.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.manage.entity.BulletinInfo;
import cc.mrbird.febs.manage.service.IBulletinInfoService;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
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
@RequestMapping("/manage/bulletin-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BulletinInfoController {

    private final IBulletinInfoService bulletinInfoService;

    /**
     * 分页获取公告信息
     *
     * @param page         分页对象
     * @param bulletinInfo 公告信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<BulletinInfo> page, BulletinInfo bulletinInfo) {
        return R.ok(bulletinInfoService.getBulletinByPage(page, bulletinInfo));
    }

    /**
     * 获取公告详情
     *
     * @param id 公告ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(bulletinInfoService.getById(id));
    }

    /**
     * 获取公告列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(bulletinInfoService.list());
    }

    /**
     * 新增公告信息
     *
     * @param bulletinInfo 公告信息
     * @return 结果
     */
    @PostMapping
    public R save(BulletinInfo bulletinInfo) {
        bulletinInfo.setTitle(StrUtil.cleanBlank(bulletinInfo.getTitle()));
        bulletinInfo.setDate(DateUtil.formatDateTime(new Date()));
        return R.ok(bulletinInfoService.save(bulletinInfo));
    }

    /**
     * 修改公告信息
     *
     * @param bulletinInfo 公告信息
     * @return 结果
     */
    @PutMapping
    public R edit(BulletinInfo bulletinInfo) {
        return R.ok(bulletinInfoService.updateById(bulletinInfo));
    }

    /**
     * 删除公告信息
     *
     * @param ids ids
     * @return 公告信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(bulletinInfoService.removeByIds(ids));
    }
}
