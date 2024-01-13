package cc.mrbird.febs.manage.service.impl;

import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.manage.entity.StudentInfo;
import cc.mrbird.febs.manage.dao.StudentInfoMapper;
import cc.mrbird.febs.manage.service.IStudentInfoService;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
@Service
public class StudentInfoServiceImpl extends ServiceImpl<StudentInfoMapper, StudentInfo> implements IStudentInfoService {

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

        return id != null && (studentList.size() > 1 || !studentList.get(0).getId().equals(id));
    }
}
