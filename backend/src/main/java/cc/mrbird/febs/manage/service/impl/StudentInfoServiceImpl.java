package cc.mrbird.febs.manage.service.impl;

import cc.mrbird.febs.manage.entity.StudentInfo;
import cc.mrbird.febs.manage.dao.StudentInfoMapper;
import cc.mrbird.febs.manage.service.IStudentInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

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
}
