package com.icet.crm.dao.custom.impl;

import com.icet.crm.dao.custom.CustomerDao;
import com.icet.crm.database.DBConnection;
import com.icet.crm.entity.CustomerEntity;
import com.icet.crm.util.CrudUtil;

import java.sql.SQLException;
import java.sql.Statement;

public class CustomerDaoImpl implements CustomerDao {

    @Override
    public boolean save(CustomerEntity entity) {

        try {
            String sql = "insert into customer values(?,?,?,?)";
            return CrudUtil.execute(sql,
                    entity.getCustomerId(),
                    entity.getName(),
                    entity.getAddress(),
                    entity.getContact()
            );
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(String id) {

        try {
            String sql = "delete from customer where customer_id='"+ id +"'";
            Statement stm  = DBConnection.getInstance().getConnection().createStatement();
            boolean isDelete = stm.execute(sql);
            return isDelete;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean update(CustomerEntity entity){

        try {
            String sql = "update customer set customer_name=?, address=?, contact=? where customer_id='"+entity.getCustomerId()+"'";
            return CrudUtil.execute(sql,
                    entity.getName(),
                    entity.getAddress(),
                    entity.getContact()
            );
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
