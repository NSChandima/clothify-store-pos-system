package com.icet.crm.bo.custom;

import com.icet.crm.bo.SuperBo;
import com.icet.crm.dto.Customer;

public interface CustomerBo extends SuperBo {

    boolean saveCustomer(Customer dto);
    boolean deleteCustomer(String id);
    boolean updateCustomer(Customer dto);
}
