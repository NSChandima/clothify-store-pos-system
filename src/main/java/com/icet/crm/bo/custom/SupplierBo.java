package com.icet.crm.bo.custom;

import com.icet.crm.bo.SuperBo;
import com.icet.crm.dto.Supplier;

public interface SupplierBo extends SuperBo {

    public boolean saveSupplier(Supplier dto);
    public boolean deleteSupplier(String id);
    public boolean updateSupplier(Supplier dto);
}
