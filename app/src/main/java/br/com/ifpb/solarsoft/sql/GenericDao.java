package br.com.ifpb.solarsoft.sql;

import java.util.ArrayList;

/**
 * Created by leonardo on 18/05/2015.
 */
public interface GenericDao<T, PK> {

    public void create(T t);

    public T update(T t);

    public boolean delete(T t);

    public T getById(PK id);

    public ArrayList<T> getAll();
}
