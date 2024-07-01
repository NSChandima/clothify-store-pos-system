package com.icet.crm.dto;

import lombok.*;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Order {

    private String orderId;
    private Date orderDate;
    private String customerId;
    private List<OrderDetails> orderDetailsList;


}
