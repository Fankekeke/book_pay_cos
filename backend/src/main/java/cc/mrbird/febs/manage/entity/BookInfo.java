package cc.mrbird.febs.manage.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 图书管理
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BookInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 书名
     */
    private String bookName;

    /**
     * 作者
     */
    private String auther;

    /**
     * 出版社
     */
    private String press;

    /**
     * 关键词
     */
    private String keyWord;

    /**
     * 摘要
     */
    private String content;

    /**
     * 图书类型（1.医疗 2.科技 3.历史 4.烹饪 5.高数 6.小说 7.诗词）
     */
    private String type;

    /**
     * 创建时间
     */
    private LocalDateTime createDate;


}
