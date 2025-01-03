package com.DbObjects;

import java.util.ArrayList;

public interface Dao<T> {
    ArrayList<T> getAll();
}
