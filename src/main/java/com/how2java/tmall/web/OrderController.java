package com.how2java.tmall.web;

import com.how2java.tmall.pojo.Order;
import com.how2java.tmall.pojo.User;
import com.how2java.tmall.service.OrderIteamService;
import com.how2java.tmall.service.OrderService;
import com.how2java.tmall.service.UserService;
import com.how2java.tmall.util.Page4Navigator;
import com.how2java.tmall.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;

@RestController
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    OrderIteamService orderIteamService;

    @GetMapping("/orders")
    public Page4Navigator<Order> list(@RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "5") int size) throws Exception {
        start = start<0?0:start;
        Page4Navigator<Order> page =orderService.list(start, size, 5);  //5表示导航分页最多有5个，像 [1,2,3,4,5] 这样
        orderIteamService.fill(page.getContent());
        orderService.removeOrderFromOrderItem(page.getContent());
        return page;
    }
    @PutMapping("deliveryOrder/{oid}")
    public Object deliveryOrder(@PathVariable int oid) throws IOException {
        Order order=orderService.get(oid);
        order.setStatus(OrderService.waitPay);
        order.setDeliveryDate(new Date());
        orderService.update(order);
        return Result.success();

    }


}