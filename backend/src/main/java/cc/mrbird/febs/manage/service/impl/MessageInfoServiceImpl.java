package cc.mrbird.febs.manage.service.impl;

import cc.mrbird.febs.manage.entity.MessageInfo;
import cc.mrbird.febs.manage.dao.MessageInfoMapper;
import cc.mrbird.febs.manage.service.IMessageInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author FanK
 */
@Service
public class MessageInfoServiceImpl extends ServiceImpl<MessageInfoMapper, MessageInfo> implements IMessageInfoService {

}
