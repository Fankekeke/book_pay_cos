package cc.mrbird.febs.manage.service.impl;

import cc.mrbird.febs.manage.entity.StudentInfo;
import cc.mrbird.febs.manage.dao.StudentInfoMapper;
import cc.mrbird.febs.manage.service.IStudentInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author FanK
 */
@Service
public class StudentInfoServiceImpl extends ServiceImpl<StudentInfoMapper, StudentInfo> implements IStudentInfoService {

}
