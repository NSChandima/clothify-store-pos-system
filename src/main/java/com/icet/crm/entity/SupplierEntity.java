package com.icet.crm.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SupplierEntity {

    private String supplierId;
    private String name;
    private String company;
    private Integer contact;
    private String email;
}
