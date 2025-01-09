package com.DbObjects;

public interface Dao<T> {

    T find(int id);
    void insert(T item);
    void update(T item);
    void delete(int id);
}
