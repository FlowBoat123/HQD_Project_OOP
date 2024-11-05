package database;

import java.util.ArrayList;

public interface DAO<T> {
    void add(T t);
    void delete(T t);
    void update(T t);
    ArrayList<T> getAll();
}
