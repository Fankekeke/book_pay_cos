package cc.mrbird.febs.manage.service.impl;

import cc.mrbird.febs.manage.entity.ClassBindBookInfo;
import cc.mrbird.febs.manage.dao.ClassBindBookInfoMapper;
import cc.mrbird.febs.manage.service.IClassBindBookInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
@Service
public class ClassBindBookInfoServiceImpl extends ServiceImpl<ClassBindBookInfoMapper, ClassBindBookInfo> implements IClassBindBookInfoService {

    /**
     * 分页获取班级绑定图书信息
     *
     * @param page              分页对象
     * @param classBindBookInfo 班级绑定图书信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectPage(Page<ClassBindBookInfo> page, ClassBindBookInfo classBindBookInfo) {
        return baseMapper.selectPage(page, classBindBookInfo);
    }
}
