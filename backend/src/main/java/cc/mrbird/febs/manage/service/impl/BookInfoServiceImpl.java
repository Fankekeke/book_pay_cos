package cc.mrbird.febs.manage.service.impl;

import cc.mrbird.febs.manage.entity.BookInfo;
import cc.mrbird.febs.manage.dao.BookInfoMapper;
import cc.mrbird.febs.manage.service.IBookInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
@Service
public class BookInfoServiceImpl extends ServiceImpl<BookInfoMapper, BookInfo> implements IBookInfoService {


    /**
     * 分页获取图书信息
     *
     * @param page     分页对象
     * @param bookInfo 图书信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectBookPage(Page<BookInfo> page, BookInfo bookInfo) {
        return baseMapper.selectBookPage(page, bookInfo);
    }
}
