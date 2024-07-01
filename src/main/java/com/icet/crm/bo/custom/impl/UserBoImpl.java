package com.icet.crm.bo.custom.impl;

import com.icet.crm.bo.custom.UserBo;
import com.icet.crm.dao.DAOFactory;
import com.icet.crm.dao.custom.UserDao;
import com.icet.crm.dto.User;
import com.icet.crm.entity.UserEntity;
import com.icet.crm.util.DaoType;
import org.modelmapper.ModelMapper;

import java.sql.SQLException;

public class UserBoImpl implements UserBo {

    private UserDao userDao = DAOFactory.getInstance().getDao(DaoType.USER);
    @Override
    public boolean saveUser(User dto) {

        return userDao.save(new ModelMapper().map(dto, UserEntity.class));
    }

    @Override
    public boolean deleteUser(String id) {
        return userDao.delete(id);
    }

    @Override
    public boolean updateUser(User dto) {
       return userDao.update(new ModelMapper().map(dto,UserEntity.class));
    }
}
