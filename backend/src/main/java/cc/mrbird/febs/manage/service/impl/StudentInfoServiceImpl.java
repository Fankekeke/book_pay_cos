package cc.mrbird.febs.manage.service.impl;

import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.manage.dao.BookInfoMapper;
import cc.mrbird.febs.manage.dao.PayRecordMapper;
import cc.mrbird.febs.manage.dao.RecycleInfoMapper;
import cc.mrbird.febs.manage.entity.*;
import cc.mrbird.febs.manage.dao.StudentInfoMapper;
import cc.mrbird.febs.manage.service.IClassInfoService;
import cc.mrbird.febs.manage.service.IPayRecordService;
import cc.mrbird.febs.manage.service.IStudentInfoService;
import cc.mrbird.febs.system.service.UserService;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author FanK
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StudentInfoServiceImpl extends ServiceImpl<StudentInfoMapper, StudentInfo> implements IStudentInfoService {

    private final BookInfoMapper bookInfoMapper;

    private final RecycleInfoMapper recycleInfoMapper;

    private final PayRecordMapper payRecordMapper;

    private final IClassInfoService classInfoService;

    private final IPayRecordService payRecordService;

    private final UserService userService;

    /**
     * 分页获取学生信息
     *
     * @param page        分页对象
     * @param studentInfo 学生信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectStudentPage(Page<StudentInfo> page, StudentInfo studentInfo) {
        return baseMapper.selectStudentPage(page, studentInfo);
    }

    /**
     * 校验学号是否重复
     *
     * @param code 学号
     * @param id   ID
     * @return 结果
     */
    @Override
    public boolean checkCode(String code, Integer id) throws FebsException {
        if (StrUtil.isEmpty(code)) {
            throw new FebsException("参数不能为空");
        }

        List<StudentInfo> studentList = this.list(Wrappers.<StudentInfo>lambdaQuery().eq(StudentInfo::getCode, code));
        if (CollectionUtil.isEmpty(studentList)) {
            return false;
        }

        return studentList.size() > 1 || (id != null && !studentList.get(0).getId().equals(id));
    }

    /**
     * 根据学生获取已缴费图书
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public List<BookInfo> selectBookByStudentId(Integer userId) {
        // 获取学生信息
        StudentInfo studentInfo = this.getOne(Wrappers.<StudentInfo>lambdaQuery().eq(StudentInfo::getUserId, userId));

        // 缴费完成图书
        List<PayRecord> recordList = payRecordMapper.selectList(Wrappers.<PayRecord>lambdaQuery().eq(PayRecord::getStudentId, studentInfo.getId()).eq(PayRecord::getStatus, "1"));
        if (CollectionUtil.isEmpty(recordList)) {
            return Collections.emptyList();
        }

        // 获取图书信息
        List<Integer> bookIds = recordList.stream().map(PayRecord::getBookId).collect(Collectors.toList());
        return bookInfoMapper.selectList(Wrappers.<BookInfo>lambdaQuery().in(BookInfo::getId, bookIds));
    }

    /**
     * 根据学生获取可用图书
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public List<BookInfo> selectUseBookByStudent(Integer userId) {
        // 获取学生信息
        StudentInfo studentInfo = this.getOne(Wrappers.<StudentInfo>lambdaQuery().eq(StudentInfo::getUserId, userId));

        // 缴费完成图书
        List<PayRecord> recordList = payRecordMapper.selectList(Wrappers.<PayRecord>lambdaQuery().eq(PayRecord::getStudentId, studentInfo.getId()).eq(PayRecord::getStatus, "1"));
        if (CollectionUtil.isEmpty(recordList)) {
            return Collections.emptyList();
        }

        // 获取图书信息
        List<Integer> bookIds = recordList.stream().map(PayRecord::getBookId).collect(Collectors.toList());
        List<BookInfo> resultList = bookInfoMapper.selectList(Wrappers.<BookInfo>lambdaQuery().in(BookInfo::getId, bookIds));

        if (CollectionUtil.isEmpty(resultList)) {
            return Collections.emptyList();
        }

        // 获取回收信息
        List<RecycleInfo> recycleList = recycleInfoMapper.selectList(Wrappers.<RecycleInfo>lambdaQuery().eq(RecycleInfo::getStudentId, studentInfo.getId()));
        if (CollectionUtil.isEmpty(recycleList)) {
            return resultList;
        }
        List<Integer> useBookIds = recycleList.stream().map(RecycleInfo::getBookId).distinct().collect(Collectors.toList());

        return resultList.stream().filter(e -> !useBookIds.contains(e.getId())).collect(Collectors.toList());
    }

    /**
     * 获取学生详情
     *
     * @param userId 学生ID
     * @return 结果
     */
    @Override
    public LinkedHashMap<String, Object> selectBookDetail(Integer userId) {
        // 返回数据
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>() {
            {
                put("user", null);
                put("order", Collections.emptyList());
            }
        };

        // 学生信息
        StudentInfo studentInfo = this.getOne(Wrappers.<StudentInfo>lambdaQuery().eq(StudentInfo::getUserId, userId));
        result.put("user", studentInfo);

        // 付款记录
        List<PayRecord> payRecordList = payRecordMapper.selectList(Wrappers.<PayRecord>lambdaQuery().eq(PayRecord::getStudentId, studentInfo.getId()).eq(PayRecord::getStatus, "1"));
        result.put("order", payRecordList);
        return result;
    }

    /**
     * 导入学生信息列表
     *
     * @param file 文件
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String importExcel(MultipartFile file) throws Exception {
        ExcelReader excelReader = ExcelUtil.getReader(file.getInputStream(), 0);
        setExcelHeaderAlias(excelReader);
        List<StudentInfo> reports = excelReader.read(1, 2, Integer.MAX_VALUE, StudentInfo.class);
        StringBuilder error = new StringBuilder("");
        if (CollectionUtil.isEmpty(reports)) {
            error.append("导入数据不得为空。");
            return error.toString();
        }
        // 所有学生信息
        List<StudentInfo> studentInfoList = this.list();
        Map<String, List<StudentInfo>> studentInfoMap = studentInfoList.stream().collect(Collectors.groupingBy(StudentInfo::getCode));
        // 校验学号是否重复
        Map<String, List<StudentInfo>> studentMap = reports.stream().collect(Collectors.groupingBy(StudentInfo::getCode));
        studentMap.forEach((key, value) -> {
            if (value.size() > 1) {
                error.append("学号不能重复。");
            }
        });
        if (StrUtil.isNotEmpty(error)) {
            return error.toString();
        }

        // 所有班级信息
        List<ClassInfo> classInfos = classInfoService.list();
        Map<String, ClassInfo> classMap = classInfos.stream().collect(Collectors.toMap(ClassInfo::getClassName, e -> e));
        for (StudentInfo expert : reports) {
            if (StrUtil.isEmpty(expert.getStudentName())) {
                error.append("\n名称不能为空");
                return error.toString();
            }
            if ("男".equals(expert.getSex())) {
                expert.setSex("1");
            } else {
                expert.setSex("2");
            }

            // 学号是否重复
            List<StudentInfo> studentInfos = studentInfoMap.get(expert.getCode());
            if (CollectionUtil.isNotEmpty(studentInfos)) {
                error.append("\n学号").append(expert.getCode()).append("不能重复");
                return error.toString();
            }

            // 班级信息
            ClassInfo classInfo = classMap.get(expert.getClassName());
            if (classInfo == null) {
                error.append("\n班级").append(expert.getClassName()).append("不存在");
                return error.toString();
            }
            expert.setClassId(classInfo.getId());

            expert.setCreateDate(DateUtil.formatDate(new Date()));
        }
        if (StrUtil.isEmpty(error.toString())) {
            for (StudentInfo report : reports) {
                userService.registUser(report.getCode(), "1234qwer", report);

                payRecordService.addStudentBind(report.getId(), report.getClassId());
            }
            return null;
        }
        return error.toString();
    }

    /**
     * 设置HeaderAlias
     *
     * @param excelReader HeaderAlias
     */
    public void setExcelHeaderAlias(ExcelReader excelReader) {
        excelReader.addHeaderAlias("学生姓名", "studentName");
        excelReader.addHeaderAlias("学号", "code");
        excelReader.addHeaderAlias("所属班级", "className");
        excelReader.addHeaderAlias("省份", "province");
        excelReader.addHeaderAlias("市区", "city");
        excelReader.addHeaderAlias("区", "area");
        excelReader.addHeaderAlias("出生日期", "出生日期");
        excelReader.addHeaderAlias("联系方式", "phone");
        excelReader.addHeaderAlias("所属专业", "major");
        excelReader.addHeaderAlias("详细地址", "address");
        excelReader.addHeaderAlias("性别", "sex");

    }
}
