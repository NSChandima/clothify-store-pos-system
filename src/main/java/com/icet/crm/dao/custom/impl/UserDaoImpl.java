package com.icet.crm.dao.custom.impl;

import com.icet.crm.dao.custom.UserDao;
import com.icet.crm.database.DBConnection;
import com.icet.crm.entity.UserEntity;
import com.icet.crm.util.CrudUtil;

import java.sql.SQLException;
import java.sql.Statement;

public class UserDaoImpl implements UserDao {
    @Override
    public boolean save(UserEntity entity) {

        try {
            String sql = "insert into user values (?,?,?,?)";
            return CrudUtil.execute(sql,
                    entity.getUserId(),
                    entity.getName(),
                    entity.getEmail(),
                    entity.getContact()
            );
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(String id) {
        try {
            String sql = "delete from user where user_id='"+ id +"'";
            Statement stm = DBConnection.getInstance().getConnection().createStatement();
            boolean isDelete = stm.execute(sql);
            return isDelete;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(UserEntity entity) {
        String sql = "update user set name=?, email=? , contact=? where user_id='"+entity.getUserId()+"'";
        try {
           return CrudUtil.execute(sql,
                    entity.getName(),
                    entity.getEmail(),
                    entity.getContact()
            );
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
