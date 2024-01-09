package cc.mrbird.febs.manage.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 班级图书绑定
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ClassBindBookInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 班级ID
     */
    private Integer classId;

    /**
     * 图书ID
     */
    private Integer bookId;

    /**
     * 创建时间
     */
    private String createDate;


}
