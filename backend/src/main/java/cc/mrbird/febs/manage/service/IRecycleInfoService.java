package cc.mrbird.febs.manage.service;

import cc.mrbird.febs.manage.entity.RecycleInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface IRecycleInfoService extends IService<RecycleInfo> {

    /**
     * 分页获取图书回收信息
     *
     * @param page        分页对象
     * @param recycleInfo 图书回收信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectRecyclePage(Page<RecycleInfo> page, RecycleInfo recycleInfo);

    /**
     * 获取图书回收详情
     *
     * @param id 图书回收ID
     * @return 结果
     */
    LinkedHashMap<String, Object> recycleDetail(Integer id);
}
