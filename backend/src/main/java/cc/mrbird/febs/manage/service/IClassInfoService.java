package cc.mrbird.febs.manage.service;

import cc.mrbird.febs.manage.entity.ClassInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface IClassInfoService extends IService<ClassInfo> {

    /**
     * 分页获取班级信息
     *
     * @param page      分页对象
     * @param classInfo 班级信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectClassPage(Page<ClassInfo> page, ClassInfo classInfo);
}
