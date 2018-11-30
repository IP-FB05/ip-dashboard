package de.fhaachen.ipserver.repository;

import org.springframework.data.repository.CrudRepository;

import de.fhaachen.ipserver.model.System;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface SystemRepository extends CrudRepository<System, Integer> {

}