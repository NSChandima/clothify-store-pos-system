package com.icet.crm.bo.custom.impl;

import com.icet.crm.bo.custom.CustomerBo;
import com.icet.crm.dao.DAOFactory;
import com.icet.crm.dao.custom.CustomerDao;
import com.icet.crm.dto.Customer;
import com.icet.crm.entity.CustomerEntity;
import com.icet.crm.util.DaoType;
import org.modelmapper.ModelMapper;

import java.sql.SQLException;

public class CustomerBoImpl implements CustomerBo {

    CustomerDao customerDao = DAOFactory.getInstance().getDao(DaoType.CUSTOMER);
    @Override
    public boolean saveCustomer(Customer dto) {

        return customerDao.save(new ModelMapper().map(dto, CustomerEntity.class));
    }
    @Override
    public boolean deleteCustomer(String id) {
        return customerDao.delete(id);
    }

    public boolean updateCustomer(Customer dto){
        return customerDao.update(new ModelMapper().map(dto, CustomerEntity.class));
    }

}
