package cc.mrbird.febs.manage.service.impl;

import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.manage.dao.BookInfoMapper;
import cc.mrbird.febs.manage.dao.PayRecordMapper;
import cc.mrbird.febs.manage.dao.RecycleInfoMapper;
import cc.mrbird.febs.manage.entity.BookInfo;
import cc.mrbird.febs.manage.entity.PayRecord;
import cc.mrbird.febs.manage.entity.RecycleInfo;
import cc.mrbird.febs.manage.entity.StudentInfo;
import cc.mrbird.febs.manage.dao.StudentInfoMapper;
import cc.mrbird.febs.manage.service.IStudentInfoService;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
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
}
