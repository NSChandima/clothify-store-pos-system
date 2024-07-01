package com.icet.crm.bo.custom;

import com.icet.crm.bo.SuperBo;
import com.icet.crm.dto.Item;

public interface ItemBo extends SuperBo {

    public boolean saveItem(Item dto);
    public boolean deleteItem(String itemCode);
}
