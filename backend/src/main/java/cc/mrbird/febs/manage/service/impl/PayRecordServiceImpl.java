package cc.mrbird.febs.manage.service.impl;

import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.manage.dao.*;
import cc.mrbird.febs.manage.entity.*;
import cc.mrbird.febs.manage.service.IBulletinInfoService;
import cc.mrbird.febs.manage.service.IPayRecordService;
import cc.mrbird.febs.manage.service.IStudentInfoService;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author FanK
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PayRecordServiceImpl extends ServiceImpl<PayRecordMapper, PayRecord> implements IPayRecordService {

    private final StudentInfoMapper studentInfoMapper;

    private final ClassInfoMapper classInfoMapper;

    private final BookInfoMapper bookInfoMapper;

    private final IBulletinInfoService bulletinInfoService;

    private final ClassBindBookInfoMapper classBindBookInfoMapper;

    private final MessageInfoMapper messageInfoMapper;

    /**
     * 分页获取支付记录信息
     *
     * @param page      分页对象
     * @param payRecord 支付记录信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectRecordPage(Page<PayRecord> page, PayRecord payRecord) {
        return baseMapper.selectRecordPage(page, payRecord);
    }

    /**
     * 获取支付记录详情
     *
     * @param id 支付记录ID
     * @return 结果
     */
    @Override
    public LinkedHashMap<String, Object> selectRecordDetail(Integer id) {
        // 返回信息
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>() {
            {
                put("payRecord", null);
                put("student", null);
                put("book", null);
                put("class", null);
            }
        };
        // 支付记录详情
        PayRecord payRecord = this.getById(id);
        result.put("payRecord", payRecord);

        StudentInfo studentInfo = studentInfoMapper.selectById(payRecord.getStudentId());
        result.put("student", studentInfo);

        BookInfo bookInfo = bookInfoMapper.selectById(payRecord.getBookId());
        result.put("book", bookInfo);

        ClassInfo classInfo = classInfoMapper.selectById(studentInfo.getClassId());
        result.put("class", classInfo);
        return result;
    }

    /**
     * 添加班级图书绑定后处理
     *
     * @param classId 班级ID
     * @param bookId  图书ID
     */
    @Override
    public void addClassBind(Integer classId, Integer bookId) throws FebsException {
        // 获取班级信息
        ClassInfo classInfo = classInfoMapper.selectById(classId);

        if (classInfo == null) {
            throw new FebsException("班级信息不存在");
        }

        // 获取班级下的学生
        List<StudentInfo> studentList = studentInfoMapper.selectList(Wrappers.<StudentInfo>lambdaQuery().eq(StudentInfo::getClassId, classId));
        if (CollectionUtil.isEmpty(studentList)) {
            return;
        }
        // 图书信息
        BookInfo bookInfo = bookInfoMapper.selectById(bookId);

        List<Integer> studentIds = studentList.stream().map(StudentInfo::getId).collect(Collectors.toList());
        // 保存缴费记录
        List<PayRecord> recordList = new ArrayList<>();
        for (Integer studentId : studentIds) {
            PayRecord payRecord = new PayRecord();
            payRecord.setStudentId(studentId);
            payRecord.setBookId(bookId);
            payRecord.setPrice(bookInfo.getPrice());
            payRecord.setCode("PR-" + RandomUtil.randomNumbers(10));
            payRecord.setStatus("0");
            payRecord.setCreateDate(DateUtil.formatDateTime(new Date()));
            recordList.add(payRecord);
        }

        this.saveBatch(recordList);
    }

    /**
     * 添加学生图书绑定后处理
     *
     * @param studentId 学生ID
     * @param classId   班级ID
     */
    @Override
    public void addStudentBind(Integer studentId, Integer classId) {
        // 获取班级绑定的图书信息
        List<ClassBindBookInfo> bindList = classBindBookInfoMapper.selectList(Wrappers.<ClassBindBookInfo>lambdaQuery().eq(ClassBindBookInfo::getClassId, classId));
        if (CollectionUtil.isEmpty(bindList)) {
            return;
        }

        List<Integer> bookIds = bindList.stream().map(ClassBindBookInfo::getBookId).filter(Objects::nonNull).distinct().collect(Collectors.toList());
        List<BookInfo> bookInfoList = bookInfoMapper.selectList(Wrappers.<BookInfo>lambdaQuery().in(BookInfo::getId, bookIds));
        Map<Integer, BigDecimal> bookMap = bookInfoList.stream().collect(Collectors.toMap(BookInfo::getId, BookInfo::getPrice));

        // 保存缴费记录
        List<PayRecord> recordList = new ArrayList<>();
        for (Integer bookId : bookIds) {
            PayRecord payRecord = new PayRecord();
            payRecord.setStudentId(studentId);
            payRecord.setBookId(bookId);
            payRecord.setPrice(bookMap.getOrDefault(bookId, BigDecimal.ONE));
            payRecord.setCode("PR-" + RandomUtil.randomNumbers(10));
            payRecord.setStatus("0");
            payRecord.setCreateDate(DateUtil.formatDateTime(new Date()));
            recordList.add(payRecord);
        }
        this.saveBatch(recordList);
    }

    /**
     * 订单付款
     *
     * @param orderCode 订单编号
     */
    @Override
    public void orderPaymentPlatform(String orderCode) {
        PayRecord payRecord = this.getOne(Wrappers.<PayRecord>lambdaQuery().eq(PayRecord::getCode, orderCode));
        // 更新缴费状态
        payRecord.setStatus("1");
        this.updateById(payRecord);
    }

    /**
     * 获取学生统计数据
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public LinkedHashMap<String, Object> selectHomeDataByStudent(Integer userId) throws FebsException {
        // 返回数据
        LinkedHashMap<String, Object> result = new LinkedHashMap<>();

        // 获取学生信息
        StudentInfo studentInfo = studentInfoMapper.selectOne(Wrappers.<StudentInfo>lambdaQuery().eq(StudentInfo::getUserId, userId));
        if (studentInfo == null) {
            throw new FebsException("未找到此学生信息");
        }

        // 公告信息
        result.put("bulletin", bulletinInfoService.list(Wrappers.<BulletinInfo>lambdaQuery().eq(BulletinInfo::getRackUp, 1)));

        List<PayRecord> recordList = this.list(Wrappers.<PayRecord>lambdaQuery().eq(PayRecord::getStudentId, studentInfo.getId()));
        // 我的图书
        result.put("bookNum", recordList.size());
        // 未缴费图书
        result.put("bookOweNum", recordList.stream().filter(e -> "0".equals(e.getStatus())).count());
        // 已缴费图书
        result.put("bookOwnNum", recordList.stream().filter(e -> "1".equals(e.getStatus())).count());
        // 欠缴金额
        List<PayRecord> oweRecordList = recordList.stream().filter(e -> "0".equals(e.getStatus())).collect(Collectors.toList());
        result.put("owePrice", CollectionUtil.isEmpty(oweRecordList) ? BigDecimal.ZERO : oweRecordList.stream().map(PayRecord::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add));

        // 欠交图书信息
        List<Integer> bookletList = oweRecordList.stream().map(PayRecord::getBookId).collect(Collectors.toList());
        List<BookInfo> bookList = bookInfoMapper.selectList(Wrappers.<BookInfo>lambdaQuery().in(BookInfo::getId, bookletList));
        result.put("oweBook", bookList);

        // 消息通知
        List<MessageInfo> messageList = messageInfoMapper.selectList(Wrappers.<MessageInfo>lambdaQuery().eq(MessageInfo::getStudentId, studentInfo.getId()));
        result.put("message", messageList);
        return result;
    }

    /**
     * 主页数据
     *
     * @return 结果
     */
    @Override
    public LinkedHashMap<String, Object> homeData() {
        LinkedHashMap<String, Object> result = new LinkedHashMap<>();
        // 总缴费数量
        result.put("orderCode", this.count());
        // 总收益
        result.put("orderPrice", baseMapper.selectOrderPrice());
        // 学生数量
        result.put("pharmacyNum", studentInfoMapper.selectCount(Wrappers.<StudentInfo>lambdaQuery()));
        // 图书数量
        result.put("staffNum", bookInfoMapper.selectCount(Wrappers.<BookInfo>lambdaQuery()));

        // 本月订单数量
        List<PayRecord> orderList = baseMapper.selectOrderByMonth();
        result.put("monthOrderNum", CollectionUtil.isEmpty(orderList) ? 0 : orderList.size());
        BigDecimal orderPrice = orderList.stream().map(PayRecord::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        // 获取本月收益
        result.put("monthOrderPrice", orderPrice);

        // 本年订单数量
        List<PayRecord> orderYearList = baseMapper.selectOrderByYear();
        result.put("yearOrderNum", CollectionUtil.isEmpty(orderYearList) ? 0 : orderYearList.size());
        // 本年总收益
        BigDecimal orderYearPrice = orderYearList.stream().map(PayRecord::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        result.put("yearOrderPrice", orderYearPrice);

        // 公告信息
        result.put("bulletin", bulletinInfoService.list(Wrappers.<BulletinInfo>lambdaQuery().eq(BulletinInfo::getRackUp, 1)));

        // 近十天内订单统计
        result.put("orderNumWithinDays", baseMapper.selectOrderNumWithinDays(null));
        // 近十天内收益统计
        result.put("orderPriceWithinDays", baseMapper.selectOrderPriceWithinDays(null));
        return result;
    }
}
