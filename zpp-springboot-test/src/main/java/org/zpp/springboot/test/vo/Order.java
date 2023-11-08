package org.zpp.springboot.test.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Order implements Serializable {

    private String orderNo;

    private String productName;

    private String purchaseName;
}
