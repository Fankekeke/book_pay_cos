package cc.mrbird.febs.manage.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 学生信息管理
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class StudentInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 学生姓名
     */
    private String studentName;

    /**
     * 学号
     */
    private String code;

    /**
     * 性别（1.男 2.女）
     */
    private String sex;

    /**
     * 所属班级
     */
    private Integer classId;

    /**
     * 出生日期
     */
    private String birthday;

    /**
     * 省份
     */
    private String province;

    /**
     * 市区
     */
    private String city;

    /**
     * 区
     */
    private String area;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 创建时间
     */
    private String createDate;

    /**
     * 联系方式
     */
    private String phone;

    /**
     * 学生照片
     */
    private String images;

    /**
     * 专业
     */
    private String major;

    @TableField(exist = false)
    private String className;
}
