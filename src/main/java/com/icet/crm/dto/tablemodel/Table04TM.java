package com.icet.crm.dto.tablemodel;

import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Table04TM {

    private String itemCode;
    private String description;
    private Integer quantity;
    private Double unitPrice;
    private Double total;
}
