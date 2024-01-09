package cc.mrbird.febs.manage.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 消息通知
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MessageInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 学生ID
     */
    private Integer studentId;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 图书ID
     */
    private Integer bookId;

    /**
     * 状态（0.未读 1.已读）
     */
    private String status;

    /**
     * 创建时间
     */
    private String createDate;

    @TableField(exist = false)
    private String studentName;

    @TableField(exist = false)
    private String code;
}
