package cc.mrbird.febs.manage.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.manage.entity.StudentInfo;
import cc.mrbird.febs.manage.service.IStudentInfoService;
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
@RequestMapping("/manage/student-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StudentInfoController {

    private final IStudentInfoService studentInfoService;

    /**
     * 分页获取学生信息
     *
     * @param page        分页对象
     * @param studentInfo 学生信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<StudentInfo> page, StudentInfo studentInfo) {
        return R.ok(studentInfoService.selectStudentPage(page, studentInfo));
    }

    /**
     * 获取学生详情
     *
     * @param id 学生ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(studentInfoService.getById(id));
    }

    /**
     * 获取学生列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(studentInfoService.list());
    }

    /**
     * 新增学生信息
     *
     * @param studentInfo 学生信息
     * @return 结果
     */
    @PostMapping
    public R save(StudentInfo studentInfo) {
        studentInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(studentInfoService.save(studentInfo));
    }

    /**
     * 修改学生信息
     *
     * @param studentInfo 学生信息
     * @return 结果
     */
    @PutMapping
    public R edit(StudentInfo studentInfo) {
        return R.ok(studentInfoService.updateById(studentInfo));
    }

    /**
     * 删除学生信息
     *
     * @param ids ids
     * @return 学生信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(studentInfoService.removeByIds(ids));
    }
}
