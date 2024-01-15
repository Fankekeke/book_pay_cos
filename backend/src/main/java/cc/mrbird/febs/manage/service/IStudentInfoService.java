package cc.mrbird.febs.manage.service;

import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.manage.entity.BookInfo;
import cc.mrbird.febs.manage.entity.StudentInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
public interface IStudentInfoService extends IService<StudentInfo> {

    /**
     * 分页获取学生信息
     *
     * @param page        分页对象
     * @param studentInfo 学生信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectStudentPage(Page<StudentInfo> page, StudentInfo studentInfo);

    /**
     * 校验学号是否重复
     *
     * @param code 学号
     * @param id   ID
     * @return 结果
     */
    boolean checkCode(String code, Integer id) throws FebsException;

    /**
     * 根据学生获取已缴费图书
     *
     * @param userId 用户ID
     * @return 结果
     */
    List<BookInfo> selectBookByStudentId(Integer userId);

    /**
     * 根据学生获取可用图书
     *
     * @param userId 用户ID
     * @return 结果
     */
    List<BookInfo> selectUseBookByStudent(Integer userId);
}
