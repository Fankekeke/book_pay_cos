package cc.mrbird.febs.manage.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 缴费记录
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PayRecord implements Serializable {

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
     * 状态（0.未缴费 1.已缴费）
     */
    private String status;

    /**
     * 缴纳时间
     */
    private String payDate;

    /**
     * 缴纳金额
     */
    private BigDecimal price;

    /**
     * 创建时间
     */
    private String createDate;

    @TableField(exist = false)
    private String bookName;

    @TableField(exist = false)
    private String studentName;
}
