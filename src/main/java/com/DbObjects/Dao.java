package com.DbObjects;

/**
 * This is a general Database Access Object interface. Every specific DAO should extend this interface.
 * It provides the 4 CRUD operations
 * @param <T> The Java object which the operations will be performed on
 */
public interface Dao<T> {

    T find(int id);
    void insert(T item);
    void update(T item);
    void delete(int id);
}
