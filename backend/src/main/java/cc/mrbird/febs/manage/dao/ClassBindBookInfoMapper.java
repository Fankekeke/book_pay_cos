package cc.mrbird.febs.manage.dao;

import cc.mrbird.febs.manage.entity.ClassBindBookInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface ClassBindBookInfoMapper extends BaseMapper<ClassBindBookInfo> {

    /**
     * 分页获取班级绑定图书信息
     *
     * @param page              分页对象
     * @param classBindBookInfo 班级绑定图书信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectBindPage(Page<ClassBindBookInfo> page, @Param("classBindBookInfo") ClassBindBookInfo classBindBookInfo);
}
