package com.icet.crm.dao.custom.impl;

import com.icet.crm.dao.custom.ItemDao;
import com.icet.crm.database.DBConnection;
import com.icet.crm.entity.ItemEntity;
import com.icet.crm.util.CrudUtil;

import java.sql.SQLException;
import java.sql.Statement;

public class ItemDaoImpl implements ItemDao {
    @Override
    public boolean save(ItemEntity entity) {
        try {
            String sql = "insert into item values(?,?,?,?,?,?,?)";
            return CrudUtil.execute(sql,
                    entity.getItemCode(),
                    entity.getName(),
                    entity.getCategory(),
                    entity.getSize(),
                    entity.getUnitPrice(),
                    entity.getQuantity(),
                    entity.getSupplierId()
            );
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public boolean delete(String id) {
        try {
            String sql = "delete from item where item_code='"+ id +"'";
            Statement stm  = DBConnection.getInstance().getConnection().createStatement();
            return stm.execute(sql);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public boolean update(ItemEntity entity) {
        return false;
    }
}
