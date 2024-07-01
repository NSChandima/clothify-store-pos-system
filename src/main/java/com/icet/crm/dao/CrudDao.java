package com.icet.crm.dao;

public interface CrudDao<T> extends SuperDao{

    boolean save(T dto) ;
    boolean delete(String id);
    boolean update(T entity);
}
