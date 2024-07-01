package com.icet.crm.bo.custom.impl;

import com.icet.crm.bo.custom.ItemBo;
import com.icet.crm.dao.DAOFactory;
import com.icet.crm.dao.custom.ItemDao;
import com.icet.crm.dto.Item;
import com.icet.crm.entity.ItemEntity;
import com.icet.crm.util.DaoType;
import org.modelmapper.ModelMapper;

public class ItemBoImpl implements ItemBo {

    ItemDao itemDao = DAOFactory.getInstance().getDao(DaoType.ITEM);

    @Override
    public boolean saveItem(Item dto){
        return itemDao.save(new ModelMapper().map(dto, ItemEntity.class));
    }
    @Override
    public boolean deleteItem(String itemCode) {
       return itemDao.delete(itemCode);
    }
}
