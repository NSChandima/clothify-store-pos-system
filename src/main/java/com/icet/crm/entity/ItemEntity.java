package com.icet.crm.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ItemEntity {

    private String itemCode;
    private String name;
    private String category;
    private Integer size;
    private String unitPrice;
    private Integer quantity;
    private String supplierId;
}
