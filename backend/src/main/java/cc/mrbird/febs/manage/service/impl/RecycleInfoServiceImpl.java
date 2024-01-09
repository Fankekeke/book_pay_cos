package cc.mrbird.febs.manage.service.impl;

import cc.mrbird.febs.manage.entity.RecycleInfo;
import cc.mrbird.febs.manage.dao.RecycleInfoMapper;
import cc.mrbird.febs.manage.service.IRecycleInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
@Service
public class RecycleInfoServiceImpl extends ServiceImpl<RecycleInfoMapper, RecycleInfo> implements IRecycleInfoService {

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
}
