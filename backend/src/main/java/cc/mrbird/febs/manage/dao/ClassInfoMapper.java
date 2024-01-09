package cc.mrbird.febs.manage.dao;

import cc.mrbird.febs.manage.entity.ClassInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface ClassInfoMapper extends BaseMapper<ClassInfo> {

    /**
     * 分页获取班级信息
     *
     * @param page      分页对象
     * @param classInfo 班级信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectClassPage(Page<ClassInfo> page, @Param("classInfo") ClassInfo classInfo);
}
