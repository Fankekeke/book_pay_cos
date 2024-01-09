package cc.mrbird.febs.manage.service.impl;

import cc.mrbird.febs.manage.entity.ClassInfo;
import cc.mrbird.febs.manage.dao.ClassInfoMapper;
import cc.mrbird.febs.manage.service.IClassInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
@Service
public class ClassInfoServiceImpl extends ServiceImpl<ClassInfoMapper, ClassInfo> implements IClassInfoService {

    /**
     * 分页获取班级信息
     *
     * @param page      分页对象
     * @param classInfo 班级信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectClassPage(Page<ClassInfo> page, ClassInfo classInfo) {
        return baseMapper.selectClassPage(page, classInfo);
    }
}
