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
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

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
     * 捐赠或回收地址
     */
    private String address;

    /**
     * 备注消息
     */
    private String remark;

    /**
     * 创建时间
     */
    private String createDate;

    @TableField(exist = false)
    private String studentName;
    @TableField(exist = false)
    private String bookName;

}
