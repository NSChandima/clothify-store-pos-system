package com.icet.crm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    private String itemCode;
    private String name;
    private String category;
    private Integer size;
    private String unitPrice;
    private Integer quantity;
    private String supplierId;

}
