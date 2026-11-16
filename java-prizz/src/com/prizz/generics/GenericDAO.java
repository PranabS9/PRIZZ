package com.prizz.generics;

import com.prizz.exceptions.DatabaseOperationException;
import java.util.List;

/**
 * [GENERICS] Generic Data Access Object (DAO) Interface.
 * Purpose: Defines standard CRUD signatures parameterized by entity type T and ID type ID.
 * Syllabus: Introductory Generics (Type parameters <T, ID>), Interface Abstraction.
 *
 * @param <T>  Domain entity type
 * @param <ID> Primary key identifier type
 */
public interface GenericDAO<T, ID> {
    void create(T entity) throws DatabaseOperationException;
    T findById(ID id) throws DatabaseOperationException;
    List<T> findAll() throws DatabaseOperationException;
    void update(T entity) throws DatabaseOperationException;
    void delete(ID id) throws DatabaseOperationException;
}

