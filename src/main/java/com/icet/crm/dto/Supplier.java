package com.icet.crm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Supplier {

    private String supplierId;
    private String name;
    private String company;
    private Integer contact;
    private String email;
}
