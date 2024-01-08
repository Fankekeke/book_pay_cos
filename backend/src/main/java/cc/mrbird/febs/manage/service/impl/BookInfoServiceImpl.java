package cc.mrbird.febs.manage.service.impl;

import cc.mrbird.febs.manage.entity.BookInfo;
import cc.mrbird.febs.manage.dao.BookInfoMapper;
import cc.mrbird.febs.manage.service.IBookInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author FanK
 */
@Service
public class BookInfoServiceImpl extends ServiceImpl<BookInfoMapper, BookInfo> implements IBookInfoService {

}
