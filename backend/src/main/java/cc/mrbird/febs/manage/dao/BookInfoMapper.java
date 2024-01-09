package cc.mrbird.febs.manage.dao;

import cc.mrbird.febs.manage.entity.BookInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface BookInfoMapper extends BaseMapper<BookInfo> {

    /**
     * 分页获取图书信息
     *
     * @param page     分页对象
     * @param bookInfo 图书信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectBookPage(Page<BookInfo> page, @Param("bookInfo") BookInfo bookInfo);
}
