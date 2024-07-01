package com.icet.crm.bo.custom;

import com.icet.crm.bo.SuperBo;
import com.icet.crm.dto.User;

public interface UserBo extends SuperBo {

    boolean saveUser(User dto);
    boolean deleteUser(String id);
    boolean updateUser(User dto);
}
