package com.icet.crm.dto.tablemodel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Table01TM {

    private String customerId;
    private String name;
    private String address;
    private Integer contact;
}
