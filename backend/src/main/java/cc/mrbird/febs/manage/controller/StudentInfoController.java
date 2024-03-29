package cc.mrbird.febs.manage.controller;


import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.utils.FileDownloadUtils;
import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.manage.entity.StudentInfo;
import cc.mrbird.febs.manage.service.IPayRecordService;
import cc.mrbird.febs.manage.service.IStudentInfoService;
import cc.mrbird.febs.system.dao.UserMapper;
import cc.mrbird.febs.system.service.UserService;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * @author FanK
 */
@RestController
@RequestMapping("/cos/student-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StudentInfoController {

    private final IStudentInfoService studentInfoService;

    private final IPayRecordService payRecordService;

    private final UserService userService;

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
     * 下载模板
     */
    @GetMapping("/template")
    public void downloadTemplate(HttpServletResponse response) {
        try {
            FileDownloadUtils.downloadTemplate(response, "基础数据.xlsx");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 导入学生信息列表
     */
    @PostMapping("/import")
    public R importExcel(@RequestParam("file") MultipartFile file) {
        try {
            String errorMsg = studentInfoService.importExcel(file);
            if (StrUtil.isNotEmpty(errorMsg)) {
                return R.error(errorMsg);
            }
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.error("导入异常");
    }

    /**
     * 根据学生获取已缴费图书
     *
     * @param userId 用户ID
     * @return 结果
     */
    @GetMapping("/book/list/{userId}")
    public R selectBookByStudent(@PathVariable("userId") Integer userId) {
        return R.ok(studentInfoService.selectBookByStudentId(userId));
    }

    /**
     * 根据学生获取可用图书
     *
     * @param userId 用户ID
     * @return 结果
     */
    @GetMapping("/use/book/list/{userId}")
    public R selectUseBookByStudent(@PathVariable("userId") Integer userId) {
        return R.ok(studentInfoService.selectUseBookByStudent(userId));
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
     * 获取学生详情
     *
     * @param userId 学生ID
     * @return 结果
     */
    @GetMapping("/detail/{id}")
    public R selectBookDetail(@PathVariable("id") Integer userId) {
        return R.ok(studentInfoService.selectBookDetail(userId));
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
     * 根据班级ID获取学生信息
     *
     * @param classId 班级ID
     * @return 结果
     */
    @GetMapping("/selectStudentByClass/{classId}")
    public R selectStudentByClass(@PathVariable("classId") Integer classId) {
        return R.ok(studentInfoService.list(Wrappers.<StudentInfo>lambdaQuery().eq(StudentInfo::getClassId, classId)));
    }

    /**
     * 获取学生姓名
     *
     * @param userId 用户ID
     * @return 结果
     */
    @GetMapping("/username/{userId}")
    public R selectNameByUser(@PathVariable("userId") Integer userId) {
        StudentInfo studentInfo = studentInfoService.getOne(Wrappers.<StudentInfo>lambdaQuery().eq(StudentInfo::getUserId, userId));
        if (studentInfo == null) {
            return R.ok("");
        } else {
            return R.ok(studentInfo.getStudentName());
        }
    }

    /**
     * 新增学生信息
     *
     * @param studentInfo 学生信息
     * @return 结果
     */
    @PostMapping
    public R save(StudentInfo studentInfo) throws Exception {
        List<StudentInfo> studentList = studentInfoService.list(Wrappers.<StudentInfo>lambdaQuery().eq(StudentInfo::getCode, studentInfo.getCode()));
        if (CollectionUtil.isNotEmpty(studentList)) {
            throw new FebsException("学号不能重复");
        }

        studentInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        userService.registUser(studentInfo.getCode(), "1234qwer", studentInfo);

        payRecordService.addStudentBind(studentInfo.getId(), studentInfo.getClassId());

        // 添加登录用户
        return R.ok(true);
    }

    /**
     * 修改学生信息
     *
     * @param studentInfo 学生信息
     * @return 结果
     */
    @PutMapping
    public R edit(StudentInfo studentInfo) throws FebsException {
        boolean check = studentInfoService.checkCode(studentInfo.getCode(), studentInfo.getId());
        if (check) {
            throw new FebsException("学号不能重复");
        }
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
