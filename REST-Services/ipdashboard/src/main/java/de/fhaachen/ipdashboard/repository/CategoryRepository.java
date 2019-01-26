package de.fhaachen.ipdashboard.repository;

import org.springframework.data.repository.CrudRepository;

import de.fhaachen.ipdashboard.model.Category;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface CategoryRepository extends CrudRepository<Category, Integer> {

}