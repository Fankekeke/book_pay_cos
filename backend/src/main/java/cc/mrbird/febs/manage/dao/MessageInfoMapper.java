package cc.mrbird.febs.manage.dao;

import cc.mrbird.febs.manage.entity.MessageInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface MessageInfoMapper extends BaseMapper<MessageInfo> {

    /**
     * 分页获取消息信息
     *
     * @param page        分页对象
     * @param messageInfo 消息信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectMessagePage(Page<MessageInfo> page, @Param("messageInfo") MessageInfo messageInfo);
}
