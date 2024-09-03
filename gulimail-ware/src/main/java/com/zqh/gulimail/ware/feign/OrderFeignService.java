package com.zqh.gulimail.ware.feign;


import com.zqh.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("gulimall-order")
public interface OrderFeignService {

    /**
     * 获取订单状态
     */
    @GetMapping("/order/order/status/{orderSn}")
    R getOrderByOrderSn(@PathVariable("orderSn") String orderSn);
}
