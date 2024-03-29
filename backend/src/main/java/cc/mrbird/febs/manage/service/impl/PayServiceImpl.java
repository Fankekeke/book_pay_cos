package cc.mrbird.febs.manage.service.impl;

import cc.mrbird.febs.common.utils.Alipay;
import cc.mrbird.febs.manage.entity.AlipayBean;
import cc.mrbird.febs.manage.service.PayService;
import com.alipay.api.AlipayApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayServiceImpl implements PayService {

    @Autowired
    private Alipay alipay;

    @Override
    public String aliPay(AlipayBean alipayBean) throws AlipayApiException {
        return alipay.pay(alipayBean);
    }
}