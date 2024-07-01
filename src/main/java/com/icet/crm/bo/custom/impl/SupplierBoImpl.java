package com.icet.crm.bo.custom.impl;

import com.icet.crm.bo.custom.SupplierBo;
import com.icet.crm.dao.DAOFactory;
import com.icet.crm.dao.custom.SupplierDao;
import com.icet.crm.dto.Supplier;
import com.icet.crm.entity.SupplierEntity;
import com.icet.crm.util.DaoType;
import org.modelmapper.ModelMapper;

import java.sql.SQLException;

public class SupplierBoImpl implements SupplierBo {

    private SupplierDao supplierDao = DAOFactory.getInstance().getDao(DaoType.SUPPLIER);
    @Override
    public boolean saveSupplier(Supplier dto) {
            return supplierDao.save(new ModelMapper().map(dto, SupplierEntity.class));
    }
    @Override
    public boolean deleteSupplier(String id) {
        return supplierDao.delete(id);
    }
    @Override
    public boolean updateSupplier(Supplier dto) {
        return supplierDao.update(new ModelMapper().map(dto, SupplierEntity.class));
    }
}
