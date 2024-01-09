package cc.mrbird.febs.manage.dao;

import cc.mrbird.febs.manage.entity.RecycleInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface RecycleInfoMapper extends BaseMapper<RecycleInfo> {

    /**
     * 分页获取图书回收信息
     *
     * @param page        分页对象
     * @param recycleInfo 图书回收信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectRecyclePage(Page<RecycleInfo> page, @Param("recycleInfo") RecycleInfo recycleInfo);
}
