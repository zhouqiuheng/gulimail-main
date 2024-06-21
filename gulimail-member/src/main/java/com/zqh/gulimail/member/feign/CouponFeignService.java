package com.zqh.gulimail.member.feign;

import com.zqh.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;


/*
* 这是一个声明的远程调用
*
* */
@FeignClient("gulimail-coupon")
public interface CouponFeignService {
    @RequestMapping("/coupon/coupon/member/list")
    public R membercoupons();
}
