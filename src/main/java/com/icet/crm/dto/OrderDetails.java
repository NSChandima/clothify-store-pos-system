package com.icet.crm.dto;

import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetails {

    private String orderId;
    private String itemCode;
    private Integer quantity;
}
