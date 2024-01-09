package cc.mrbird.febs.manage.service.impl;

import cc.mrbird.febs.manage.entity.MessageInfo;
import cc.mrbird.febs.manage.dao.MessageInfoMapper;
import cc.mrbird.febs.manage.service.IMessageInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
@Service
public class MessageInfoServiceImpl extends ServiceImpl<MessageInfoMapper, MessageInfo> implements IMessageInfoService {

    /**
     * 分页获取消息信息
     *
     * @param page        分页对象
     * @param messageInfo 消息信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectMessagePage(Page<MessageInfo> page, MessageInfo messageInfo) {
        return baseMapper.selectMessagePage(page, messageInfo);
    }
}
