package cc.mrbird.febs.manage.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 图书回收
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RecycleInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 学生ID
     */
    private Integer studentId;

    /**
     * 图书ID
     */
    private Integer bookId;

    /**
     * 类型（1.捐赠 2.回收）
     */
    private String recucleType;

    /**
     * 备注消息
     */
    private String remark;

    /**
     * 创建时间
     */
    private LocalDateTime createDate;


}
