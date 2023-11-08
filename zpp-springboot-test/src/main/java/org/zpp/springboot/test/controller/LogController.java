package org.zpp.springboot.test.controller;

import com.mzt.logapi.context.LogRecordContext;
import com.mzt.logapi.starter.annotation.LogRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.zpp.springboot.test.vo.Order;

@Slf4j
@RestController
@RequestMapping("/log")
public class LogController {


    @LogRecord(
            success = "{{#order.purchaseName}}下了一个订单,购买商品「{{#order.productName}}」,测试变量「{{#innerOrder.productName}}」,下单结果:{{#_ret}}",
            type = "1", bizNo = "{{#order.orderNo}}")
    @PostMapping("/createOrder")
    public boolean createOrder(@RequestBody Order order) {
        log.info("【创建订单】orderNo={}", order.getOrderNo());
        // db insert order
        Order order1 = new Order();
        order1.setProductName("内部变量测试");
        LogRecordContext.putVariable("innerOrder", order1);
        return true;
    }
}
