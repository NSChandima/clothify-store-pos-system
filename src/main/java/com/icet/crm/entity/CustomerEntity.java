package com.icet.crm.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerEntity {

    private String customerId;
    private String name;
    private String address;
    private Integer contact;
}

