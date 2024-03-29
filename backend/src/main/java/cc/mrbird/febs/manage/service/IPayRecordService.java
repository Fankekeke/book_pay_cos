package cc.mrbird.febs.manage.service;

import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.manage.entity.MessageInfo;
import cc.mrbird.febs.manage.entity.PayRecord;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
public interface IPayRecordService extends IService<PayRecord> {

    /**
     * 分页获取支付记录信息
     *
     * @param page      分页对象
     * @param payRecord 支付记录信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectRecordPage(Page<PayRecord> page, PayRecord payRecord);

    /**
     * 获取支付记录详情
     *
     * @param id 支付记录ID
     * @return 结果
     */
    LinkedHashMap<String, Object> selectRecordDetail(Integer id);

    /**
     * 添加班级图书绑定后处理
     *
     * @param classId 班级ID
     * @param bookId  图书ID
     */
    void addClassBind(Integer classId, Integer bookId) throws FebsException;

    /**
     * 添加学生图书绑定后处理
     *
     * @param studentId 学生ID
     * @param classId   班级ID
     */
    void addStudentBind(Integer studentId, Integer classId);

    /**
     * 订单付款
     *
     * @param orderCode 订单编号
     */
    void orderPaymentPlatform(String orderCode);

    /**
     * 发送消息
     *
     * @param studentId  学生ID
     * @param bookIdList 图书ID
     */
    void sendMessage(Integer studentId, List<Integer> bookIdList);

    /**
     * 根据班级发送消息
     *
     * @param classId 班级ID
     * @param bookId  图书ID
     */
    void sendMessageByClass(Integer classId, Integer bookId);

    /**
     * 获取学生统计数据
     *
     * @param userId 用户ID
     * @return 结果
     */
    LinkedHashMap<String, Object> selectHomeDataByStudent(Integer userId) throws FebsException;

    /**
     * 主页数据
     *
     * @return 结果
     */
    LinkedHashMap<String, Object> homeData();

    /**
     * 根据月份获取统计情况
     *
     * @param date 日期
     * @return 结果
     */
    LinkedHashMap<String, Object> selectStatisticsByMonth(String date) throws FebsException;
}
