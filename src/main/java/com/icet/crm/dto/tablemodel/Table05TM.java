package com.icet.crm.dto.tablemodel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Table05TM {

    private String itemCode;
    private String name;
    private String category;
    private Integer size;
    private String unitPrice;
    private Integer quantity;
    private String supplierId;
}
