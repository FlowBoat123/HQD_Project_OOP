package database;

import java.util.ArrayList;

/**
 * Data Access Object (DAO) interface.
 * This interface defines the standard operations to be performed on a model object.
 *
 * @param <T> The type of the model object.
 */
public interface DAO<T> {

    /**
     * Adds a new object to the database.
     *
     * @param t The object to be added.
     */
    void add(T t);

    /**
     * Deletes an object from the database.
     *
     * @param t The object to be deleted.
     */
    void delete(T t);

    /**
     * Updates an object in the database.
     *
     * @param t The object to be updated.
     */
    void update(T t);

    /**
     * Retrieves all objects from the database.
     *
     * @return An ArrayList of objects.
     */
    ArrayList<T> getAll();
}