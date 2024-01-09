package cc.mrbird.febs.manage.service;

import cc.mrbird.febs.manage.entity.ClassBindBookInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface IClassBindBookInfoService extends IService<ClassBindBookInfo> {

    /**
     * 分页获取班级绑定图书信息
     *
     * @param page              分页对象
     * @param classBindBookInfo 班级绑定图书信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectPage(Page<ClassBindBookInfo> page, ClassBindBookInfo classBindBookInfo);
}
