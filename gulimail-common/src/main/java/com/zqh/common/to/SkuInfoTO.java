package com.zqh.common.to;

import lombok.Data;

import java.io.Serializable;

/**
 * 商品信息TO
 */
@Data
public class SkuInfoTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long skuId;
    private String skuName;

}
