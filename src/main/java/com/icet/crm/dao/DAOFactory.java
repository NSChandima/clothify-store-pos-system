package com.icet.crm.dao;

import com.icet.crm.dao.custom.impl.CustomerDaoImpl;
import com.icet.crm.dao.custom.impl.ItemDaoImpl;
import com.icet.crm.dao.custom.impl.SupplierDaoImpl;
import com.icet.crm.dao.custom.impl.UserDaoImpl;
import com.icet.crm.util.DaoType;

public class DAOFactory {

    private static DAOFactory instance;
    private DAOFactory(){}

    public static DAOFactory getInstance(){
       return instance != null ?instance: ( instance=new DAOFactory());
    }
    public <T extends SuperDao>T getDao(DaoType type){
        switch (type){
            case CUSTOMER: return (T) new CustomerDaoImpl();
            case ITEM: return (T) new ItemDaoImpl();
            case SUPPLIER: return (T) new SupplierDaoImpl();
            case USER: return (T) new UserDaoImpl();
        }
        return null;
    }
}
