package cc.mrbird.febs.manage.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 班级信息
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ClassInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 班级编号
     */
    private String code;

    /**
     * 班级名称
     */
    private String className;

    /**
     * 创建时间
     */
    private String createDate;

    /**
     * 授课老师
     */
    private String instructor;

    /**
     * 助理
     */
    private String assistant;

    /**
     * 辅导员
     */
    private String counselor;

    /**
     * 备注
     */
    private String remark;


}
