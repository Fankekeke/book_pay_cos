package cc.mrbird.febs.manage.service;

import cc.mrbird.febs.manage.entity.BookInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface IBookInfoService extends IService<BookInfo> {

    /**
     * 分页获取图书信息
     *
     * @param page     分页对象
     * @param bookInfo 图书信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectBookPage(Page<BookInfo> page, BookInfo bookInfo);
}
