package cc.mrbird.febs.manage.service.impl;

import cc.mrbird.febs.manage.dao.BookInfoMapper;
import cc.mrbird.febs.manage.dao.StudentInfoMapper;
import cc.mrbird.febs.manage.entity.BookInfo;
import cc.mrbird.febs.manage.entity.RecycleInfo;
import cc.mrbird.febs.manage.dao.RecycleInfoMapper;
import cc.mrbird.febs.manage.entity.StudentInfo;
import cc.mrbird.febs.manage.service.IRecycleInfoService;
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
public class RecycleInfoServiceImpl extends ServiceImpl<RecycleInfoMapper, RecycleInfo> implements IRecycleInfoService {

    private final StudentInfoMapper studentInfoMapper;

    private final BookInfoMapper bookInfoMapper;

    /**
     * 分页获取图书回收信息
     *
     * @param page        分页对象
     * @param recycleInfo 图书回收信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectRecyclePage(Page<RecycleInfo> page, RecycleInfo recycleInfo) {
        return baseMapper.selectRecyclePage(page, recycleInfo);
    }

    /**
     * 获取图书回收详情
     *
     * @param id 图书回收ID
     * @return 结果
     */
    @Override
    public LinkedHashMap<String, Object> recycleDetail(Integer id) {
        // 返回数据
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>() {
            {
                put("student", null);
                put("book", null);
                put("recycle", null);
            }
        };

        // 回收信息
        RecycleInfo recycleInfo = this.getById(id);
        result.put("recycle", recycleInfo);

        // 图书信息
        BookInfo bookInfo = bookInfoMapper.selectById(recycleInfo.getBookId());
        result.put("book", bookInfo);

        // 学生信息
        StudentInfo studentInfo = studentInfoMapper.selectById(recycleInfo.getStudentId());
        result.put("student", studentInfo);

        return result;
    }
}
