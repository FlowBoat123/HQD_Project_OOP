package org.example.javafxtutorial;

import java.util.ArrayList;

public interface DAO<T> {
    void save(T t, String username);
    void delete(T t, String username);
    void update(T t, String username);
    ArrayList<T> getAll(String username);
}
