package cc.mrbird.febs.manage.service.impl;

import cc.mrbird.febs.manage.dao.BookInfoMapper;
import cc.mrbird.febs.manage.dao.ClassInfoMapper;
import cc.mrbird.febs.manage.dao.StudentInfoMapper;
import cc.mrbird.febs.manage.entity.BookInfo;
import cc.mrbird.febs.manage.entity.ClassInfo;
import cc.mrbird.febs.manage.entity.PayRecord;
import cc.mrbird.febs.manage.dao.PayRecordMapper;
import cc.mrbird.febs.manage.entity.StudentInfo;
import cc.mrbird.febs.manage.service.IPayRecordService;
import cc.mrbird.febs.manage.service.IStudentInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PayRecordServiceImpl extends ServiceImpl<PayRecordMapper, PayRecord> implements IPayRecordService {

    private final StudentInfoMapper studentInfoMapper;

    private final ClassInfoMapper classInfoMapper;

    private final BookInfoMapper bookInfoMapper;

    /**
     * 分页获取支付记录信息
     *
     * @param page      分页对象
     * @param payRecord 支付记录信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectRecordPage(Page<PayRecord> page, PayRecord payRecord) {
        return baseMapper.selectRecordPage(page, payRecord);
    }

    /**
     * 获取支付记录详情
     *
     * @param id 支付记录ID
     * @return 结果
     */
    @Override
    public LinkedHashMap<String, Object> selectRecordDetail(Integer id) {
        // 返回信息
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>() {
            {
                put("payRecord", null);
                put("student", null);
                put("book", null);
                put("class", null);
            }
        };
        // 支付记录详情
        PayRecord payRecord = this.getById(id);
        result.put("payRecord", payRecord);

        StudentInfo studentInfo = studentInfoMapper.selectById(payRecord.getStudentId());
        result.put("student", studentInfo);

        BookInfo bookInfo = bookInfoMapper.selectById(payRecord.getBookId());
        result.put("book", bookInfo);

        ClassInfo classInfo = classInfoMapper.selectById(studentInfo.getClassId());
        result.put("class", classInfo);
        return result;
    }
}
