package cc.mrbird.febs.manage.controller;


import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.manage.entity.ClassBindBookInfo;
import cc.mrbird.febs.manage.service.IClassBindBookInfoService;
import cc.mrbird.febs.manage.service.IPayRecordService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
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
@RequestMapping("/cos/class-bind-book-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ClassBindBookInfoController {

    private final IClassBindBookInfoService classBindBookInfoService;

    private final IPayRecordService payRecordService;

    /**
     * 分页获取班级绑定图书信息
     *
     * @param page              分页对象
     * @param classBindBookInfo 班级绑定图书信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<ClassBindBookInfo> page, ClassBindBookInfo classBindBookInfo) {
        return R.ok(classBindBookInfoService.selectPage(page, classBindBookInfo));
    }

    /**
     * 获取班级绑定图书详情
     *
     * @param id 班级绑定图书ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(classBindBookInfoService.getById(id));
    }

    /**
     * 获取班级绑定图书列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(classBindBookInfoService.list());
    }

    /**
     * 新增班级绑定图书信息
     *
     * @param classBindBookInfo 班级绑定图书信息
     * @return 结果
     */
    @PostMapping
    public R save(ClassBindBookInfo classBindBookInfo) throws FebsException {
        // 判断此班级是否已经绑定
        int count = classBindBookInfoService.count(Wrappers.<ClassBindBookInfo>lambdaQuery().eq(ClassBindBookInfo::getClassId, classBindBookInfo.getClassId())
                .eq(ClassBindBookInfo::getBookId, classBindBookInfo.getBookId()));
        if (count > 0) {
            throw new FebsException("此班级已经绑定该图书");
        }
        classBindBookInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        payRecordService.addClassBind(classBindBookInfo.getClassId(), classBindBookInfo.getBookId());
        return R.ok(classBindBookInfoService.save(classBindBookInfo));
    }

    /**
     * 修改班级绑定图书信息
     *
     * @param classBindBookInfo 班级绑定图书信息
     * @return 结果
     */
    @PutMapping
    public R edit(ClassBindBookInfo classBindBookInfo) {
        return R.ok(classBindBookInfoService.updateById(classBindBookInfo));
    }

    /**
     * 删除班级绑定图书信息
     *
     * @param ids ids
     * @return 班级绑定图书信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(classBindBookInfoService.removeByIds(ids));
    }
}
