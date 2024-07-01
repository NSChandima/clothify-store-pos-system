package com.icet.crm.dao.custom.impl;

import com.icet.crm.dao.custom.SupplierDao;
import com.icet.crm.database.DBConnection;
import com.icet.crm.entity.SupplierEntity;
import com.icet.crm.util.CrudUtil;

import java.sql.SQLException;
import java.sql.Statement;

public class SupplierDaoImpl implements SupplierDao {


    @Override
    public boolean save(SupplierEntity entity) {

        try {
            String sql = "insert into supplier values (?,?,?,?,?)";
            return CrudUtil.execute(sql,
                    entity.getSupplierId(),
                    entity.getName(),
                    entity.getCompany(),
                    entity.getContact(),
                    entity.getEmail()
            );
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(String id) {

        try {
            String sql = "delete from supplier where supplier_id='"+id+"'";
            Statement  stm = DBConnection.getInstance().getConnection().createStatement();
            boolean isDelete = stm.execute(sql);
            return isDelete;
        } catch (SQLException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public boolean update(SupplierEntity entity) {
        try {
            String sql = "update supplier set name=?, company=?, contact=?, email=? where supplier_id='"+entity.getSupplierId()+"'";
            return  CrudUtil.execute(sql,
                    entity.getName(),
                    entity.getCompany(),
                    entity.getContact(),
                    entity.getEmail()
            );
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
