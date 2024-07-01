package com.icet.crm.bo;

import com.icet.crm.bo.custom.impl.*;
import com.icet.crm.util.BoType;

public class BoFactory {

    private static BoFactory instance;
    private BoFactory(){}


    public static BoFactory getInstance(){
        return  instance != null? instance : (instance = new BoFactory());
    }

    public <T extends SuperBo>T getBo(BoType type){
        switch (type){
            case CUSTOMER: return (T) new CustomerBoImpl();
            case ITEM: return (T) new ItemBoImpl();
            case SUPPLIER: return (T) new SupplierBoImpl();
            case USER: return (T) new UserBoImpl();
        }
        return null;
    }
}
